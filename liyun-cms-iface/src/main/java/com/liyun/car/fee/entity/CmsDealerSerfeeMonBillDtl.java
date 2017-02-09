package com.liyun.car.fee.entity;

import java.io.Serializable;
import java.util.Date;

import com.liyun.car.activity.entity.CmsParamSub;
import com.liyun.car.common.enums.BooleanEnum;

/**
 * 经销商服务费明细表(二级科目细分)
 * CMS_DEALER_SERFEE_MON_BILL_DTL
 * @author zhoufei
 *
 */
public class CmsDealerSerfeeMonBillDtl implements Serializable{

	private String feeMon;
	private Integer companyCode;
	private String companyName;
	private String topParamEn;
	private String subParamEn;
	private String subParamNm;
	private Double paramAmt;
	private Integer paramCrontCnt;
	private Date crtTime;
	private CmsParamSub paramSub;
	private BooleanEnum isAdj;
	
	public CmsDealerSerfeeMonBillDtl(String feeMon, Integer companyCode,
			String subParamEn) {
		super();
		this.feeMon = feeMon;
		this.companyCode = companyCode;
		this.subParamEn = subParamEn;
	}
	public CmsDealerSerfeeMonBillDtl() {
		super();
	}
	public String getFeeMon() {
		return feeMon;
	}
	public void setFeeMon(String feeMon) {
		this.feeMon = feeMon;
	}
	public Integer getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTopParamEn() {
		return topParamEn;
	}
	public void setTopParamEn(String topParamEn) {
		this.topParamEn = topParamEn;
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
	public Double getParamAmt() {
		return paramAmt;
	}
	public void setParamAmt(Double paramAmt) {
		this.paramAmt = paramAmt;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public CmsParamSub getParamSub() {
		return paramSub;
	}
	public void setParamSub(CmsParamSub paramSub) {
		this.paramSub = paramSub;
	}
	public BooleanEnum getIsAdj() {
		return isAdj;
	}
	public void setIsAdj(BooleanEnum isAdj) {
		this.isAdj = isAdj;
	}
	public Integer getParamCrontCnt() {
		return paramCrontCnt;
	}
	public void setParamCrontCnt(Integer paramCrontCnt) {
		this.paramCrontCnt = paramCrontCnt;
	}
	
	
}
