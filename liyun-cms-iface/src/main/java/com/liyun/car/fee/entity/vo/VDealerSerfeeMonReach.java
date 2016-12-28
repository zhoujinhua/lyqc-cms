package com.liyun.car.fee.entity.vo;

import java.io.Serializable;
import java.util.Date;

import com.liyun.car.activity.entity.CmsActivityRule;

public class VDealerSerfeeMonReach implements Serializable{

	private String feeMon;
	private String companyCode;
	private String companyName;
	private String acttCode;
	private String subParamNm;
	private Integer ruleId;
	private String ruleName;
	private Double ruleAmt;
	private Date crtTime;
	
	private CmsActivityRule rule;
	
	public String getFeeMon() {
		return feeMon;
	}
	public void setFeeMon(String feeMon) {
		this.feeMon = feeMon;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getActtCode() {
		return acttCode;
	}
	public void setActtCode(String acttCode) {
		this.acttCode = acttCode;
	}
	public String getSubParamNm() {
		return subParamNm;
	}
	public void setSubParamNm(String subParamNm) {
		this.subParamNm = subParamNm;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Double getRuleAmt() {
		return ruleAmt;
	}
	public void setRuleAmt(Double ruleAmt) {
		this.ruleAmt = ruleAmt;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public CmsActivityRule getRule() {
		return rule;
	}
	public void setRule(CmsActivityRule rule) {
		this.rule = rule;
	}
	
}
