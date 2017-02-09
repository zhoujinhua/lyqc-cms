package com.liyun.car.dealer.controller;

import java.io.PrintWriter;
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
import com.liyun.car.core.utils.ReturnUitl;
import com.liyun.car.dealer.entity.CmsDealer;
import com.liyun.car.dealer.entity.CmsDealerApproval;
import com.liyun.car.dealer.entity.SyDealer;
import com.liyun.car.dealer.entity.vo.VDealer;
import com.liyun.car.dealer.entity.vo.VDealerCompany;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.dealer.service.DealerService;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.service.VUserRoleService;
import com.liyun.car.workflow.service.WorkflowService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("dealer")
public class DealerController {
	
	private Logger logger = LoggerFactory.getLogger(DealerController.class);
	
    @Autowired
    private DealerService dealerService;
    
    @Autowired
    private VUserRoleService vUserRoleService;
    
    @Autowired
    private WorkflowService workflowService;
    
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request ,VDealer dc){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
		boolean am = vUserRoleService.hasRole(user, "AM");
		Map<String, Object> map = new HashMap<String,Object>();
		Page<VDealer> page = dealerService.pageList(dc, pn, am, user, DealerStatusEnum.getOnOrPreLineStatus());
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    map.put("am", am);
	    
	    return map;
	}
	
	@RequestMapping("listOnline")
	@ResponseBody
	public Map<String,Object> listOnline(HttpServletRequest request ,VDealer dc){
		SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
		boolean am = vUserRoleService.hasRole(user, "AM");
		Map<String, Object> map = new HashMap<String,Object>();
		List<SyDealer> list = dealerService.getSyList(am, user);
		
		map.put("aaData", list);
	    return map;
	}
	
	@RequestMapping("save")
    public String save(HttpServletRequest request, CmsDealer dealer){
    	SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
    	dealer.setAm(user);
    	if(dealer.getDealerCode()!=null){
    		dealerService.updateCmsDealer(dealer);
    	} else {
    		dealerService.saveCmsDealer(dealer, user);
    	}
    	
    	//request.setAttribute("dc", dealer);
    	//request.setAttribute("msg", "保存成功.");
    	return "redirect:edit?dealerCode="+dealer.getDealerCode()+"&msg=succ";
    }
	
	@RequestMapping("submit")
    public String submit(HttpServletRequest request, CmsDealer dealer){
    	try{
    		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
    		CmsDealerApproval approval = new CmsDealerApproval();
    		
    		dealerService.saveStartProcess(dealer, null, approval, user);
    		request.setAttribute("msg", "提交成功!");
    		return "dealer/dealer/list";
    	} catch(Exception e){
    		logger.error("提交失败,错误信息：",e);
    		request.setAttribute("msg", "提交失败，"+e.getMessage());
    		return "dealer/dealer/list";
    	}
    }
	
	@RequestMapping("saveUpdate")
    public String saveUpdate(HttpServletRequest request, SyDealer dealer){
    	try{
    		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
    		Integer code = ServletRequestUtils.getIntParameter(request, "code");
    		
    		dealerService.saveSyDealer(dealer,code,user);
    		request.setAttribute("msg","保存成功！");
    	} catch(Exception e){
    		logger.error("保存失败,错误信息：",e);
    		
    		request.setAttribute("dc", dealer);
    		request.setAttribute("msg", "保存失败,"+e.getMessage());
    		return "dealer/dealer/update";
    	}
    	return "dealer/dealer/list";
    }
	
	@RequestMapping("offline")
    public String offline(HttpServletRequest request, CmsDealerApproval approval, SyDealer dealer){
    	try{
    		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
    		
    		dealerService.saveStartProcess(null, dealer, approval, user);
	    	request.setAttribute("msg", "提交成功!");
			return "dealer/dealer/list";
		} catch(Exception e){
			logger.error("下线失败,错误信息：",e);
			request.setAttribute("msg", "提交失败，"+e.getMessage());
			return "dealer/dealer/list";
		}
    }
	
	@RequestMapping("edit")
    public String edit(HttpServletRequest request ){
    	String dealerCode = request.getParameter("dealerCode");
    	CmsDealer cmsDealer = null;
    	Integer companyCode = null;
    	
    	SyDealer syDealer = dealerService.getEntityById(SyDealer.class, Integer.parseInt(dealerCode), true);
		if(syDealer == null){
			cmsDealer = dealerService.getEntityById(CmsDealer.class,Integer.parseInt(dealerCode), true);
			companyCode = cmsDealer.getCompanyCode();
			request.setAttribute("dc", cmsDealer);
			if(cmsDealer!=null){
				request.setAttribute("dealerFiles", JSONArray.fromObject(cmsDealer.getDealerFiles()));
			}
		} else {
			companyCode = syDealer.getCompany().getCompanyCode();
			request.setAttribute("dc", syDealer);
			if(syDealer!=null){
				request.setAttribute("dealerFiles", JSONArray.fromObject(syDealer.getDealerFiles()));
			}
		}
		VDealerCompany dealerCompany = dealerService.getEntityById(VDealerCompany.class,companyCode, true);
    	request.setAttribute("company", dealerCompany);
    	request.setAttribute("msg", request.getParameter("msg")!=null&&request.getParameter("msg").equals("succ")?"操作成功！":"");
    	
		if((syDealer!=null && syDealer.getStatus().getEditFlag().equals("2")) || (cmsDealer!=null && cmsDealer.getStatus().getEditFlag().equals("2"))){
			//更新已上线经销商
			return "dealer/dealer/update";
		}
    	return "dealer/dealer/add";
    }
	
	@RequestMapping("view")
    public String view(HttpServletRequest request, Integer dealerCode){
    	try {
    		SyDealer syDealer = dealerService.getEntityById(SyDealer.class, dealerCode, true);
    		Integer tempCode = null;
    		if(syDealer == null){
    			CmsDealer cmsDealer = dealerService.getEntityById(CmsDealer.class, dealerCode, true);
    			request.setAttribute("dc", cmsDealer);
    			tempCode = cmsDealer.getDealerCode();
    		} else {
    			request.setAttribute("dc", syDealer);
    			tempCode = syDealer.getTempDealerCode();
    		}
    		Map<String, Object> map = new HashMap<String, Object>();
    		Integer[] valueArray = new Integer[]{dealerCode,tempCode};
    		map.put("code", valueArray);
    		List<CmsDealerApproval> approvalList = dealerService.getEntitysByParamMap(CmsDealerApproval.class, OperMode.IN, map);
    		request.setAttribute("approvalList", approvalList);
		} catch (Exception e) {
			logger.error("查看失败,错误信息：",e);
			e.printStackTrace();
		} 
    	return "dealer/dealer/view";
    }
	
	@RequestMapping("approval")
    public String approval(HttpServletRequest request, HttpServletResponse response){
		String taskId = request.getParameter("taskId");
		Integer dealerCode;
		try {
			dealerCode = ServletRequestUtils.getIntParameter(request, "dealerCode");
			Task task = workflowService.getTask(taskId);
			VDealer vDealer = new VDealer(dealerCode);
			vDealer.setTempDealerCode(dealerCode);
			VDealer vdealer = dealerService.getEntitysByParams(vDealer, OperMode.OR, "dealerCode","tempDealerCode").get(0);
			vdealer.setTask(task);
			
			Map<String, Object> map = new HashMap<String, Object>();
			Integer[] valueArray = new Integer[]{dealerCode,vdealer.getTempDealerCode()};
			map.put("code", valueArray);
			List<CmsDealerApproval> approvalList = dealerService.getEntitysByParamMap(CmsDealerApproval.class, OperMode.IN, map);
			
			request.setAttribute("vdealer", vdealer);
			request.setAttribute("approvalList", approvalList);
			
			SyDealer dealer = dealerService.getEntityById(SyDealer.class, dealerCode, true);
			CmsDealer cmsDealer = null;
			if(dealer == null){
				cmsDealer = dealerService.getEntityById(CmsDealer.class, dealerCode, true);
				request.setAttribute("dc", cmsDealer);
			} else {
				request.setAttribute("dc", dealer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("进入审批页面失败,",e);
		}
		return "dealer/dealer/view";
    }
    
    @RequestMapping("delete")
    public String delete(HttpServletRequest request, CmsDealer dealer){
    	try{
    		dealerService.deleteCmsDealer(dealer);
    		request.setAttribute("msg", "删除成功！");
        } catch (Exception e) {
        	logger.error("删除失败,错误信息：",e);
        	request.setAttribute("msg", "删除失败,！"+e.getMessage());
        }
    	return "dealer/dealer/list";
    }
    
    @RequestMapping("onOffLine")
    public String onOffLine(HttpServletRequest request, CmsDealerApproval approval, VDealer vDealer){
    	try{
    		String taskId = request.getParameter("taskId");
	    	SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
	    	
	    	dealerService.saveCompleteTask(vDealer, taskId, approval, user);
	    	request.setAttribute("msg", "操作成功！");
    	} catch(Exception e){
    		logger.error("任务失败,错误信息：",e);
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    	}
    	return "dealer/dealer/listTask";
    }
    
    @RequestMapping("listTask")
    @ResponseBody
    public Map<String,Object> listTask(HttpServletRequest request){
    	int length = ServletRequestUtils.getIntParameter(request, "length", 10);
    	int start = ServletRequestUtils.getIntParameter(request, "start", 0);
    	int pn = start == 0?1:(start/length+1);
    	
    	SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
    	Map<String, Object> map = new HashMap<String,Object>();
    	Page<VDealer> page;
		try {
			page = workflowService.pageTasks(VDealer.class,user, pn, VDealer.FLOW_NAME, "dealerCode","tempDealerCode");
    	
	    	map.put("aaData", page.getItems());
	    	map.put("recordsTotal", page.getCount());
	    	map.put("recordsFiltered", page.getCount());
	    } catch (Exception e) {
	    	
	    }
		return map;
	}

    @RequestMapping("claimTask")
    public String claimTask(HttpServletRequest request ){
    	try{
    		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
    		String taskId = request.getParameter("taskId");
    		workflowService.claimTask(taskId, user.getUserId());
    		
    		request.setAttribute("msg", "操作成功!");
        } catch (Exception e) {
        	logger.error("认领失败,错误信息：",e);
        	request.setAttribute("msg", "操作失败,"+e.getMessage());
        }
    	return "dealer/dealer/listTask";
    }
    
    @RequestMapping("complete")
    public String complete(HttpServletRequest request, CmsDealerApproval approval, VDealer vDealer) {
        try {
        	String taskId = request.getParameter("taskId");
        	SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
        	
        	dealerService.saveCompleteTask(vDealer, taskId, approval, user);
        	request.setAttribute("msg", "操作成功！");
        } catch (Exception e) {
        	logger.error("完成失败,错误信息：",e);
        	request.setAttribute("msg", "操作失败,！"+e.getMessage());
        }
        return "dealer/dealer/listTask";
    }
    
    @RequestMapping("getTree")
    public void getTree(HttpServletRequest request, HttpServletResponse response){
    	PrintWriter pw = null;
		try{
			pw = response.getWriter();
	    	List<String> list = dealerService.getDealerTree();
	    	pw.write(JSONArray.fromObject(list).toString());
		} catch(Exception e){
			logger.error("生成(经销商树)失败,错误信息：",e);
		}
    }
    
    @RequestMapping("forceEnd")
    public String forceEnd(HttpServletRequest request){
    	try{
    		String taskId = request.getParameter("taskId");
    		Integer dealerCode = ServletRequestUtils.getIntParameter(request, "dealerCode");
    		dealerService.updateEndProcess(taskId, dealerCode);
    		request.setAttribute("msg", "操作成功!");
    	} catch(Exception e){
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    		logger.error("强制结束流程失败,",e);
    	}
    	return "workflow/monitor/dealerMonitor";
    }
    
    @RequestMapping("check")
    public void check(HttpServletRequest request, HttpServletResponse response, SyDealer dealer){
    	dealer = dealerService.getEntityById(SyDealer.class, dealer.getDealerCode(), false);
		if(dealer!=null){
			ReturnUitl.write(response, 0, "经销商门店代码重复.");
		} else {
			ReturnUitl.write(response, 1);
		}
    }
}
