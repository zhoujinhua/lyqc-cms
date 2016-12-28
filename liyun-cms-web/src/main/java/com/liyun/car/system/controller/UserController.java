package com.liyun.car.system.controller;

import java.io.PrintWriter;
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
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.enums.UserStatusEnum;
import com.liyun.car.system.service.PermSetService;
import com.liyun.car.system.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PermSetService permSetService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request ,SyUser user){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		Map<String, Object> map = new HashMap<String,Object>();
		Page<SyUser> page = userService.pageList(user, pn);
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, SyUser user) {
		try {
			userService.saveEntity(user);
			request.setAttribute("msg", "保存成功");
		} catch (Exception e) {
			logger.error("用户保存失败.",e);
			request.setAttribute("msg", e.getMessage());
			
			request.setAttribute("user", user);
			return "system/user/add";
		}
		return "system/user/list";
	}
	
	@RequestMapping("update")
	public String update(HttpServletRequest request, SyUser user) {
		try {
			userService.updateEntity(user,true, "");
			request.setAttribute("msg", "保存成功");
		} catch (Exception e) {
			logger.error("用户更新失败.",e);
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", user);
			return "system/user/add";
		}
		return "system/user/list";
	}
	
	@RequestMapping("initPwd")
	public String initPwd(HttpServletRequest request, SyUser user) {
		try {
			userService.updateUserPassword(user);
			request.setAttribute("msg", "用户重置密码成功!");
		} catch (Exception e) {
			logger.error("用户密码重置失败.",e);
			request.setAttribute("msg", "用户重置密码失败," + e.getMessage());
		}
		return "system/user/list";
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request, SyUser user) {
		try {
			user.setUserStatus(UserStatusEnum.F);
			userService.updateEntity(user, "userStatus");
			request.setAttribute("msg", "用户停用成功!");
		} catch (Exception e) {
			logger.error("用户停用失败.",e);
			request.setAttribute("msg", "用户停用失败," + e.getMessage());
		}
		return "system/user/list";
	}
	
	@RequestMapping("activate")
	public String activate(HttpServletRequest request, SyUser user) {
		try {
			user.setUserStatus(UserStatusEnum.N);
			this.userService.updateEntity(user, "userStatus");
			request.setAttribute("msg", "用户激活成功!");
		} catch (Exception e) {
			logger.error("用户激活失败.",e);
			request.setAttribute("msg", "用户激活失败," + e.getMessage());
		}
		return "system/user/list";
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, SyUser user) {
		user = userService.getEntityById(SyUser.class,user.getUserId(),true);
		request.setAttribute("user", user);
		return "system/user/add";
	}
	@RequestMapping("modify")
	public String modify(HttpServletRequest request, SyUser user) {
		user = (SyUser) request.getSession().getAttribute("loginUser");
		request.setAttribute("user", user);
		return "system/user/modify";
	}
	@RequestMapping("saveModify")
	public String saveModify(HttpServletRequest request, SyUser user) {
		try{
			userService.updateEntity(user, "address","trueName","sex","phone","email","cardType","cardId","postalCode");
			user = userService.getEntityById(SyUser.class, user.getUserId(), true);
			request.getSession().setAttribute("loginUser", user);
			request.setAttribute("msg", "修改成功!");
		} catch(Exception e){
			request.setAttribute("msg", "修改失败,请联系管理员,"+e.getMessage());
		}
		request.setAttribute("user", user);
		return "system/user/modify";
	}
	
	@RequestMapping("detail")
	public String view(HttpServletRequest request, SyUser user) {
		user = userService.getEntityById(SyUser.class,user.getUserId(),true);
		request.setAttribute("user", user);
		return "system/user/detail";
	}
	
	@RequestMapping("viewPermSetTree")
	public void viewPermSetTree(HttpServletRequest request, HttpServletResponse response, SyUser user) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			
			String jsonArray = permSetService.getPermSetTree(user);
			pw.print(jsonArray);
		} catch (Exception e) {
			logger.error("生成权限集树失败.",e);
		}
	}
	
	@RequestMapping("setPermSet")
	public String setPermSet(HttpServletRequest request, HttpServletResponse response, SyUser user) {
		try {
			String ids = request.getParameter("ids");
			String[] permIds = ids.split(",");
			userService.updateSyUserPermSets(user, permIds);
			request.setAttribute("msg","权限集设置成功!");
		} catch (Exception e) {
			logger.error("用户设置权限集失败.",e);
			request.setAttribute("msg","权限集设置失败,"+e.getMessage());
		}
		return "system/user/list";
	}
}
