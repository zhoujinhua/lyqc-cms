package com.liyun.car.activity.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.liyun.car.activity.entity.CmsActivityInfo;
import com.liyun.car.activity.enums.ActivityStatusEnum;
import com.liyun.car.activity.service.ActivityInfoService;
import com.liyun.car.common.entity.Page;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("activity")
public class ActivityInfoController {
	
	private Logger logger = LoggerFactory.getLogger(ActivityInfoController.class);
	
	@Autowired
	private ActivityInfoService activityInfoService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request , CmsActivityInfo info){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsActivityInfo> page = activityInfoService.pageList(info, pn,"acttCode","acttNm","stt");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, CmsActivityInfo info){
		info = activityInfoService.getEntityByCode(CmsActivityInfo.class, info.getActtCode(), true);
		
		request.setAttribute("activityInfo", info);
		return "activity/activity/add";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, CmsActivityInfo activityInfo){
		try{
			activityInfoService.saveActivityInfo(activityInfo);
			request.setAttribute("msg","操作成功！");
		} catch (Exception e){
			logger.error("保存或者更新活动信息失败,错误信息：",e);
			request.setAttribute("msg", "操作失败,"+e.getMessage());
			request.setAttribute("activityInfo", activityInfo);
			return "activity/activity/add";
		}
		return "activity/activity/list";
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request,CmsActivityInfo activityInfo){
		activityInfo = activityInfoService.getEntityByCode(CmsActivityInfo.class, activityInfo.getActtCode(), true);
		
		request.setAttribute("activityInfo", activityInfo);
		return "activity/activity/view";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, CmsActivityInfo activityInfo){
		try{
			activityInfoService.deleteActivityInfo(activityInfo);
			request.setAttribute("msg", "删除成功!");
		} catch(Exception e){
			logger.error("删除活动信息失败,错误信息：",e);
			request.setAttribute("msg", "删除失败,"+e.getMessage());
		}
		return "activity/activity/list";
	}
	
	
	
	@RequestMapping("changeStatus")
	public String changeStatus(HttpServletRequest request, CmsActivityInfo activityInfo){
		try{
			activityInfo = activityInfoService.getEntityByCode(CmsActivityInfo.class, activityInfo.getActtCode(), false);
			if(activityInfo.getStt() == ActivityStatusEnum.ON){
				activityInfo.setStt(ActivityStatusEnum.OFF);
			} else {
				activityInfo.setStt(ActivityStatusEnum.ON);
			}
			activityInfoService.updateEntity(activityInfo, "stt");
			request.setAttribute("msg", "操作成功!");
		} catch(Exception e){
			logger.error("活动状态切换失败,",e);
			request.setAttribute("msg", "操作失败,"+e.getMessage());
		}
		return "activity/activity/list";
	}
	
	@RequestMapping("getTree")
    public void getTree(HttpServletRequest request, HttpServletResponse response, CmsActivityInfo activityInfo){
    	PrintWriter pw = null;
		try{
			pw = response.getWriter();
			
	    	List<String> treeList = activityInfoService.getDealerTree(activityInfo);
	    	pw.write(JSONArray.fromObject(treeList).toString());
		} catch(Exception e){
			logger.error("生成(经销商树)失败,错误信息：",e);
		}
    }
	
	@RequestMapping("upload")
	public void upload(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request, HttpServletResponse response){
    	PrintWriter pw = null;
    	try {
    		pw = response.getWriter();
    		response.setContentType("text/html;charset=UTF-8");
			
    		List<String> treeList = activityInfoService.getUploadTree(file);
			pw.print(JSONArray.fromObject(treeList).toString());
    	} catch (IOException e) {
    		logger.error("任务失败,错误信息：",e);
    		pw.print("{\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();
		}
    }
}
