package com.liyun.car.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyun.car.common.entity.Page;
import com.liyun.car.common.utils.BeanInvokeUtils;
import com.liyun.car.core.utils.ReturnUitl;
import com.liyun.car.workflow.entity.vo.CmsProcessDefinition;
import com.liyun.car.workflow.entity.vo.CmsTask;
import com.liyun.car.workflow.service.WorkflowService;

@Controller
@RequestMapping("workflow")
public class WorkflowController {

	@Autowired
	private WorkflowService workFlowService;
	private Logger logger = LoggerFactory.getLogger(WorkflowController.class);
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request ,String resourceName){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		Page<ProcessDefinition> page = workFlowService.getProcessDefinitions(resourceName, pn);
		List<CmsProcessDefinition> list = new ArrayList<>();
		for(ProcessDefinition pd : page.getItems()){
			CmsProcessDefinition cpd = new CmsProcessDefinition();
			BeanInvokeUtils.cloneMethod(pd, cpd, "id","name","key","version","deploymentId","resourceName","diagramResourceName");
			list.add(cpd);
		}
		map.put("aaData", list);
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("uploadProcessFile")
    public void uploadProcessFile(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request, HttpServletResponse response){
    	PrintWriter pw = null;
    	try {
    		pw = response.getWriter();
    		workFlowService.saveDeployNewProcess(file.getInputStream());
			pw.write("{\"msg\":\"success\"}");
		} catch (Exception e) {
			logger.error("上传失败,错误信息：",e);
			pw.write("{\"msg\":\""+e.getMessage()+"\"}");
		}
    }
	
	@RequestMapping("deleteProcess")
    public String deleteProcess(HttpServletRequest request, HttpServletResponse response){
    	String deploymentId = request.getParameter("deploymentId");
    	workFlowService.deleteProcessDef(deploymentId);
    	return "workflow/list";
    }
	@RequestMapping("viewDiagram")
    public void viewDiagram(HttpServletRequest request, HttpServletResponse response){
    	String deploymentId = request.getParameter("deploymentId");
    	String resourceName = request.getParameter("resourceName");
    	OutputStream outputStream = null;	    	
         try {
        	 response.setContentType("image/jpeg");
        	 response.setCharacterEncoding("utf-8");  
        	 InputStream in = workFlowService.getResourceAsStream(deploymentId, resourceName);
        	 
        	 outputStream = response.getOutputStream();
        	 
        	 int len=0;  
        	 byte[]buf=new byte[1024];  
        	 while((len=in.read(buf,0,1024))!=-1){  
        		 outputStream.write(buf, 0, len);
        	 }  
        	 outputStream.close();
			} catch (IOException e) {
				logger.error("流程查看失败,错误信息：",e);
				e.printStackTrace();
			}
    }
	
	/**
	 * 获取流程图坐标
	 */
	@RequestMapping("getCoordinate")
	public String getCoordinateByTaskId(HttpServletRequest request, HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		
		ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("height", activityImpl.getHeight());
		map.put("width", activityImpl.getWidth());
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		request.setAttribute("map", map);
		return "workflow/image";
	}
	
	/**
	 * 通过taskId获取流程图
	 */
	@RequestMapping("viewImage")
	public void getActivityImageByTaskId(HttpServletRequest request, HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		
		InputStream in = workFlowService.getActivityImageByTaskId(taskId);
		response.setContentType("image/png");
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			byte[] buffer = new byte[4 * 1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();
			outputStream.close();
		} catch(Exception e) {
			logger.error("IO异常", e);
			e.printStackTrace();
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch(Exception e1) {}
			}
		}
	}
	
	@RequestMapping("monitor")
	@ResponseBody
    public Map<String, Object> monitor(HttpServletRequest request, String status, String clazzName) throws JsonProcessingException{
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
    	Map<String, Object> map = new HashMap<String,Object>();
    	try {
    		Page<?> page = workFlowService.getMonitorList(status,clazzName,pn);
			
			map.put("aaData", page.getItems());
			map.put("recordsTotal", page.getCount());
			map.put("recordsFiltered", page.getCount());
		} catch (Exception e) {
			logger.error("监控失败,",e);
		}
    	 ObjectMapper mapper = new ObjectMapper();  
    	 System.out.println(mapper.writeValueAsString(map));
    	 
    	return map;
    }
	
	@RequestMapping("forceEnd")
    public void forceEnd(HttpServletRequest request, HttpServletResponse response, String taskId, String code, String clazzName){
    	try{
    		workFlowService.updateForceEnd(clazzName, taskId, code);
    		ReturnUitl.write(response, 1, "操作成功!");
    	} catch(Exception e){
    		logger.error("强制结束流程失败,",e);
    		ReturnUitl.write(response, 0, "操作失败!"+e.getMessage());
    	}
    }
	
	@RequestMapping("suspendActivate")
    public void suspendActivate(HttpServletRequest request, HttpServletResponse response, CmsTask task){
		try{
			if(task.getSuspensionState().equals("1")){
				workFlowService.updateSuspendProcess(task.getProcessInstanceId());
			} else {
				workFlowService.updateActivateProcess(task.getProcessInstanceId());
			}
    		ReturnUitl.write(response, 1, "操作成功!");
    	} catch(Exception e){
    		logger.error("挂起流程/激活失败,",e);
    		ReturnUitl.write(response, 0, "操作失败!"+e.getMessage());
    	}
    }
}
