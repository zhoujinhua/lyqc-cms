package com.liyun.car.report.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liyun.car.common.utils.PropertyUtil;
import com.liyun.car.remote.utils.HttpUtils;
import com.liyun.car.remote.utils.SignUtil;
import com.liyun.car.report.entity.vo.ReportSearchDto;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("report")
public class ReportController {
	
	private Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	@RequestMapping("show")
	public String report(HttpServletRequest request,HttpServletResponse response,ReportSearchDto dto){
		try{
			String secretKey = PropertyUtil.getPropertyValue("DEALER_MONTH_REPORT_KEY");
			
			String url = PropertyUtil.getPropertyValue("DEALER_MONTH_REPORT_URL")+SignUtil.getQueryUrl(request, "dealerDimen", secretKey);
			String result = HttpUtils.invokeGet(null, url);
			if(result!=null && !result.isEmpty()){
				JSONObject jsonObject = JSONObject.fromObject(result);
				if(jsonObject.get("code")!=null ){
					if(Integer.parseInt(String.valueOf(jsonObject.get("code"))) == 0){
						JSONObject innerJsonObject = jsonObject.getJSONObject("data");
						request.setAttribute("currMonthMap", innerJsonObject.getJSONObject("currMonthMap"));
						request.setAttribute("preMonthMap", innerJsonObject.getJSONObject("preMonthMap"));
						request.setAttribute("currYearMap", innerJsonObject.getJSONObject("currYearMap"));
						request.setAttribute("monthTotalMap", innerJsonObject.getJSONObject("monthTotalMap"));
						request.setAttribute("yearTotalMap", innerJsonObject.getJSONObject("yearTotalMap"));
					} else {
						throw new RuntimeException(String.valueOf(jsonObject.get("error")));
					}
				} else {
					throw new RuntimeException("未返回任何数据...");
				}
			}
			url = PropertyUtil.getPropertyValue("DEALER_MONTH_REPORT_URL")+SignUtil.getQueryUrl(request, "customerDimen", secretKey);
			result = HttpUtils.invokeGet(null, url);
			if(result!=null && !result.isEmpty()){
				JSONObject jsonObject = JSONObject.fromObject(result);
				if(jsonObject.get("code")!=null){
					if(Integer.parseInt(String.valueOf(jsonObject.get("code"))) == 0){
						JSONObject innerJsonObject = jsonObject.getJSONObject("data");
						request.setAttribute("customerPrecentMap", innerJsonObject.getJSONObject("customerPrecentMap"));
						request.setAttribute("customerPrecentJsonMap", innerJsonObject.getJSONObject("customerPrecentJsonMap"));
					} else {
						throw new RuntimeException(String.valueOf(jsonObject.get("error")));
					}
				} else {
					throw new RuntimeException("未返回任何数据...");
				}
			}
		} catch(Exception e){
			logger.error("生成报告失败,",e);
			request.setAttribute("msg", "生成报告失败,"+e.getMessage());
			request.setAttribute("dto", dto);
			return "report/query";
		}
		return "report/report";
	}
	
}
