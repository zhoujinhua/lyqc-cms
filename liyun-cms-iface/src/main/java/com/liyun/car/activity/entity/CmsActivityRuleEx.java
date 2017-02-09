package com.liyun.car.activity.entity;

import java.io.Serializable;

import com.liyun.car.common.enums.ParamStatusEnum;

public class CmsActivityRuleEx implements Serializable{

	private Integer id;
	private CmsActivityRuleGroup group;
	private String exCode;
	private String exName;
	private ParamStatusEnum status;
	private Integer ruleId;
	private String ruleName;
	private Integer exPri;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CmsActivityRuleGroup getGroup() {
		return group;
	}
	public void setGroup(CmsActivityRuleGroup group) {
		this.group = group;
	}
	public String getExCode() {
		return exCode;
	}
	public void setExCode(String exCode) {
		this.exCode = exCode;
	}
	public String getExName() {
		return exName;
	}
	public void setExName(String exName) {
		this.exName = exName;
	}
	public ParamStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ParamStatusEnum status) {
		this.status = status;
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
	public Integer getExPri() {
		return exPri;
	}
	public void setExPri(Integer exPri) {
		this.exPri = exPri;
	}
}
