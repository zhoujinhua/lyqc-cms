package com.liyun.car.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyun.car.common.entity.Page;
import com.liyun.car.system.entity.SyShortmsgLog;
import com.liyun.car.system.service.ShortmsgLogService;

@Controller
@RequestMapping("sms")
public class ShortmsgLogController {

	@Autowired
	private ShortmsgLogService shortmsgLogService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyShortmsgLog log){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		Map<String, Object> map = new HashMap<String,Object>();
		Page<SyShortmsgLog> page = shortmsgLogService.pageList(log, pn, "");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
}
