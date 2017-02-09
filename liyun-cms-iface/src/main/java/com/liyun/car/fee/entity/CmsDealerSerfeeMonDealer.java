package com.liyun.car.fee.entity;

import java.io.Serializable;
import java.util.Date;

import com.liyun.car.activity.entity.CmsParamSub;

public class CmsDealerSerfeeMonDealer implements Serializable{

	private String feeMon;
	private String companyCode;
	private Integer contrCnt;
	private Double contrAmt;
	private String acttCode;
	private String companyName;
	private String topParamEn;
	private CmsParamSub subParam;
	private Integer ruleId;
	private String ruleName;
	private Double ruleAmt;
	private Date crtTime;
	
	
	public CmsDealerSerfeeMonDealer(String feeMon, String companyCode, Integer ruleId) {
		super();
		this.feeMon = feeMon;
		this.companyCode = companyCode;
		this.ruleId = ruleId;
	}
	public CmsDealerSerfeeMonDealer() {
		super();
	}
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
	public Integer getContrCnt() {
		return contrCnt;
	}
	public void setContrCnt(Integer contrCnt) {
		this.contrCnt = contrCnt;
	}
	public Double getContrAmt() {
		return contrAmt;
	}
	public void setContrAmt(Double contrAmt) {
		this.contrAmt = contrAmt;
	}
	public String getActtCode() {
		return acttCode;
	}
	public void setActtCode(String acttCode) {
		this.acttCode = acttCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	
	
}
