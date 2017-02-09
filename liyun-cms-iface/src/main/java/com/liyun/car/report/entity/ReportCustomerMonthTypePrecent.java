package com.liyun.car.report.entity;

import java.io.Serializable;

/**
 * 经销商月报按客户属性占比率
 * @author zhoufei
 *
 */
public class ReportCustomerMonthTypePrecent implements Serializable{

	private String countType;
	private String month;
	private String typeValue;
	private String companyName;
	private Integer companyCode;
	private Integer dealerCnt;
	private Integer countryCnt;
	private Float dealerPrecent;
	private Float countryPrecent;
	
	public String getCountType() {
		return countType;
	}
	public void setCountType(String countType) {
		this.countType = countType;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}
	public Integer getDealerCnt() {
		return dealerCnt;
	}
	public void setDealerCnt(Integer dealerCnt) {
		this.dealerCnt = dealerCnt;
	}
	public Integer getCountryCnt() {
		return countryCnt;
	}
	public void setCountryCnt(Integer countryCnt) {
		this.countryCnt = countryCnt;
	}
	public Float getDealerPrecent() {
		return dealerPrecent;
	}
	public void setDealerPrecent(Float dealerPrecent) {
		this.dealerPrecent = dealerPrecent;
	}
	public Float getCountryPrecent() {
		return countryPrecent;
	}
	public void setCountryPrecent(Float countryPrecent) {
		this.countryPrecent = countryPrecent;
	}
	
}
