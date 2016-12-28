package com.liyun.car.param.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.param.entity.SyDictionaryReg;
import com.liyun.car.param.service.DictionaryRegService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("dictionary")
public class DictionaryRegController {

	private Logger logger = LoggerFactory.getLogger(DictionaryRegController.class);
	
	@Autowired
	private DictionaryRegService dictionaryRegService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request ,SyDictionaryReg reg){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		Map<String, Object> map = new HashMap<String,Object>();
		Page<SyDictionaryReg> page = dictionaryRegService.pageList(reg, pn);
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	/**
     * 加载城市和省份
     * @param request
     * @param response
     */
    @RequestMapping("loadPC")
    public void loadPC(HttpServletRequest request,HttpServletResponse response, SyDictionaryReg reg){
    	PrintWriter pw = null	;
    	try{
    		pw = response.getWriter();
    		List<SyDictionaryReg> regs = dictionaryRegService.getEntitysByParams(reg, OperMode.EQ, "regCodePar");
    		pw.print(JSONArray.fromObject(regs).toString());
    	} catch(Exception e){
    		logger.error("加载失败,错误信息：",e);
    		pw.print("");
    	}
    }
}
