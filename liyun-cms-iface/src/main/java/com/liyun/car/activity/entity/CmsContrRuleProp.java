package com.liyun.car.activity.entity;

import java.io.Serializable;

import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.param.usertype.DictEnum;


public class CmsContrRuleProp implements Serializable{

	private Integer ruleId;
	private BooleanEnum isGPS;
	private String rGPSFee;
	private String rcarloanAmountMax;
	private String rcarloanAmountMin;
	private String courseMax;
	private String courseMin;
	private String initScaleMax;
	private String initScaleMin;
	private BooleanEnum isHouse;
	private String isLcv;
	private BooleanEnum isOld;
	private String carAgeMin;
	private String carAgeMax;
	private String rloanAmountMax;
	private String rloanAmountMin;
	private String rloanPeriods;
	private String rLoanRate;
	private String salePriceMax;
	private String salePriceMin;
	private String loanTimeBegin;
	private String loanTimeEnd;
	private String productCode;
	private String productName;
	private String paymentChEn;
	private String carBrand;
	private String carSeries;
	private String dealerType;
	private String appTimeMax;
	private String appTimeMin;
	private String rComFeeMax;
	private String rComFeeMin;
	private String rAccountFeeMin;
	private String rAccountFeeMax;
	private String rDisTrueEMin;
	private String rDisTrueEMax;
	private String rYanbaoFee;
	private String comRate;
	private String gpsRebate;
	private String workCity;
	private String contrSTT;
	
	private String isLcvZh;
	
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public BooleanEnum getIsGPS() {
		return isGPS;
	}
	public void setIsGPS(BooleanEnum isGPS) {
		this.isGPS = isGPS;
	}
	public String getRcarloanAmountMax() {
		return rcarloanAmountMax;
	}
	public String getRGPSFee() {
		return rGPSFee;
	}
	public void setRGPSFee(String rGPSFee) {
		this.rGPSFee = rGPSFee;
	}
	public void setRcarloanAmountMax(String rcarloanAmountMax) {
		this.rcarloanAmountMax = rcarloanAmountMax;
	}
	public String getRcarloanAmountMin() {
		return rcarloanAmountMin;
	}
	public void setRcarloanAmountMin(String rcarloanAmountMin) {
		this.rcarloanAmountMin = rcarloanAmountMin;
	}
	public String getCourseMax() {
		return courseMax;
	}
	public void setCourseMax(String courseMax) {
		this.courseMax = courseMax;
	}
	public String getCourseMin() {
		return courseMin;
	}
	public void setCourseMin(String courseMin) {
		this.courseMin = courseMin;
	}
	public String getInitScaleMax() {
		return initScaleMax;
	}
	public void setInitScaleMax(String initScaleMax) {
		this.initScaleMax = initScaleMax;
	}
	public String getInitScaleMin() {
		return initScaleMin;
	}
	public void setInitScaleMin(String initScaleMin) {
		this.initScaleMin = initScaleMin;
	}
	public BooleanEnum getIsHouse() {
		return isHouse;
	}
	public void setIsHouse(BooleanEnum isHouse) {
		this.isHouse = isHouse;
	}
	public String getIsLcv() {
		return isLcv;
	}
	public void setIsLcv(String isLcv) {
		this.isLcv = isLcv;
	}
	
	public String getIsLcvZh() {
		return DictEnum.nameOf("fwcl", this.isLcv);
	}
	public BooleanEnum getIsOld() {
		return isOld;
	}
	public void setIsOld(BooleanEnum isOld) {
		this.isOld = isOld;
	}
	public String getCarAgeMin() {
		return carAgeMin;
	}
	public void setCarAgeMin(String carAgeMin) {
		this.carAgeMin = carAgeMin;
	}
	public String getCarAgeMax() {
		return carAgeMax;
	}
	public void setCarAgeMax(String carAgeMax) {
		this.carAgeMax = carAgeMax;
	}
	public String getRloanAmountMax() {
		return rloanAmountMax;
	}
	public void setRloanAmountMax(String rloanAmountMax) {
		this.rloanAmountMax = rloanAmountMax;
	}
	public String getRloanAmountMin() {
		return rloanAmountMin;
	}
	public void setRloanAmountMin(String rloanAmountMin) {
		this.rloanAmountMin = rloanAmountMin;
	}
	public String getRloanPeriods() {
		return rloanPeriods;
	}
	public void setRloanPeriods(String rloanPeriods) {
		this.rloanPeriods = rloanPeriods;
	}
	public String getRLoanRate() {
		return rLoanRate;
	}
	public void setRLoanRate(String rLoanRate) {
		this.rLoanRate = rLoanRate;
	}
	public String getSalePriceMax() {
		return salePriceMax;
	}
	public void setSalePriceMax(String salePriceMax) {
		this.salePriceMax = salePriceMax;
	}
	public String getSalePriceMin() {
		return salePriceMin;
	}
	public void setSalePriceMin(String salePriceMin) {
		this.salePriceMin = salePriceMin;
	}
	public String getLoanTimeBegin() {
		return loanTimeBegin;
	}
	public void setLoanTimeBegin(String loanTimeBegin) {
		this.loanTimeBegin = loanTimeBegin;
	}
	public String getLoanTimeEnd() {
		return loanTimeEnd;
	}
	public void setLoanTimeEnd(String loanTimeEnd) {
		this.loanTimeEnd = loanTimeEnd;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPaymentChEn() {
		return paymentChEn;
	}
	public void setPaymentChEn(String paymentChEn) {
		this.paymentChEn = paymentChEn;
	}
	public String getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	public String getCarSeries() {
		return carSeries;
	}
	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}
	public String getDealerType() {
		return dealerType;
	}
	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}
	public String getAppTimeMax() {
		return appTimeMax;
	}
	public void setAppTimeMax(String appTimeMax) {
		this.appTimeMax = appTimeMax;
	}
	public String getAppTimeMin() {
		return appTimeMin;
	}
	public void setAppTimeMin(String appTimeMin) {
		this.appTimeMin = appTimeMin;
	}
	public String getRComFeeMax() {
		return rComFeeMax;
	}
	public void setRComFeeMax(String rComFeeMax) {
		this.rComFeeMax = rComFeeMax;
	}
	public String getRComFeeMin() {
		return rComFeeMin;
	}
	public void setRComFeeMin(String rComFeeMin) {
		this.rComFeeMin = rComFeeMin;
	}
	public String getRAccountFeeMin() {
		return rAccountFeeMin;
	}
	public void setRAccountFeeMin(String rAccountFeeMin) {
		this.rAccountFeeMin = rAccountFeeMin;
	}
	public String getRAccountFeeMax() {
		return rAccountFeeMax;
	}
	public void setRAccountFeeMax(String rAccountFeeMax) {
		this.rAccountFeeMax = rAccountFeeMax;
	}
	public String getRDisTrueEMin() {
		return rDisTrueEMin;
	}
	public void setRDisTrueEMin(String rDisTrueEMin) {
		this.rDisTrueEMin = rDisTrueEMin;
	}
	public String getRDisTrueEMax() {
		return rDisTrueEMax;
	}
	public void setRDisTrueEMax(String rDisTrueEMax) {
		this.rDisTrueEMax = rDisTrueEMax;
	}
	public String getRYanbaoFee() {
		return rYanbaoFee;
	}
	public void setRYanbaoFee(String rYanbaoFee) {
		this.rYanbaoFee = rYanbaoFee;
	}
	public String getComRate() {
		return comRate;
	}
	public void setComRate(String comRate) {
		this.comRate = comRate;
	}
	public String getGpsRebate() {
		return gpsRebate;
	}
	public void setGpsRebate(String gpsRebate) {
		this.gpsRebate = gpsRebate;
	}
	public String getWorkCity() {
		return workCity;
	}
	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}
	public String getContrSTT() {
		return contrSTT;
	}
	public void setContrSTT(String contrSTT) {
		this.contrSTT = contrSTT;
	}
	public String getrGPSFee() {
		return rGPSFee;
	}
	public void setrGPSFee(String rGPSFee) {
		this.rGPSFee = rGPSFee;
	}
	public String getrLoanRate() {
		return rLoanRate;
	}
	public void setrLoanRate(String rLoanRate) {
		this.rLoanRate = rLoanRate;
	}
	public String getrComFeeMax() {
		return rComFeeMax;
	}
	public void setrComFeeMax(String rComFeeMax) {
		this.rComFeeMax = rComFeeMax;
	}
	public String getrComFeeMin() {
		return rComFeeMin;
	}
	public void setrComFeeMin(String rComFeeMin) {
		this.rComFeeMin = rComFeeMin;
	}
	public String getrAccountFeeMin() {
		return rAccountFeeMin;
	}
	public void setrAccountFeeMin(String rAccountFeeMin) {
		this.rAccountFeeMin = rAccountFeeMin;
	}
	public String getrAccountFeeMax() {
		return rAccountFeeMax;
	}
	public void setrAccountFeeMax(String rAccountFeeMax) {
		this.rAccountFeeMax = rAccountFeeMax;
	}
	public String getrDisTrueEMin() {
		return rDisTrueEMin;
	}
	public void setrDisTrueEMin(String rDisTrueEMin) {
		this.rDisTrueEMin = rDisTrueEMin;
	}
	public String getrDisTrueEMax() {
		return rDisTrueEMax;
	}
	public void setrDisTrueEMax(String rDisTrueEMax) {
		this.rDisTrueEMax = rDisTrueEMax;
	}
	public String getrYanbaoFee() {
		return rYanbaoFee;
	}
	public void setrYanbaoFee(String rYanbaoFee) {
		this.rYanbaoFee = rYanbaoFee;
	}
	public void setIsLcvZh(String isLcvZh) {
		this.isLcvZh = isLcvZh;
	}
	
}
