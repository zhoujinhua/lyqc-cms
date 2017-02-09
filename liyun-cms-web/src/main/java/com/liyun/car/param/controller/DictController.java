package com.liyun.car.param.controller;

import java.util.HashMap;
import java.util.List;
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
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.param.entity.SyDict;
import com.liyun.car.param.service.DictService;


@Controller
@RequestMapping("argControl")
public class DictController {
	
    @Autowired
    private DictService argControlService;
    
    private Logger logger = LoggerFactory.getLogger(DictController.class);
    
    @RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyDict arg){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		Map<String, Object> map = new HashMap<String,Object>();
		if(arg.getDict() == null){
			arg.setDict(new SyDict(null,"0"));
		}
		Page<SyDict> page = argControlService.pageList(arg, pn, "name","code","status","dict.code");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
    
    @RequestMapping("listDict")
	@ResponseBody
	public Map<String,Object> listDict(HttpServletRequest request, SyDict arg){
		Map<String, Object> map = new HashMap<String,Object>();
		List<SyDict> list = argControlService.getList(arg, "code");
		
		map.put("aaData", list);
	    return map;
	}
    
    @RequestMapping("save")
    public String saveArgControl(HttpServletRequest request, SyDict arg) {
    	try{
    		argControlService.saveArgControl(arg);
    		request.setAttribute("msg", "操作成功");
    	} catch(Exception e){
    		logger.error("保存失败,错误信息：",e);
    		request.setAttribute("arg", arg);
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    		return "param/dict/add";
    	}
    	if(arg.getDict()!=null && !arg.getDict().getCode().equals("0")){
    		return "redirect:detail?id="+arg.getDict().getId();
    	}
    	return "param/dict/list";
    }
    
    @RequestMapping("edit")
    public String edit(HttpServletRequest request, SyDict arg) {
    	arg = argControlService.getEntityById(SyDict.class, arg.getId(), true);
    	request.setAttribute("arg", arg);
    	return "param/dict/add";
    }
    
    @RequestMapping("delete")
    public String deleteArgControls(HttpServletRequest request, SyDict arg) {
    	try{
    		argControlService.deleteArgControl(arg);
    		request.setAttribute("msg", "操作成功");
    	} catch(Exception e){
    		logger.error("停用/启用失败,错误信息：",e);
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    	}
    	return "param/dict/list";
    }
    
    @RequestMapping("detail")
    public String detail(HttpServletRequest request, SyDict arg) {
    	arg = argControlService.getEntityById(SyDict.class, arg.getId(), true);

    	SyDict dict = new SyDict();
    	dict.setDict(new SyDict(null,arg.getCode()));
    	List<SyDict> list = argControlService.getEntitysByParams(dict, OperMode.EQ, "dict.code");
    	
    	request.setAttribute("arg", arg);
    	request.setAttribute("list", list);
    	return "param/dict/detail";
    }
    
}
