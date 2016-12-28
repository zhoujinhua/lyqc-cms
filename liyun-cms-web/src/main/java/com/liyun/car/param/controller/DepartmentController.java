package com.liyun.car.param.controller;

import java.util.ArrayList;
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
import com.liyun.car.param.entity.SyDepartment;
import com.liyun.car.param.service.DepartmentService;

@Controller
@RequestMapping("department")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	private Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyDepartment department){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		Map<String, Object> map = new HashMap<String,Object>();
		Page<SyDepartment> page = departmentService.pageList(department, pn, "departmentName","departmentLevel","status");
		//departmentService.insertDepartment();
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());	
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("listDepart")
	@ResponseBody
	public Map<String, Object> listDepart(HttpServletRequest request, SyDepartment department){
		Map<String, Object> map = new HashMap<String,Object>();
		/*if(department.getDepartmentLevel()!=null && department.getDepartmentLevel() == DepartmentLevelEnum.LEVELTWO){
			department.setDepartmentLevel(DepartmentLevelEnum.LEVELONE);
		}
		else if(department.getDepartmentLevel()!=null && department.getDepartmentLevel() ==  DepartmentLevelEnum.LEVELTHR){
			department.setDepartmentLevel(DepartmentLevelEnum.LEVELTHR);
		} else if(department.getDepartmentLevel()!=null && department.getDepartmentLevel() == DepartmentLevelEnum.LEVELONE){
			map.put("aaData", null);
			return map;
		}*/
		List<SyDepartment> list = departmentService.getList(department,"departmentLevel","status");	
		if(department.getDepartmentId()!=null){
			List<SyDepartment> tempList = new ArrayList<>();
			for(SyDepartment syDepartment : list){
				if(syDepartment.getDepartmentId().intValue() == department.getDepartmentId().intValue()){
					tempList.add(syDepartment);
				}
			}
			list.removeAll(tempList);
		}
		
		map.put("aaData", list);
		return map;
	}
	
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,HttpServletResponse response,SyDepartment department){
		department = departmentService.getEntityById(SyDepartment.class, department.getDepartmentId(), true);
		
		request.setAttribute("department", department);
		return "param/department/add";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, SyDepartment department){
		try {
			departmentService.saveDepartment(department);
			request.setAttribute("msg","操作成功!");
		} catch(Exception e){
			logger.error("操作失败,错误信息：",e);
			request.setAttribute("msg","操作失败,"+e.getMessage());
			request.setAttribute("department", department);
			return "system/department/add";
		}
		return "param/department/list";
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request,HttpServletResponse response,SyDepartment department){
		department = departmentService.getEntityById(SyDepartment.class, department.getDepartmentId(), true);
		
		request.setAttribute("department", department);
		return "param/department/view";
	}
	
}
