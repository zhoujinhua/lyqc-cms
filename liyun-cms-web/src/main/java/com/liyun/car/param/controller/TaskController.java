package com.liyun.car.param.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyun.car.common.entity.Page;
import com.liyun.car.core.utils.ReturnUitl;
import com.liyun.car.param.entity.SyTask;
import com.liyun.car.param.service.TaskService;
import com.liyun.car.system.entity.SyItem;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.service.UserService;

@Controller
@RequestMapping("task")
public class TaskController {

	private Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyTask task){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		Map<String, Object> map = new HashMap<String,Object>();
		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
		Page<SyTask> page = taskService.pageList(task, user, pn);
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());	
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("finlist")
	@ResponseBody
	public Map<String,Object> finlist(HttpServletRequest request, String subject, String crtTime){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
		Page<?> page = taskService.pageList(user,subject,crtTime, pn);
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());	
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("check")
	public void check(HttpServletRequest request, HttpServletResponse response, SyTask task){
		task = taskService.getEntityById(SyTask.class, task.getId(), false);
		if(task!=null){
			SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
			String itemId = task.getType().getItemId();
			SyItem item = userService.userHasItem(user, itemId);
			if(item!=null){
				ReturnUitl.write(response, 1, item.getItemLocation());
			} else {
				ReturnUitl.write(response, 0, "拒绝访问，您暂无此访问权限.");
			}
		} else {
			ReturnUitl.write(response, 0, "任务已不存在.");
		}
	}
	
}
