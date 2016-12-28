package com.liyun.car.activity.controller;

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

import com.liyun.car.activity.entity.CmsParamSub;
import com.liyun.car.activity.entity.CmsParamTop;
import com.liyun.car.activity.service.ParamService;
import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.enums.ParamStatusEnum;

@Controller
@RequestMapping("param")
public class ParamController {
	
	private Logger logger = LoggerFactory.getLogger(ParamController.class);
	
	@Autowired
	private ParamService paramService;

	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, CmsParamTop top){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsParamTop> page = paramService.pageList(top, pn,"topParamEn","topParamNm","stt");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("listSub")
	@ResponseBody
	public Map<String,Object> listSub(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String,Object>();
		CmsParamSub sub = new CmsParamSub();
		sub.setStt(ParamStatusEnum.ON);
		List<CmsParamSub> list = paramService.getList(sub, "stt");
		
		map.put("aaData", list);
	    return map;
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, CmsParamTop top){
		top = paramService.getEntityById(CmsParamTop.class, top.getId(), false);
		
		request.setAttribute("top", top);
		return "activity/param/update";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, CmsParamTop top){
		try{
			paramService.saveParamTop(top);
			request.setAttribute("msg", "保存成功!");
		}catch(Exception e){
			logger.error("操作顶级科目失败,错误信息：",e);
			request.setAttribute("msg", "保存失败,"+e.getMessage());
			request.setAttribute("top", top);
			return "activity/param/update";
		}
		return "activity/param/list";
	}
	
	@RequestMapping("view")
	@ResponseBody
	public Map<String,Object> view(HttpServletRequest request, CmsParamSub sub){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsParamSub> page = paramService.pageList(sub, pn, "paramTop.topParamEn");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("saveSub")
	public String saveSub(HttpServletRequest request, CmsParamSub sub){
		try{
			paramService.saveParamSub(sub);
			request.setAttribute("msg", "保存成功!");
		}catch(Exception e){
			logger.error("更新二级科目失败,错误信息：",e);
			request.setAttribute("msg", "保存失败,"+e.getMessage());
			request.setAttribute("sub", sub);
		}
		request.setAttribute("topParamEn", sub.getParamTop().getTopParamEn());
		return "activity/param/view";
	}
}
