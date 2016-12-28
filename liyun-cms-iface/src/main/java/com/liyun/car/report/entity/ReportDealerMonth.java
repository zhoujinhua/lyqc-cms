package com.liyun.car.report.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 经销商月报按申请单属性占比率
 * @author zhoufei
 *
 */
public class ReportDealerMonth implements Serializable{

	private String countType; //类型
	private String appTime;  //月份
	private String companyName; //
	private Integer companyCode;
	private Float rInitScale = 0f; //首付比例
	private Float rLoanRate = 0f; //利率
	private Float rLoanPeriods = 0f; //期数
	private Integer appCodeCnt = 0; //申请单数
	private Integer paltCodeCnt = 0; //融平台费合同量
	private Integer yaobaoCodeCnt = 0; //延保合同单量
	private Integer gpsCodeCnt = 0; //GPS安装单量
	private Float loanAmt = 0f; //放款金额
	private Integer dyodCodeCnt = 0; //抵押逾期单量
	private Integer pfCodeCnt = 0; //批复单量
	private Integer fkCodeCnt = 0; //放款单量
	private Integer od30CodeCnt = 0; //逾期30+单量
	private Integer hkCodeCnt = 0; //还款单量
	private Integer pd6 = 0; //前6期出现逾期的比例
	private Integer pd3 = 0; //前3期出现逾期的比例
	private Integer fpd = 0; //第一期出现逾期的比例  
	private Integer tqCodeCnt = 0; //提前还款单量
	private Integer od180CodeCnt = 0; //逾期180+单量
	private Date onlineTime; //上线日期
	private Date firstLoanTime; //收单日期
	private Integer dealerCnt = 0; //账号数量
	private Integer cityCnt = 0; //抵押上牌城市个数
	private Integer cityCodeCnt = 0; //单城市合同量
	
	public ReportDealerMonth() {
		super();
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
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

	public Float getrInitScale() {
		return rInitScale;
	}

	public void setrInitScale(Float rInitScale) {
		this.rInitScale = rInitScale;
	}

	public Float getrLoanRate() {
		return rLoanRate;
	}

	public void setrLoanRate(Float rLoanRate) {
		this.rLoanRate = rLoanRate;
	}

	public Float getrLoanPeriods() {
		return rLoanPeriods;
	}

	public void setrLoanPeriods(Float rLoanPeriods) {
		this.rLoanPeriods = rLoanPeriods;
	}

	public Integer getAppCodeCnt() {
		return appCodeCnt;
	}

	public void setAppCodeCnt(Integer appCodeCnt) {
		this.appCodeCnt = appCodeCnt;
	}

	public Integer getPaltCodeCnt() {
		return paltCodeCnt;
	}

	public void setPaltCodeCnt(Integer paltCodeCnt) {
		this.paltCodeCnt = paltCodeCnt;
	}

	public Integer getYaobaoCodeCnt() {
		return yaobaoCodeCnt;
	}

	public void setYaobaoCodeCnt(Integer yaobaoCodeCnt) {
		this.yaobaoCodeCnt = yaobaoCodeCnt;
	}

	public Integer getGpsCodeCnt() {
		return gpsCodeCnt;
	}

	public void setGpsCodeCnt(Integer gpsCodeCnt) {
		this.gpsCodeCnt = gpsCodeCnt;
	}

	public Float getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(Float loanAmt) {
		this.loanAmt = loanAmt;
	}

	public Integer getDyodCodeCnt() {
		return dyodCodeCnt;
	}

	public void setDyodCodeCnt(Integer dyodCodeCnt) {
		this.dyodCodeCnt = dyodCodeCnt;
	}

	public Integer getPfCodeCnt() {
		return pfCodeCnt;
	}

	public void setPfCodeCnt(Integer pfCodeCnt) {
		this.pfCodeCnt = pfCodeCnt;
	}

	public Integer getFkCodeCnt() {
		return fkCodeCnt;
	}

	public void setFkCodeCnt(Integer fkCodeCnt) {
		this.fkCodeCnt = fkCodeCnt;
	}

	public Integer getOd30CodeCnt() {
		return od30CodeCnt;
	}

	public void setOd30CodeCnt(Integer od30CodeCnt) {
		this.od30CodeCnt = od30CodeCnt;
	}

	public Integer getHkCodeCnt() {
		return hkCodeCnt;
	}

	public void setHkCodeCnt(Integer hkCodeCnt) {
		this.hkCodeCnt = hkCodeCnt;
	}

	public Integer getPd6() {
		return pd6;
	}

	public void setPd6(Integer pd6) {
		this.pd6 = pd6;
	}

	public Integer getPd3() {
		return pd3;
	}

	public void setPd3(Integer pd3) {
		this.pd3 = pd3;
	}

	public Integer getFpd() {
		return fpd;
	}

	public void setFpd(Integer fpd) {
		this.fpd = fpd;
	}

	public Integer getTqCodeCnt() {
		return tqCodeCnt;
	}

	public void setTqCodeCnt(Integer tqCodeCnt) {
		this.tqCodeCnt = tqCodeCnt;
	}

	public Integer getOd180CodeCnt() {
		return od180CodeCnt;
	}

	public void setOd180CodeCnt(Integer od180CodeCnt) {
		this.od180CodeCnt = od180CodeCnt;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Date getFirstLoanTime() {
		return firstLoanTime;
	}

	public void setFirstLoanTime(Date firstLoanTime) {
		this.firstLoanTime = firstLoanTime;
	}

	public Integer getDealerCnt() {
		return dealerCnt;
	}

	public void setDealerCnt(Integer dealerCnt) {
		this.dealerCnt = dealerCnt;
	}

	public Integer getCityCnt() {
		return cityCnt;
	}

	public void setCityCnt(Integer cityCnt) {
		this.cityCnt = cityCnt;
	}

	public Integer getCityCodeCnt() {
		return cityCodeCnt;
	}

	public void setCityCodeCnt(Integer cityCodeCnt) {
		this.cityCodeCnt = cityCodeCnt;
	}
	
	
}
