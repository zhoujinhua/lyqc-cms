package com.liyun.car.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyun.car.system.entity.vo.VUserRole;
import com.liyun.car.system.service.VUserRoleService;

@Controller
@RequestMapping("userRole")
public class UserRoleController {

private Logger logger = LoggerFactory.getLogger(UserRoleController.class);
	
	@Autowired
	private VUserRoleService userRoleService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, VUserRole userRole){
		Map<String, Object> map = new HashMap<String,Object>();
		List<VUserRole> list = userRoleService.getList(userRole ,"userPosition","orgCode");
		
		map.put("aaData", list);
	    return map;
	}
}
