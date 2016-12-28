package com.liyun.car.activity.entity;

import java.io.Serializable;

import com.liyun.car.activity.enums.RuleLevelEnum;

public class CmsParamRuleProp implements Serializable{

	private Integer id;
	private CmsActivityRule rule;
	private String propCode;
	private String propName;
	private RuleLevelEnum propLvl;
	private String propValue;
	private Integer ruleNum;
	
	public CmsParamRuleProp() {
		super();
	}
	public CmsParamRuleProp(String propCode, String propName,
			RuleLevelEnum propLvl, String propValue) {
		super();
		this.propCode = propCode;
		this.propName = propName;
		this.propLvl = propLvl;
		this.propValue = propValue;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CmsActivityRule getRule() {
		return rule;
	}
	public void setRule(CmsActivityRule rule) {
		this.rule = rule;
	}
	public String getPropCode() {
		return propCode;
	}
	public void setPropCode(String propCode) {
		this.propCode = propCode;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public RuleLevelEnum getPropLvl() {
		return propLvl;
	}
	public void setPropLvl(RuleLevelEnum propLvl) {
		this.propLvl = propLvl;
	}
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	public Integer getRuleNum() {
		return ruleNum;
	}
	public void setRuleNum(Integer ruleNum) {
		this.ruleNum = ruleNum;
	}
	
	
}
