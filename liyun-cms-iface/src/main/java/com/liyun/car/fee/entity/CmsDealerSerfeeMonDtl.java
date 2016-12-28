package com.liyun.car.fee.entity;

import java.io.Serializable;
import java.util.Date;

public class CmsDealerSerfeeMonDtl implements Serializable{

	private String feeMon;
	private String companyCode;
	private String companyName;
	private String acttCode;
	private String topParamEn;
	private String subParamEn;
	private String subParamNm;
	private Double paramAmt;
	private Date upTime;
	
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
	public String getTopParamEn() {
		return topParamEn;
	}
	public void setTopParamEn(String topParamEn) {
		this.topParamEn = topParamEn;
	}
	public String getSubParamEn() {
		return subParamEn;
	}
	public void setSubParamEn(String subParamEn) {
		this.subParamEn = subParamEn;
	}
	public String getSubParamNm() {
		return subParamNm;
	}
	public void setSubParamNm(String subParamNm) {
		this.subParamNm = subParamNm;
	}
	public Double getParamAmt() {
		return paramAmt;
	}
	public void setParamAmt(Double paramAmt) {
		this.paramAmt = paramAmt;
	}
	public Date getUpTime() {
		return upTime;
	}
	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}
	
}
