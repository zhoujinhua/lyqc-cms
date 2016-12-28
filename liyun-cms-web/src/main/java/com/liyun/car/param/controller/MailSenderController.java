package com.liyun.car.param.controller;

import java.util.Date;
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

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.param.entity.SyMailSender;
import com.liyun.car.param.service.MailSenderService;

@Controller
@RequestMapping("sender")
public class MailSenderController {

private Logger logger = LoggerFactory.getLogger(MailSenderController.class);
	
	@Autowired
	private MailSenderService mailSenderService;

	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request,SyMailSender sender){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		Map<String, Object> map = new HashMap<String,Object>();
		Page<SyMailSender> page = mailSenderService.pageList(sender, pn, "userName");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request , SyMailSender sender){
		try{
			if(sender.getId() != null){
				mailSenderService.updateEntity(sender,"userName","email","password","isDefault","status","remark");
			} else {
				sender.setCreateTime(new Date());
				mailSenderService.saveEntity(sender);
			}
		} catch(Exception e){
			logger.error("保存失败,",e);
			request.setAttribute("sender", sender);
			
			return "sender/add";
		}
		return "param/sender/list";
	}
	
	@RequestMapping("add")
	public String add(HttpServletRequest request, SyMailSender sender){
		sender = mailSenderService.getEntityById(SyMailSender.class,sender.getId(),false);
		
		request.setAttribute("sender", sender);
		return "param/sender/add";
	}
	
	@RequestMapping("setStatus")
	public String setStatus(HttpServletRequest request , SyMailSender sender){
		try{
			sender = mailSenderService.getEntityById(SyMailSender.class,sender.getId(),false);
			if(sender.getStatus() == ParamStatusEnum.ON){
				sender.setStatus(ParamStatusEnum.OFF);
			} else {
				sender.setStatus(ParamStatusEnum.ON);
			}
			mailSenderService.updateEntity(sender, "status");
			request.setAttribute("msg", "状态设置成功!");
		} catch(Exception e){
			logger.error("设置状态失败,",e);
			request.setAttribute("msg", "状态设置失败,"+e.getMessage());
		}
		return "param/sender/list";
	}
}
