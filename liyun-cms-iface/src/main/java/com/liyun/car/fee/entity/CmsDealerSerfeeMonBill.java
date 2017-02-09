package com.liyun.car.fee.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.dealer.enums.BillPayStatusEnum;
import com.liyun.car.fee.enums.SerfeeBillStatusEnum;

/**
 * 账单明细表
 * @author zhoufei
 *
 */
public class CmsDealerSerfeeMonBill implements Serializable{

	private String feeMon;
	private Integer companyCode;
	private String companyName;
	private String province;
	private String city;
	private String saleArea;
	private Integer contrCnt;
	private Double contrAmt;
	private Double serfee;
	private Double serfeeRatio;
	private SerfeeBillStatusEnum stt;
	private BillPayStatusEnum payStt;
	private Date crtTime;
	
	@JsonIgnore
	private List<CmsDealerSerfeeMonBillDtl> dtls;
	private Double invoieAmt;
	
	public CmsDealerSerfeeMonBill(String feeMon, Integer companyCode) {
		super();
		this.feeMon = feeMon;
		this.companyCode = companyCode;
	}
	public CmsDealerSerfeeMonBill() {
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSaleArea() {
		return saleArea;
	}
	public void setSaleArea(String saleArea) {
		this.saleArea = saleArea;
	}
	public Integer getContrCnt() {
		return contrCnt;
	}
	public void setContrCnt(Integer contrCnt) {
		this.contrCnt = contrCnt;
	}
	public Double getContrAmt() {
		return contrAmt;
	}
	public void setContrAmt(Double contrAmt) {
		this.contrAmt = contrAmt;
	}
	public Double getSerfee() {
		return serfee;
	}
	public void setSerfee(Double serfee) {
		this.serfee = serfee;
	}
	public Double getSerfeeRatio() {
		return serfeeRatio;
	}
	public void setSerfeeRatio(Double serfeeRatio) {
		this.serfeeRatio = serfeeRatio;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public SerfeeBillStatusEnum getStt() {
		return stt;
	}
	public void setStt(SerfeeBillStatusEnum stt) {
		this.stt = stt;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public BillPayStatusEnum getPayStt() {
		return payStt;
	}
	public void setPayStt(BillPayStatusEnum payStt) {
		this.payStt = payStt;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public List<CmsDealerSerfeeMonBillDtl> getDtls() {
		return dtls;
	}
	public void setDtls(List<CmsDealerSerfeeMonBillDtl> dtls) {
		this.dtls = dtls;
	}
	public Double getInvoieAmt() {
		if(this.dtls!=null && !dtls.isEmpty()){
			Double amt = 0.00d;
			for(CmsDealerSerfeeMonBillDtl dtl : dtls){
				if(dtl.getParamSub().getIsReceipt() == BooleanEnum.YES){
					amt += dtl.getParamAmt();
				}
			}
			return amt;
		}
		return invoieAmt;
	}
	
	
}
