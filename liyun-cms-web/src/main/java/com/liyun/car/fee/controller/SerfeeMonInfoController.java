package com.liyun.car.fee.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.utils.PropertyUtil;
import com.liyun.car.core.utils.DownloadUtil;
import com.liyun.car.fee.entity.CmsDealerSerfeeFlow;
import com.liyun.car.fee.entity.CmsSerfeeMonInfo;
import com.liyun.car.fee.enums.SerfeeActionEnum;
import com.liyun.car.fee.service.SerfeeMonInfoService;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.workflow.service.WorkflowService;

@Controller
@RequestMapping("serfee")
public class SerfeeMonInfoController {

	private Logger logger = LoggerFactory.getLogger(SerfeeMonInfoController.class);
	
	@Autowired
	private SerfeeMonInfoService serfeeMonInfoService;

	@Autowired
	private WorkflowService workflowService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request , CmsSerfeeMonInfo info){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsSerfeeMonInfo> page = serfeeMonInfoService.pageList(info, pn,"feeMon","stt");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("submit")
	public String submit(HttpServletRequest request, CmsDealerSerfeeFlow flow){
		try{
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			Map<String, Object> variables = new HashMap<String, Object>();
			serfeeMonInfoService.saveStartProcess(flow,user,variables);
			request.setAttribute("msg", "提交成功")	;
		} catch(Exception e){
			logger.error("提交失败,错误信息：",e);
			request.setAttribute("msg", "提交失败,"+e.getMessage());
		}
		return "fee/serfee/list";
	}
	
	@RequestMapping("listTask")
	@ResponseBody
	public Map<String,Object> listTask(HttpServletRequest request , CmsSerfeeMonInfo info){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsSerfeeMonInfo> page = null;
		try {
			page = workflowService.pageTasks(CmsSerfeeMonInfo.class,user, pn, CmsSerfeeMonInfo.FLOW_NAME, "feeMon");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("claimTask")
    public String claimTask(HttpServletRequest request ){
    	try{
    		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
    		String taskId = request.getParameter("taskId");
    		workflowService.claimTask(taskId, user.getUserId());
    		
    		request.setAttribute("msg", "操作成功！");
        } catch (Exception e) {
        	logger.error("操作失败,错误信息：",e);
        	request.setAttribute("msg", "操作失败,"+e.getMessage());
        }
    	return "fee/serfee/listTask";
    }
	
	@RequestMapping("complete")
    public String complete(HttpServletRequest request, CmsSerfeeMonInfo info, CmsDealerSerfeeFlow flow) {
        try {
        	String taskId = request.getParameter("taskId");
        	SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
        	
        	flow.setAction(SerfeeActionEnum.APPROVAL);
        	serfeeMonInfoService.saveCompleteTask(info,taskId,flow,user);
        	request.setAttribute("msg", "操作成功！");
        } catch (Exception e) {
        	logger.error("操作失败,错误信息：",e);
        	request.setAttribute("msg", "操作失败,！"+e.getMessage());
        }
        return "fee/serfee/listTask";
    }
	
	@RequestMapping("downAdjFile")
	public void downAdjFile(HttpServletRequest request,HttpServletResponse response,CmsDealerSerfeeFlow flow){
		try{
			flow = serfeeMonInfoService.getEntityById(CmsDealerSerfeeFlow.class, flow.getId(), true);
			if(!new File(flow.getFileDir()).exists()){
				throw new RuntimeException("下载失败，文件丢失.");
			}
			DownloadUtil.download(request, response, new File(flow.getFileDir()), new File(flow.getFileDir()).getName());
		} catch(Exception e){
			logger.error("下载调整文件失败,",e);
			try {
				show_msg(response, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	@RequestMapping("downSerFile")
	public void downSerFile(HttpServletRequest request,HttpServletResponse response,CmsSerfeeMonInfo info,RedirectAttributes redirectAttributes){
		try{
			info = serfeeMonInfoService.getEntityByCode(CmsSerfeeMonInfo.class, info.getFeeMon(), false);
			if(info == null){
				throw new RuntimeException("下载失败，服务费批次不存在.");
			}
			
			boolean flag = false;
			if(info.getSerfeeDir()!=null && !"".equals(info.getSerfeeDir())){
				File file = new File(info.getSerfeeDir());
				if(file.exists()){
					flag = true;
					DownloadUtil.download(request, response, file, info.getFeeMon()+"批次服务费计算文件.zip");
				}
			}
			if(!flag){
				File file = serfeeMonInfoService.genSerfeeCalFile(info);
				DownloadUtil.download(request, response, file, file.getName());
			}
		} catch(Exception e){
			logger.error("下载服务费计算结果文件失败,错误信息：",e);
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
	
	@RequestMapping("downModel")
	public void downModel(HttpServletRequest request,HttpServletResponse response,CmsSerfeeMonInfo info){
		try{
			File file = serfeeMonInfoService.genSerfeeModelFile(info);
			DownloadUtil.download(request, response, file, file.getName());
		} catch(Exception e){
			logger.error("下载失败,错误信息：",e);
			try {
				show_msg(response, "下载失败,"+e.getMessage());
			} catch (IOException e1) {
				logger.error("下载失败,错误信息："+e1.getStackTrace());
				e1.printStackTrace();
			}
		}
	}
	@RequestMapping("uploadModifyFile")
	public void uploadModifyFile(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request,HttpServletResponse response){
		PrintWriter pw = null;
    	try {
    		pw = response.getWriter();
    		//文件上传
    		response.setContentType("text/html;charset=UTF-8");
    		String fileName = file.getOriginalFilename();
    		String path = PropertyUtil.getPropertyValue("SER_FEE_MODI_PATH")+fileName;
			FileUtils.writeByteArrayToFile(new File(path), file.getBytes());
			
			//处理数据
			SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
			String feeMon = request.getParameter("feeMon");
			serfeeMonInfoService.updateSerfeeByModiFile(new File(path),feeMon,user);
			
			pw.write("{\"msg\":\"success\"}");
		} catch (Exception e) {
			logger.error("上传失败,错误信息：",e);
			pw.write("{\"msg\":\""+e.getMessage()+"\"}");
		}
	}
	
	@RequestMapping("confirm")
	public String confirm(HttpServletRequest request, CmsSerfeeMonInfo info, CmsDealerSerfeeFlow flow){
		try{
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			Task task = workflowService.getTask(user.getUserId()+"", info.getFeeMon());
			
			if(task == null){
				throw new RuntimeException("流程错误.");
			}
			
			flow.setAction(SerfeeActionEnum.RECHECK);
			serfeeMonInfoService.saveCompleteTask(info, task.getId(), flow , user);
			request.setAttribute("msg", "操作成功!");
		} catch(Exception e){
			logger.error("提交确认服务费计算结果失败,",e);
			request.setAttribute("msg", "操作失败,"+e.getMessage());
		}
		return "fee/serfee/list";
	}
	
	@RequestMapping("listFlow")
	@ResponseBody
	public Map<String, Object> listFlow(HttpServletRequest request, CmsDealerSerfeeFlow flow){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsDealerSerfeeFlow> page = serfeeMonInfoService.pageList(flow, pn, "feeMon");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	@RequestMapping("forceEnd")
    public String forceEnd(HttpServletRequest request){
    	try{
    		String taskId = request.getParameter("taskId");
    		String feeMon = request.getParameter("feeMon");
    		serfeeMonInfoService.updateEndProcess(taskId, feeMon);
    		request.setAttribute("msg", "操作成功!");
    	} catch(Exception e){
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    		logger.error("强制结束流程失败,",e);
    	}
    	return "workflow/monitor/serfeeMonitor";
    }
}
