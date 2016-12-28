package com.liyun.car.dealer.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.utils.PropertyUtil;
import com.liyun.car.core.utils.DownloadUtil;
import com.liyun.car.core.utils.ReturnUitl;
import com.liyun.car.dealer.entity.CmsDealerApproval;
import com.liyun.car.dealer.entity.CmsDealerCompany;
import com.liyun.car.dealer.entity.CmsDealerFile;
import com.liyun.car.dealer.entity.SyDealerCompany;
import com.liyun.car.dealer.entity.vo.VDealerCompany;
import com.liyun.car.dealer.service.DealerCompanyService;
import com.liyun.car.param.entity.SyDict;
import com.liyun.car.param.usertype.DictEnum;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.service.VUserRoleService;
import com.liyun.car.workflow.service.WorkflowService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("dealercompany")
public class DealerCompanyController{

	private Logger logger = LoggerFactory.getLogger(DealerCompanyController.class);
	
    @Autowired
    private DealerCompanyService dealerCompanyService;
    
    @Autowired
    private VUserRoleService vUserRoleService;
    
    @Autowired
    private WorkflowService workflowService;
    
    @RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request ,VDealerCompany dc){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
		boolean am = vUserRoleService.hasRole(user, "AM");
		Map<String, Object> map = new HashMap<String,Object>();
		Page<VDealerCompany> page = dealerCompanyService.pageList(dc, pn, am, user, null);
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    map.put("am", am);
	    map.put("user", user);
	    
	    return map;
	}
    
    @RequestMapping("listOnline")
	@ResponseBody
	public Map<String,Object> listOnline(HttpServletRequest request){
		SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
		boolean am = vUserRoleService.hasRole(user, "AM");
		Map<String, Object> map = new HashMap<String,Object>();
		List<SyDealerCompany> vBeans = dealerCompanyService.getSyList(am, user);
		
		map.put("aaData", vBeans);
	    return map;
	}
    
    @RequestMapping("edit")
    public String edit(HttpServletRequest request ){
		try {
			Integer companyCode = ServletRequestUtils.getIntParameter(request, "companyCode");
	    	if(companyCode!=null && !"".equals(companyCode)){
	    		SyDealerCompany company = dealerCompanyService.getEntityById(SyDealerCompany.class, companyCode, true);
	    		CmsDealerCompany cmsCompany = null;
	    		
	    		if(company == null){
	    			cmsCompany = dealerCompanyService.getEntityById(CmsDealerCompany.class, companyCode, true);
	    			request.setAttribute("dc", cmsCompany);
	    			if(cmsCompany!=null){
	    				request.setAttribute("dealerFiles", JSONArray.fromObject(cmsCompany.getDealerFiles()));
	    			}
	    		} else {
	    			request.setAttribute("dc", company);
	    			if(company!=null){
	    				request.setAttribute("dealerFiles", JSONArray.fromObject(company.getDealerFiles()));
	    			}
	    		}
	    		if((company!=null && company.getStatus().getEditFlag().equals("2")) || (cmsCompany!=null && cmsCompany.getStatus().getEditFlag().equals("2"))){
	    			//更新已上线经销商
	    			return "dealer/company/update";
	    		}
	    	}
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}  
    	return "dealer/company/add";
    }
    
    @RequestMapping("save")
    public String save(HttpServletRequest request, CmsDealerCompany company){
    	try{
    		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
    		if(company.getCompanyCode()!=null){ 
    			dealerCompanyService.updateCmsDealerCompany(company,user);
    		} else {
    			dealerCompanyService.saveCmsDealerCompany(company,user);
    		}
    		request.setAttribute("msg", "保存成功.");
    	} catch(Exception e){
    		
    	}
    	return "redirect:edit?companyCode="+company.getCompanyCode();
    }
    
    @RequestMapping("submit")
    public String submit(HttpServletRequest request,HttpServletResponse response,CmsDealerCompany company){
    	try{
    		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
    		
    		CmsDealerApproval approval = new CmsDealerApproval();
    		//保存修改并启动流程
			dealerCompanyService.saveStartProcess(company, null, approval, user);
    		request.setAttribute("msg", "提交成功!");
    	} catch(Exception e){
    		logger.error("提交失败,错误信息：",e);
    		request.setAttribute("msg", "提交失败，"+e.getMessage());
    	}
    	return "dealer/company/list";
    }
    
    @RequestMapping("listTask")
    @ResponseBody
    public Map<String,Object> listTask(HttpServletRequest request ,VDealerCompany dc){
    	int length = ServletRequestUtils.getIntParameter(request, "length", 10);
    	int start = ServletRequestUtils.getIntParameter(request, "start", 0);
    	int pn = start == 0?1:(start/length+1);
    	
    	SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
    	Map<String, Object> map = new HashMap<String,Object>();
    	Page<VDealerCompany> page;
		try {
			page = workflowService.pageTasks(VDealerCompany.class,user, pn, VDealerCompany.FLOW_NAME, "companyCode","tempCompanyCode");
    	
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
    	return "dealer/company/listTask";
    }
    
    @RequestMapping("complete")
    public String complete(HttpServletRequest request, CmsDealerApproval approval, VDealerCompany vDealerCompany) {
        try {
        	String taskId = request.getParameter("taskId");
        	SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
        	
        	dealerCompanyService.saveCompleteTask(vDealerCompany,taskId,approval,user);
        	request.setAttribute("msg", "操作成功！");
        } catch (Exception e) {
        	logger.error("完成失败,错误信息：",e);
        	request.setAttribute("msg", "操作失败,！"+e.getMessage());
        }
        return "dealer/company/listTask";
    }
    
    /**
     * 下线申请
     * @return
     */
    @RequestMapping("offline")
    public String offline(HttpServletRequest request, HttpServletResponse response,CmsDealerApproval approval, SyDealerCompany syDealerCompany){
    	try{
	    	SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
	    	
	    	dealerCompanyService.saveStartProcess(null, syDealerCompany, approval, user);
	    	request.setAttribute("msg", "提交成功!");
			return "dealer/company/list";
		} catch(Exception e){
			logger.error("提交失败,错误信息：",e);
			request.setAttribute("msg", "提交失败，"+e.getMessage());
			return "dealer/company/list";
		}
    }
    
    @RequestMapping("confirm")
    public String confirm(HttpServletRequest request, CmsDealerApproval approval, VDealerCompany vDealerCompany){
    	try{
    		String taskId = request.getParameter("taskId");
	    	SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
	    	
	    	dealerCompanyService.saveCompleteTask(vDealerCompany, taskId, approval, user);
	    	request.setAttribute("msg", "操作成功！");
    	} catch(Exception e){
    		logger.error("任务失败,错误信息：",e);
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    	}
    	return "dealer/company/listTask";
    }
    
    @RequestMapping("view")
    public String view(HttpServletRequest request, Integer companyCode){
    	try {
			SyDealerCompany company = dealerCompanyService.getEntityById(SyDealerCompany.class, companyCode, true);
			Integer tempCompanyCode = null;
			if(company==null){
				CmsDealerCompany cmsDealerCompany = dealerCompanyService.getEntityById(CmsDealerCompany.class, companyCode, true);
				request.setAttribute("dc", cmsDealerCompany);
				tempCompanyCode = cmsDealerCompany.getCompanyCode();
			} else {
				tempCompanyCode = company.getTempCompanyCode();
				request.setAttribute("dc", company);
			}
			Map<String, Object> map = new HashMap<String, Object>();
    		Integer[] valueArray = new Integer[]{companyCode,tempCompanyCode};
    		map.put("code", valueArray);
    		List<CmsDealerApproval> approvalList = dealerCompanyService.getEntitysByParamMap(CmsDealerApproval.class, OperMode.IN, map);
			request.setAttribute("approvalList", approvalList);
		} catch (Exception e) {
			logger.error("任务失败,错误信息：",e);
			e.printStackTrace();
		} 
    	return "dealer/company/view";
    }
    
    @RequestMapping("saveUpdate")
    public String saveUpdate(HttpServletRequest request, SyDealerCompany company){
    	try{
    		Integer code = ServletRequestUtils.getIntParameter(request, "code");
    		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
    		
    		dealerCompanyService.saveSyDealerCompany(company,code,user);
    		request.setAttribute("msg", "修改成功!");
    		return "dealer/company/list";
    	} catch(Exception e){
    		logger.error("任务失败,错误信息：",e);
    		edit(request);
    		request.setAttribute("msg", "修改失败,"+e.getMessage());
    		return "dealer/company/update";
    	}
    }
    
    @RequestMapping("forceEnd")
    public String forceEnd(HttpServletRequest request){
    	try{
    		String taskId = request.getParameter("taskId");
    		Integer companyCode = ServletRequestUtils.getIntParameter(request, "companyCode");
    		dealerCompanyService.updateEndProcess(taskId, companyCode);
    		request.setAttribute("msg", "操作成功!");
    	} catch(Exception e){
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    		logger.error("强制结束流程失败,",e);
    	}
    	return "workflow/monitor/companyMonitor";
    }
    
    @RequestMapping("approval")
    public String approval(HttpServletRequest request, HttpServletResponse response){
		String taskId = request.getParameter("taskId");
		Integer companyCode;
		try {
			companyCode = ServletRequestUtils.getIntParameter(request, "companyCode");
			Task task = workflowService.getTask(taskId);
			VDealerCompany vDealerCompany = new VDealerCompany(companyCode);
			vDealerCompany.setTempCompanyCode(companyCode);
			vDealerCompany = dealerCompanyService.getEntitysByParams(vDealerCompany, OperMode.OR, "companyCode","tempCompanyCode").get(0);
			vDealerCompany.setTask(task);
			
			Map<String, Object> map = new HashMap<String, Object>();
			Integer[] valueArray = new Integer[]{companyCode,vDealerCompany.getTempCompanyCode()};
			map.put("code", valueArray);
			List<CmsDealerApproval> approvalList = dealerCompanyService.getEntitysByParamMap(CmsDealerApproval.class, OperMode.IN, map);
			
			request.setAttribute("vcompany", vDealerCompany);
			request.setAttribute("approvalList", approvalList);
			
			SyDealerCompany company = dealerCompanyService.getEntityById(SyDealerCompany.class, companyCode, true);
			CmsDealerCompany cmsDealerCompany = null;
			if(company == null){
				cmsDealerCompany = dealerCompanyService.getEntityById(CmsDealerCompany.class, companyCode, true);
				request.setAttribute("dc", cmsDealerCompany);
			} else {
				request.setAttribute("dc", company);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("进入审批页面失败,",e);
		}
		return "dealer/company/view";
    }
    
    @RequestMapping("upload")
    public void upload(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request, HttpServletResponse response){
    	PrintWriter pw = null;
    	try {
    		pw = response.getWriter();
    		String typeId = request.getParameter("typeId");
    		String code = request.getParameter("code");
    		
    		response.setContentType("text/html;charset=UTF-8");
    		String fileName = file.getOriginalFilename();
    		String prefix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
    		String path = PropertyUtil.getPropertyValue("DEALER_FILE_PATH")+(code+"_"+new Date().getTime()+prefix);
			
			FileUtils.writeByteArrayToFile(new File(path), file.getBytes());
			CmsDealerFile cdf = new CmsDealerFile(Integer.parseInt(code),DictEnum.valueOf(SyDict.class, typeId),fileName,path,new Date());
			cdf = dealerCompanyService.saveCmsDealerFile(cdf);
			
			pw.print("{\"msg\":\"success\",\"fileName\":\""+fileName+"\",\"fileId\":\""+cdf.getId()+"\"}");
    	} catch (IOException e) {
    		logger.error("任务失败,错误信息：",e);
    		pw.print("{\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();
		}
    }
    
    @RequestMapping("deleteFile")
    public void deleteFile(HttpServletRequest request, HttpServletResponse response,CmsDealerFile file){
    	PrintWriter pw = null;
    	try{
    		pw = response.getWriter();
    		dealerCompanyService.deleteEntity(file);
    		pw.print("1");
    	} catch(Exception e){
    		pw.print(e.getMessage());
    	}
    }
    
    @RequestMapping("viewAnnex")
    public void downloadAnnex(HttpServletRequest request, HttpServletResponse response, CmsDealerFile cmsDealerFile){
    	cmsDealerFile = dealerCompanyService.getEntityById(CmsDealerFile.class, cmsDealerFile.getId(), false);
    	try {
    		if(new File(cmsDealerFile.getFileDir()).exists()){
    			DownloadUtil.download(request, response, new File(cmsDealerFile.getFileDir()), cmsDealerFile.getFileName());
    		} else {
    			throw new RuntimeException("下载失败，文件不存在!");
    		}
		} catch (Exception e) {
			logger.error("任务失败,错误信息：",e);
			try {
				show_msg(response, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
    public void show_msg(HttpServletResponse response,String msg) throws IOException{
	    response.setContentType("text/html; charset=gbk");  
	    PrintWriter out = response.getWriter();   
	    out.println("<script language='javascript'>");   
    	out.println("alert('"+msg+"');");
    	out.println("history.go(-1);");
	    out.print("</script>");   
	}
    @RequestMapping("delete")
    public String delete(HttpServletRequest request, CmsDealerCompany company){
    	try{
    		dealerCompanyService.deleteEntity(company);
    		request.setAttribute("msg", "删除成功！");
    	} catch (Exception e) {
    		logger.error("任务失败,错误信息：",e);
    		request.setAttribute("msg", "删除失败,！"+e.getMessage());
    	}
    	return "dealer/company/list";
    }
    
    @RequestMapping("onOffLine")
    public String onOffLine(HttpServletRequest request, CmsDealerApproval approval, VDealerCompany vDealerCompany){
    	try{
    		String taskId = request.getParameter("taskId");
	    	SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
	    	
	    	dealerCompanyService.saveCompleteTask(vDealerCompany, taskId, approval, user);
	    	request.setAttribute("msg", "操作成功！");
    	} catch(Exception e){
    		logger.error("任务失败,错误信息：",e);
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    	}
    	return "dealer/company/listTask";
    }
    
    @RequestMapping("check")
    public void check(HttpServletRequest request, HttpServletResponse response, SyDealerCompany company){
		company = dealerCompanyService.getEntityById(SyDealerCompany.class, company.getCompanyCode(), false);
		if(company!=null){
			ReturnUitl.write(response, 0, "经销商代码重复.");
		} else {
			ReturnUitl.write(response, 1);
		}
    }
}
