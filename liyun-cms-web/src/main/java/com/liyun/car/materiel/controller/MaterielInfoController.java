package com.liyun.car.materiel.controller;

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
import com.liyun.car.materiel.entity.CmsMaterielInfo;
import com.liyun.car.materiel.service.MaterielInfoService;

@Controller
@RequestMapping("materiel")
public class MaterielInfoController {

	private Logger logger = LoggerFactory.getLogger(MaterielInfoController.class);
	@Autowired
	private MaterielInfoService materielInfoService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request ,CmsMaterielInfo info){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		if(info.getMtrlTyp()!=null && info.getMtrlTyp().getCode().equals("")){
			info.setMtrlTyp(null);
		}
		Page<CmsMaterielInfo> page = materielInfoService.pageList(info, pn,"mtrlCode","mtrlNm","mtrlTyp");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("listOnline")
	@ResponseBody
	public Map<String,Object> listOnline(HttpServletRequest request, CmsMaterielInfo info){
		Map<String, Object> map = new HashMap<String,Object>();
		List<CmsMaterielInfo> list = materielInfoService.getList(info,"status");
		
		map.put("aaData", list);
	    return map;
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, CmsMaterielInfo info){
		info = materielInfoService.getEntityById(CmsMaterielInfo.class, info.getId(),false);
		
		request.setAttribute("info", info);
		return "materiel/info/add";
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request, CmsMaterielInfo info){
		info = materielInfoService.getEntityById(CmsMaterielInfo.class, info.getId(),false);
		
		request.setAttribute("info", info);
		return "materiel/info/view";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, CmsMaterielInfo info){
		try{
			materielInfoService.saveMaterielInfo(info);
			request.setAttribute("msg", "保存成功!");
		}catch(Exception e){
			logger.error("保存物料失败",e);
			request.setAttribute("msg", "保存失败,"+e.getMessage());
			return "materiel/info/add";
		}
		return "materiel/info/list";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, CmsMaterielInfo info){
		try{
			materielInfoService.deleteMaterielInfo(info);
			request.setAttribute("msg", "操作成功!");
		}catch(Exception e){
			logger.error("物料状态修改失败,",e);
			request.setAttribute("msg", "操作失败!");
		}
		return "materiel/info/list";
	}
}
