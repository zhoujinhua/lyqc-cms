package com.liyun.car.activity.controller;

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

import com.liyun.car.activity.entity.CmsActivityRule;
import com.liyun.car.activity.service.ActivityRuleService;
import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.core.utils.ReturnUitl;
import com.liyun.car.loan.entity.CaGpsRule;
import com.liyun.car.loan.entity.CaProduct;
import com.liyun.car.loan.entity.CaRateRule;
import com.liyun.car.loan.entity.UaChannel;
import com.liyun.car.param.entity.SyDict;
import com.liyun.car.param.service.DictService;
import com.liyun.car.param.service.DictionaryRegService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("rule")
public class ActivityRuleController {

	@Autowired
	private ActivityRuleService activityRuleService;
	
	@Autowired
	private DictService argControlService;
	
	@Autowired
	private DictionaryRegService dictionaryRegService;
	
	private Logger logger = LoggerFactory.getLogger(ActivityRuleController.class);
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request , CmsActivityRule rule){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsActivityRule> page = activityRuleService.pageList(rule, pn,"ruleNm","ruleLvl","activityInfo.acttCode");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("listRule")
	@ResponseBody
	public Map<String,Object> listRule(HttpServletRequest request, CmsActivityRule rule, String ruleIds){
		Map<String, Object> map = new HashMap<String,Object>();
		System.out.println(request.getAttribute("ruleIds"));
		List<CmsActivityRule> list = activityRuleService.getAvliaList(rule, ruleIds);
		
		map.put("aaData", list);
	    
	    return map;
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, CmsActivityRule rule){
		rule = activityRuleService.getEntityById(CmsActivityRule.class, rule.getRuleId(), true);
		
		request.setAttribute("rule", rule);
		return "activity/rule/add";
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request, CmsActivityRule rule){
		rule = activityRuleService.getEntityById(CmsActivityRule.class, rule.getRuleId(), true);
		
		request.setAttribute("rule", rule);
		return "activity/rule/view";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, CmsActivityRule rule){
		try{
			activityRuleService.saveActivityRule(rule);
			
			request.setAttribute("msg", "保存成功!");
			request.setAttribute("acttCode", rule.getActivityInfo().getActtCode());
			request.setAttribute("stt", rule.getActivityInfo().getStt().getValue());
		} catch(Exception e){
			logger.error("保存或者更新规则失败,错误信息：",e);
			request.setAttribute("rule", rule);
			request.setAttribute("msg", "保存失败,"+e.getMessage());
			return "activity/rule/add";
		}
		return "activity/rule/list";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, CmsActivityRule rule){
		try{
			request.setAttribute("acttCode", rule.getActivityInfo().getActtCode());
			request.setAttribute("stt", rule.getActivityInfo().getStt().getValue());
			
			activityRuleService.deleteActivityRule(rule);
			request.setAttribute("msg","操作成功!");
		} catch(Exception e){
			logger.error("删除规则失败,错误信息：",e);
			request.setAttribute("msg","操作失败,"+e.getMessage());
		}
		return "activity/rule/list";
	}
	
	@RequestMapping("getChannel")
	@ResponseBody
	public Map<String,Object> getChannel(HttpServletRequest request ){
		Map<String, Object> map = new HashMap<String,Object>();
		UaChannel channel = new UaChannel();
		channel.setStatus("1");
		List<UaChannel> list = activityRuleService.getList(channel,"status");
		
		map.put("aaData", list);
		return map;
	}
	
	@RequestMapping("getProduct")
	@ResponseBody
	public Map<String,Object> getProduct(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String,Object>();
		CaProduct product = new CaProduct();
		product.setStatus(1);
		List<CaProduct> list = activityRuleService.getList(product,"status");
		
		map.put("aaData", list);
	    return map;
	}
	
	
	@RequestMapping("getRate")
	@ResponseBody
	public Map<String,Object> getRate(HttpServletRequest request ){
		Map<String, Object> map = new HashMap<String,Object>();
		CaRateRule rate = new CaRateRule();
		List<CaRateRule> list = activityRuleService.getList(rate,null);
		
		map.put("aaData", list);
	    return map;
	}
	
	@RequestMapping("getGps")
	@ResponseBody
	public Map<String,Object> getGps(HttpServletRequest request ){
		Map<String, Object> map = new HashMap<String,Object>();
		CaGpsRule gps = new CaGpsRule();
		List<CaGpsRule> list = activityRuleService.getList(gps,null);
		
		map.put("aaData", list);
	    return map;
	}
	
	@RequestMapping("getComRate")
	@ResponseBody
	public Map<String,Object> getComRate(HttpServletRequest request ){
		Map<String, Object> map = new HashMap<String,Object>();
		SyDict arg = new SyDict();
		arg.setStatus(ParamStatusEnum.ON);
		arg.setDict(new SyDict(null,"rxsfl"));
		List<SyDict> list = argControlService.getList(arg, "status","dict.code");
		
		map.put("aaData", list);
	    return map;
	}
	
	@RequestMapping("getWorkCity")
	public void getWorkCity(HttpServletRequest request, HttpServletResponse response){
		PrintWriter pw = null;
		try{
			pw = response.getWriter();
			List<String> treeList = dictionaryRegService.getCityTree();
			pw.println(JSONArray.fromObject(treeList).toString());
		} catch(Exception e){
			logger.error("工作城市树封装失败,",e);
		}
	}
	
	@RequestMapping("getActivityTree")
	public void getActivityTree(HttpServletRequest request, HttpServletResponse response){
		PrintWriter pw = null;
		try{
			pw = response.getWriter();
			String acttCode = request.getParameter("acttCode");
			List<String> treeList = activityRuleService.getActivityTree(acttCode);
			pw.println(JSONArray.fromObject(treeList).toString());
		} catch(Exception e){
			logger.error("活动树封装失败,",e);
		}
	}
	
	@RequestMapping("saveCopy")
	public void saveCopy(HttpServletRequest request, HttpServletResponse response){
		try{
			String ids = request.getParameter("ids");
			String acttCode = request.getParameter("acttCode");
			activityRuleService.saveActivityRuleCopy(acttCode, ids);
			ReturnUitl.write(response, 1);
		} catch(Exception e){
			ReturnUitl.write(response, 0,"操作失败,"+e.getMessage());
		}
	}
}
