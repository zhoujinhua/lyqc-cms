package com.liyun.car.activity.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.activity.enums.ActivityCycleEnum;
import com.liyun.car.activity.enums.ActivityStatusEnum;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.serializer.EnumSerializer;

public class CmsActivityInfo implements Serializable{
	
	private String acttCode;
	private String acttNm;
	private String subParamEn;
	private String subParamNm;
	private String topParamEn;
	private ActivityCycleEnum acttCyc;
	private String acttTyp;
	private BooleanEnum isDealer;
	private String acttDesc;
	private ActivityStatusEnum stt;
	private Date acttBegin;
	private Date acttEnd;
	
	@JsonIgnore
	private List<CmsActivityDealer> dealers;
	@JsonIgnore
	private List<CmsActivityRule> activityRules;
	@JsonIgnore
	private String companyCodes;
	@JsonIgnore
	private String companyNames;
	
	
	public String getActtCode() {
		return acttCode;
	}
	public void setActtCode(String acttCode) {
		this.acttCode = acttCode;
	}
	public String getActtNm() {
		return acttNm;
	}
	public void setActtNm(String acttNm) {
		this.acttNm = acttNm;
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
	public String getTopParamEn() {
		return topParamEn;
	}
	public void setTopParamEn(String topParamEn) {
		this.topParamEn = topParamEn;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public BooleanEnum getIsDealer() {
		return isDealer;
	}
	public void setIsDealer(BooleanEnum isDealer) {
		this.isDealer = isDealer;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public ActivityStatusEnum getStt() {
		return stt;
	}
	public void setStt(ActivityStatusEnum stt) {
		this.stt = stt;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public ActivityCycleEnum getActtCyc() {
		return acttCyc;
	}
	public void setActtCyc(ActivityCycleEnum acttCyc) {
		this.acttCyc = acttCyc;
	}
	public String getActtTyp() {
		return acttTyp;
	}
	public void setActtTyp(String acttTyp) {
		this.acttTyp = acttTyp;
	}
	public String getActtDesc() {
		return acttDesc;
	}
	public void setActtDesc(String acttDesc) {
		this.acttDesc = acttDesc;
	}
	public Date getActtBegin() {
		return acttBegin;
	}
	public void setActtBegin(Date acttBegin) {
		this.acttBegin = acttBegin;
	}
	public Date getActtEnd() {
		return acttEnd;
	}
	public void setActtEnd(Date acttEnd) {
		this.acttEnd = acttEnd;
	}
	public List<CmsActivityDealer> getDealers() {
		return dealers;
	}
	public void setDealers(List<CmsActivityDealer> dealers) {
		this.dealers = dealers;
	}
	public String getCompanyCodes() {
		if(this.dealers!=null && !dealers.isEmpty()){
			String codes = "";
			for(CmsActivityDealer dealer : dealers){
				codes += dealer.getCompanyCode()+",";
			}
			return codes;
		}
		return companyCodes;
	}
	public void setCompanyCodes(String companyCodes) {
		this.companyCodes = companyCodes;
	}
	public List<CmsActivityRule> getActivityRules() {
		return activityRules;
	}
	public void setActivityRules(List<CmsActivityRule> activityRules) {
		this.activityRules = activityRules;
	}
	public String getCompanyNames() {
		if(this.dealers!=null && !dealers.isEmpty()){
			String names = "";
			for(CmsActivityDealer dealer : dealers){
				names += dealer.getCompanyName()+",";
			}
			return names;
		}
		return companyNames;
	}
	public void setCompanyNames(String companyNames) {
		this.companyNames = companyNames;
	}
}
