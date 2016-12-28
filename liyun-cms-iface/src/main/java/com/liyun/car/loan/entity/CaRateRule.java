package com.liyun.car.loan.entity;

import java.io.Serializable;

public class CaRateRule implements Serializable{

	private Integer ruleSeq;
	private Double loanRate;
	
	public Integer getRuleSeq() {
		return ruleSeq;
	}
	public void setRuleSeq(Integer ruleSeq) {
		this.ruleSeq = ruleSeq;
	}
	public Double getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(Double loanRate) {
		this.loanRate = loanRate;
	}
	
	
}
