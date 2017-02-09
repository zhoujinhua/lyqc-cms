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
import com.liyun.car.param.entity.SyModel;
import com.liyun.car.param.service.ModelService;
import com.liyun.car.system.entity.SyUser;

@Controller
@RequestMapping("model")
public class ModelController {

	/**
	 * 模型管理
	 */
	@Autowired
	private ModelService modelService;
	
	private Logger logger = LoggerFactory.getLogger(ModelController.class);
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyModel model){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		Map<String, Object> map = new HashMap<String,Object>();
		Page<SyModel> page = modelService.pageList(model, pn, "name","code","modelType","status");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,HttpServletResponse response,SyModel model){
		model = modelService.getEntityById(SyModel.class, model.getId(), true);
		request.setAttribute("model", model);
		return "param/model/add";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, SyModel model){
		try{
			SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
			
			modelService.saveModel(model, user);
			request.setAttribute("msg", "保存成功!");
		}catch(Exception e){
			logger.error("保存失败,错误信息：",e);
			request.setAttribute("msg","保存失败,"+e.getMessage());
			request.setAttribute("model", model);
			return "system/model/add";
		}
		return "param/model/list";
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request,HttpServletResponse response,SyModel model){
		model = modelService.getEntityById(SyModel.class, model.getId(), true);
		request.setAttribute("model", model);
		return "param/model/view";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, SyModel model){
		try{
			modelService.deleteEntity(model);
			request.setAttribute("msg", "删除成功!");
		}catch(Exception e){
			logger.error("删除失败,错误信息：",e);
			request.setAttribute("msg","删除失败,"+e.getMessage());
		}
		return "param/model/list";
	}
}
