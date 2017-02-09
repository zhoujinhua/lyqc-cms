package com.liyun.car.materiel.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.materiel.entity.CmsDealerMateriel;
import com.liyun.car.materiel.entity.CmsDealerMaterielApproval;
import com.liyun.car.materiel.entity.CmsMaterielExpressHis;
import com.liyun.car.materiel.enums.DealerMaterielActionEnum;
import com.liyun.car.materiel.service.DealerMaterielService;
import com.liyun.car.param.entity.SyDict;
import com.liyun.car.param.usertype.DictEnum;
import com.liyun.car.remote.utils.JuHeExpressUtil;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.enums.UserTypeEnum;
import com.liyun.car.system.service.VUserRoleService;
import com.liyun.car.workflow.service.WorkflowService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("dealerMateriel")
public class DealerMaterielController {

	private Logger logger = LoggerFactory.getLogger(DealerMaterielController.class);
	@Autowired
	private DealerMaterielService dealerMaterielService;
	@Autowired
	private VUserRoleService vUserRoleService;
	@Autowired
	private WorkflowService workflowService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request ,CmsDealerMateriel materiel){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		String mtrlTyp = request.getParameter("mtrlTyp");
		
		SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsDealerMateriel> page = dealerMaterielService.pageList(materiel, pn, mtrlTyp, user);
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    map.put("userType", user.getUserType().getValue());
	    
	    return map;
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,CmsDealerMateriel materiel){
		materiel = dealerMaterielService.getEntityById(CmsDealerMateriel.class,materiel.getMtrlAppCode(),false);
		
		request.setAttribute("materiel", materiel);
		return "materiel/req/add";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,CmsDealerMateriel materiel){
		try{
			dealerMaterielService.deleteEntity(materiel);
			request.setAttribute("msg", "删除成功!");
		} catch(Exception e){
			request.setAttribute("msg", "删除失败,"+e.getMessage());
			logger.error("删除物料申请失败,",e);
		}
		return "materiel/req/list";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request,CmsDealerMateriel materiel){
		try{
			dealerMaterielService.saveDealerMateriel(materiel);
			request.setAttribute("msg", "保存成功!");
		} catch(Exception e){
			logger.error("保存或者更新申请信息失败",e);
			request.setAttribute("msg", "保存失败"+e.getMessage());
			return "materiel/req/add";
		}
		return "materiel/req/list";
	}
	
	@RequestMapping("submit")
	public String submit(HttpServletRequest request,CmsDealerMateriel materiel){
		try{
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			
			dealerMaterielService.saveStartProcess(materiel, user);
			request.setAttribute("msg", "提交成功!");
		} catch(Exception e){
			logger.error("保存或者更新申请信息失败",e);
			request.setAttribute("msg", "保存失败"+e.getMessage());
			request.setAttribute("materiel", materiel);
			return "materiel/req/add";
		}
		return "materiel/req/list";
	}
	
	@RequestMapping("listTask")
    @ResponseBody
    public Map<String,Object> listTask(HttpServletRequest request){
    	int length = ServletRequestUtils.getIntParameter(request, "length", 10);
    	int start = ServletRequestUtils.getIntParameter(request, "start", 0);
    	int pn = start == 0?1:(start/length+1);
    	
    	SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
    	Map<String, Object> map = new HashMap<String,Object>();
    	Page<CmsDealerMateriel> page;
		try {
			page = workflowService.pageTasks(CmsDealerMateriel.class,user, pn, CmsDealerMateriel.FLOW_NAME, "mtrlAppCode");
    	
	    	map.put("aaData", page.getItems());
	    	map.put("recordsTotal", page.getCount());
	    	map.put("recordsFiltered", page.getCount());
	    } catch (Exception e) {
	    	
	    }
		return map;
	}
	
	@RequestMapping("claimTask")
    public String claimTask(HttpServletRequest request, HttpServletResponse response){
    	try{
    		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
    		String taskId = request.getParameter("taskId");
    		workflowService.claimTask(taskId, user.getUserId());
    		
    		request.setAttribute("msg", "操作成功!");;
        } catch (Exception e) {
        	logger.error("认领失败,错误信息：",e);
        	request.setAttribute("msg", "操作失败,"+e.getMessage());
        }
    	return "materiel/req/listTask";
    }
	
	@RequestMapping("complete")
    public String complete(HttpServletRequest request, CmsDealerMaterielApproval approval, CmsDealerMateriel materiel) {
        try {
        	String taskId = request.getParameter("taskId");
        	SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
        	
        	approval.setAction(DealerMaterielActionEnum.APPROVAL);
        	dealerMaterielService.saveCompleteTask(materiel,taskId,approval,user);
        	request.setAttribute("msg", "操作成功！");
        } catch (Exception e) {
        	logger.error("完成失败,错误信息：",e);
        	request.setAttribute("msg", "操作失败,！"+e.getMessage());
        }
        return "materiel/req/listTask";
    }
	
	@RequestMapping("approval")
	public String approval(HttpServletRequest request,HttpServletResponse response,CmsDealerMateriel materiel){
		String taskId = request.getParameter("taskId");
		
		Task task = workflowService.getTask(taskId);
		materiel = dealerMaterielService.getEntityById(CmsDealerMateriel.class, materiel.getMtrlAppCode(), false);
		materiel.setTask(task);
		CmsDealerMaterielApproval approval = new CmsDealerMaterielApproval(null, materiel.getMtrlAppCode());
		List<CmsDealerMaterielApproval> approvalList = dealerMaterielService.getEntitysByParams(approval, OperMode.EQ, "mtrlAppCode");
		
		request.setAttribute("materiel", materiel);
		request.setAttribute("approvalList", approvalList);
		return "materiel/req/view";
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request,HttpServletResponse response,CmsDealerMateriel materiel){
		materiel = dealerMaterielService.getEntityById(CmsDealerMateriel.class, materiel.getMtrlAppCode(), false);
		CmsDealerMaterielApproval approval = new CmsDealerMaterielApproval(null, materiel.getMtrlAppCode());
		List<CmsDealerMaterielApproval> approvalList = dealerMaterielService.getEntitysByParams(approval, OperMode.EQ, "mtrlAppCode");
		
		request.setAttribute("materiel", materiel);
		request.setAttribute("approvalList", approvalList);
		return "materiel/req/view";
	}
	
	@RequestMapping("register")
	public String register(HttpServletRequest request, CmsDealerMateriel materiel){
		try{
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			boolean flag = vUserRoleService.hasRole(user, "SO");
			if(flag){
				Task task = workflowService.getTask(user.getUserId()+"", materiel.getMtrlAppCode()+"");
				materiel.setTask(task);
				
				CmsDealerMaterielApproval approval = new CmsDealerMaterielApproval();
				approval.setAction(DealerMaterielActionEnum.SEND);
				dealerMaterielService.saveCompleteTask(materiel, task.getId(), approval, user);
				request.setAttribute("msg", "操作成功！");
			} else {
				throw new RuntimeException("此用户无权进行物流登记操作.");
			}
		} catch(Exception e){
			logger.error("物流登记失败,",e);
			request.setAttribute("msg", "操作失败，"+e.getMessage());
		}
		return "materiel/req/list";
	}
	
	@RequestMapping("confirm")
	public String comfirm(HttpServletRequest request, CmsDealerMateriel materiel){
		try{
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			Task task = workflowService.getTask(user.getUserId()+"", materiel.getMtrlAppCode()+"");
			materiel.setTask(task);
			
			CmsDealerMaterielApproval approval = new CmsDealerMaterielApproval();
			approval.setAction(DealerMaterielActionEnum.CONFIRM);
			dealerMaterielService.saveCompleteTask(materiel, task.getId(), approval, user);
			request.setAttribute("msg", "操作成功！");
		} catch(Exception e){
			logger.error("经销商确认收货失败,",e);
			request.setAttribute("msg", "操作失败，"+e.getMessage());
		}
		return "materiel/req/list";
	}
	
	
	@RequestMapping("search")
	public void expressSeach(HttpServletRequest request,HttpServletResponse response, CmsMaterielExpressHis his){
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			JSONObject object = null;
			
			if(user.getUserType() != UserTypeEnum.M){
				his = dealerMaterielService.getAvailExpressHis(his);
				if(his!=null){
					object = JSONObject.fromObject(his.getRecContent());
				}
			}
			if(object == null){
				object = JuHeExpressUtil.httpGet(his.getNo());
				
				his = new CmsMaterielExpressHis(his.getNo(),object.toString(),user.getUserId(),user.getTrueName(),new Date());
				dealerMaterielService.saveEntity(his);
			}
			pw.print(object.toString());
		} catch (Exception e) {
			logger.error("调用聚信立物流接口失败.");
		}
	}
	@RequestMapping("forceEnd")
    public String forceEnd(HttpServletRequest request){
    	try{
    		String taskId = request.getParameter("taskId");
    		Integer mtrlAppCode = ServletRequestUtils.getIntParameter(request, "mtrlAppCode");
    		dealerMaterielService.updateEndProcess(taskId, mtrlAppCode);
    		request.setAttribute("msg", "操作成功!");
    	} catch(Exception e){
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    		logger.error("强制结束流程失败,",e);
    	}
    	return "workflow/monitor/materielMonitor";
    }
}
