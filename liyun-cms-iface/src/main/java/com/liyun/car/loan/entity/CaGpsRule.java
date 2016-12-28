package com.liyun.car.loan.entity;

import java.io.Serializable;

public class CaGpsRule implements Serializable{

	private Integer ruleSeq;
	private Double gpsFee;
	
	public Integer getRuleSeq() {
		return ruleSeq;
	}
	public void setRuleSeq(Integer ruleSeq) {
		this.ruleSeq = ruleSeq;
	}
	public Double getGpsFee() {
		return gpsFee;
	}
	public void setGpsFee(Double gpsFee) {
		this.gpsFee = gpsFee;
	}
	
	
}
