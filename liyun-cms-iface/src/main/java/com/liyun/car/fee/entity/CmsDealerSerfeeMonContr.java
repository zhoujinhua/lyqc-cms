package com.liyun.car.fee.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyun.car.activity.entity.CmsActivityRule;
import com.liyun.car.activity.entity.CmsParamSub;

public class CmsDealerSerfeeMonContr implements Serializable{

	private String feeMon;
	private String appCode;
	private String companyCode;
	private String companyName;
	private String paymentChEn;
	private Double rLoanAmount;
	private Integer rLoanPeriods;
	private Double rGpsFee;
	private Double rComFee;
	private Date loanTime;
	private String acttCode;
	private String topParamEn;
	private CmsParamSub subParam;
	private Integer ruleId;
	private String ruleName;
	private Double ruleAmt;
	private Date crtTime;
	
	@JsonIgnore
	private CmsActivityRule rule;
	
	
	
	public CmsDealerSerfeeMonContr(String feeMon, String companyCode, Integer ruleId) {
		super();
		this.feeMon = feeMon;
		this.companyCode = companyCode;
		this.ruleId = ruleId;
	}
	public CmsDealerSerfeeMonContr() {
		super();
	}
	public String getFeeMon() {
		return feeMon;
	}
	public void setFeeMon(String feeMon) {
		this.feeMon = feeMon;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
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
	public String getPaymentChEn() {
		return paymentChEn;
	}
	public void setPaymentChEn(String paymentChEn) {
		this.paymentChEn = paymentChEn;
	}
	public Double getrLoanAmount() {
		return rLoanAmount;
	}
	public void setrLoanAmount(Double rLoanAmount) {
		this.rLoanAmount = rLoanAmount;
	}
	public Integer getrLoanPeriods() {
		return rLoanPeriods;
	}
	public void setrLoanPeriods(Integer rLoanPeriods) {
		this.rLoanPeriods = rLoanPeriods;
	}
	public Double getrGpsFee() {
		return rGpsFee;
	}
	public void setrGpsFee(Double rGpsFee) {
		this.rGpsFee = rGpsFee;
	}
	public Double getrComFee() {
		return rComFee;
	}
	public void setrComFee(Double rComFee) {
		this.rComFee = rComFee;
	}
	public Date getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}
	public String getActtCode() {
		return acttCode;
	}
	public void setActtCode(String acttCode) {
		this.acttCode = acttCode;
	}
	public String getTopParamEn() {
		return topParamEn;
	}
	public void setTopParamEn(String topParamEn) {
		this.topParamEn = topParamEn;
	}
	public CmsParamSub getSubParam() {
		return subParam;
	}
	public void setSubParam(CmsParamSub subParam) {
		this.subParam = subParam;
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
