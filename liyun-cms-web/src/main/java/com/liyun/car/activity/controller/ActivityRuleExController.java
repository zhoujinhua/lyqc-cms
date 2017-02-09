package com.liyun.car.activity.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyun.car.activity.entity.CmsActivityRuleGroup;
import com.liyun.car.activity.service.ActivityRuleExService;
import com.liyun.car.common.entity.Page;

@Controller
@RequestMapping("ruleEx")
public class ActivityRuleExController {

	private Logger logger = LoggerFactory.getLogger(ActivityRuleExController.class);
	@Autowired
	private ActivityRuleExService activityRuleExService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request , CmsActivityRuleGroup ruleEx){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsActivityRuleGroup> page = activityRuleExService.pageList(ruleEx, pn,"exCode","exName","activityInfo.acttCode");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, CmsActivityRuleGroup group){
		try{
			activityRuleExService.saveActivityRuleEx(group);
			request.setAttribute("msg", "保存成功!");
			request.setAttribute("acttCode", group.getActivityInfo().getActtCode());
			request.setAttribute("stt", group.getActivityInfo().getStt().getValue());
		}catch(Exception e){
			request.setAttribute("group", group);
			logger.error("保存互斥规则失败,",e);
			request.setAttribute("msg", "保存互斥规则失败,"+e.getMessage());
			return "activity/ex/add.jsp";
		}
		return "activity/ex/list";
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, CmsActivityRuleGroup group){
		group = activityRuleExService.getEntityById(CmsActivityRuleGroup.class, group.getId(), true);
		request.setAttribute("group", group);
		request.setAttribute("acttCode", group.getActivityInfo().getActtCode());
		request.setAttribute("stt", group.getActivityInfo().getStt().getValue());
		return "activity/ex/add";
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request, CmsActivityRuleGroup group){
		group = activityRuleExService.getEntityById(CmsActivityRuleGroup.class, group.getId(), true);
		request.setAttribute("group", group);
		request.setAttribute("acttCode", group.getActivityInfo().getActtCode());
		request.setAttribute("stt", group.getActivityInfo().getStt().getValue());
		return "activity/ex/view";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, CmsActivityRuleGroup group){
		try{
			group = activityRuleExService.getEntityById(CmsActivityRuleGroup.class, group.getId(), true);
			request.setAttribute("group", group);
			request.setAttribute("acttCode", group.getActivityInfo().getActtCode());
			activityRuleExService.deleteGroup(group);
			request.setAttribute("msg", "操作成功!");
		}catch(Exception e){
			request.setAttribute("msg", "操作失败,"+e.getMessage());
			logger.error("删除互斥分组失败,",e);
		}
		return "activity/ex/list";
	}
}
