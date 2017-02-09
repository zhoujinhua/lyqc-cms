package com.liyun.car.report.entity.vo;

import java.io.Serializable;

import com.liyun.car.common.enums.BooleanEnum;

public class ReportSearchDto implements Serializable{

	private BooleanEnum isAll;
	private Integer companyCode;
	private String month;
	
	public BooleanEnum getIsAll() {
		return isAll;
	}
	public void setIsAll(BooleanEnum isAll) {
		this.isAll = isAll;
	}
	public Integer getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
}
