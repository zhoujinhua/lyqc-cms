package com.liyun.car.fee.controller;

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

import com.liyun.car.activity.entity.CmsActivityRule;
import com.liyun.car.activity.enums.RuleLevelEnum;
import com.liyun.car.activity.service.ActivityRuleService;
import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonContr;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonDealer;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonDtl;
import com.liyun.car.fee.entity.vo.VDealerSerfeeMonReach;
import com.liyun.car.fee.service.DealerSerfeeMonDtlService;
import com.liyun.car.loan.entity.CaAppInfo;
import com.liyun.car.loan.service.AppInfoService;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.service.VUserRoleService;

/**
 * 活动达标详情
 * @author zhoufei
 *
 */
@Controller
@RequestMapping("feeDetail")
public class DealerSerfeeMonDtlController {
	
	private Logger logger = LoggerFactory.getLogger(DealerSerfeeMonDtlController.class);
	
	@Autowired
	private DealerSerfeeMonDtlService dealerSerfeeMonDtlService;
	
	@Autowired
	private VUserRoleService vUserRoleService;

	@Autowired
	private AppInfoService appInfoService;
	
	@Autowired
	private ActivityRuleService activityRuleService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request , CmsDealerSerfeeMonDtl dtl){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);

		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsDealerSerfeeMonDtl> page = dealerSerfeeMonDtlService.pageList(dtl, pn, "feeMon","companyCode","companyName","acttCode");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("appList")
	@ResponseBody
	public Map<String,Object> appList(HttpServletRequest request , CaAppInfo info){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);

		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
		boolean am = vUserRoleService.hasRole(user, "AM");
		Map<String, Object> map = new HashMap<String,Object>();
		info.setStatus("32");
		Page<CaAppInfo> page = dealerSerfeeMonDtlService.pageList(info, pn, "appCode","companyCode","companyName","loanTime","status");
		
		map.put("user", user);
		map.put("am", am);
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("apDetail")
	public String apDetail(HttpServletRequest request, CaAppInfo info){
		try{
			List<CaAppInfo> appInfos = appInfoService.getEntitysByParams(info, OperMode.EQ, "appCode");
			
			request.setAttribute("info", appInfos.get(0));
		}catch(Exception e){
			logger.error("加载申请单数据失败,",e);
			return "fee/feeDetail/list";
		}
		return "fee/feeDetail/apDetail";
	}
	
	@RequestMapping("appRuleList")
	@ResponseBody
	public Map<String,Object> appRuleList(HttpServletRequest request, CmsDealerSerfeeMonContr contr){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);

		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsDealerSerfeeMonContr> page = dealerSerfeeMonDtlService.pageList(contr, pn, "appCode");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("subList")
	@ResponseBody
	public Map<String,Object> subList(HttpServletRequest request , VDealerSerfeeMonReach reach){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);

		Map<String, Object> map = new HashMap<String,Object>();
		Page<VDealerSerfeeMonReach> page = dealerSerfeeMonDtlService.pageList(reach, pn, "acttCode","companyCode","companyName","ruleName");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("detail")
	@ResponseBody
	public Map<String,Object> detail(HttpServletRequest request,  VDealerSerfeeMonReach reach){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);

		Map<String, Object> map = new HashMap<String,Object>();
		Page<?> page = null;
		CmsActivityRule rule = activityRuleService.getEntityById(CmsActivityRule.class, reach.getRuleId(), false);

		if(rule.getRuleLvl() == RuleLevelEnum.APP){
			CmsDealerSerfeeMonContr contr = new CmsDealerSerfeeMonContr(reach.getFeeMon(),reach.getCompanyCode(),reach.getRuleId());
			page = dealerSerfeeMonDtlService.pageList(contr, pn, "feeMon","companyCode","ruleId");
		} else {
			CmsDealerSerfeeMonDealer dealer = new CmsDealerSerfeeMonDealer(reach.getFeeMon(),reach.getCompanyCode(),reach.getRuleId());
			page = dealerSerfeeMonDtlService.pageList(dealer, pn,  "feeMon","companyCode","ruleId");
		}

		map.put("ruleLvl", rule.getRuleLvl() == RuleLevelEnum.APP ? true:false);
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
}
