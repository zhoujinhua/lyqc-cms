package com.liyun.car.activity.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.activity.enums.RuleLevelEnum;
import com.liyun.car.activity.enums.RuleReturnTypeEnum;
import com.liyun.car.activity.enums.RuleRewardTypeEnum;
import com.liyun.car.common.serializer.EnumSerializer;

public class CmsActivityRule implements Serializable{

	private Integer ruleId;
	private CmsActivityInfo activityInfo;
	private String ruleNm;
	//private String exCode;
	//private RuleExTypeEnum exType;
	//private Integer exPri;
	private RuleRewardTypeEnum reTyp;
	private RuleReturnTypeEnum reMode;
	private RuleLevelEnum ruleLvl;
	private String reRst;
	private String ruleDesc;
	private Date crtTime;
	private Integer rulePropCnt;
	@JsonIgnore
	private List<CmsParamRuleProp> props;
	@JsonIgnore
	private List<CmsDealerRuleProp> dealerProps;
	private CmsContrRuleProp contrProp;
	
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public CmsActivityInfo getActivityInfo() {
		return activityInfo;
	}
	public void setActivityInfo(CmsActivityInfo activityInfo) {
		this.activityInfo = activityInfo;
	}
	public String getRuleNm() {
		return ruleNm;
	}
	public void setRuleNm(String ruleNm) {
		this.ruleNm = ruleNm;
	}
	/*public String getExCode() {
		return exCode;
	}
	public void setExCode(String exCode) {
		this.exCode = exCode;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public RuleExTypeEnum getExType() {
		return exType;
	}
	public void setExType(RuleExTypeEnum exType) {
		this.exType = exType;
	}
	public Integer getExPri() {
		return exPri;
	}
	public void setExPri(Integer exPri) {
		this.exPri = exPri;
	}*/
	@JsonSerialize(using = EnumSerializer.class)
	public RuleReturnTypeEnum getReMode() {
		return reMode;
	}
	public void setReMode(RuleReturnTypeEnum reMode) {
		this.reMode = reMode;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public RuleLevelEnum getRuleLvl() {
		return ruleLvl;
	}
	public void setRuleLvl(RuleLevelEnum ruleLvl) {
		this.ruleLvl = ruleLvl;
	}
	public String getReRst() {
		return reRst;
	}
	public void setReRst(String reRst) {
		this.reRst = reRst;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public List<CmsParamRuleProp> getProps() {
		return props;
	}
	public void setProps(List<CmsParamRuleProp> props) {
		this.props = props;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public RuleRewardTypeEnum getReTyp() {
		return reTyp;
	}
	public void setReTyp(RuleRewardTypeEnum reTyp) {
		this.reTyp = reTyp;
	}
	public List<CmsDealerRuleProp> getDealerProps() {
		return dealerProps;
	}
	public void setDealerProps(List<CmsDealerRuleProp> dealerProps) {
		this.dealerProps = dealerProps;
	}
	public CmsContrRuleProp getContrProp() {
		return contrProp;
	}
	public void setContrProp(CmsContrRuleProp contrProp) {
		this.contrProp = contrProp;
	}
	public void addProp(CmsParamRuleProp paramProp) {
		if(getProps()==null){
			setProps(new ArrayList<CmsParamRuleProp>());
		}
		getProps().add(paramProp);
		paramProp.setRule(this);
	}
	public void addDealerProp(CmsDealerRuleProp dealerProp) {
		if(getDealerProps()!=null){
			setDealerProps(new ArrayList<CmsDealerRuleProp>());
		}
		getDealerProps().add(dealerProp);
		dealerProp.setRule(this);
	}
	public Integer getRulePropCnt() {
		return rulePropCnt;
	}
	public void setRulePropCnt(Integer rulePropCnt) {
		this.rulePropCnt = rulePropCnt;
	}
	
	
}
