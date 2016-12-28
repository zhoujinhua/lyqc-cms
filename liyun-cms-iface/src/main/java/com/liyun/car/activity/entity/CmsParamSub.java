package com.liyun.car.activity.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.param.usertype.DictEnum;

public class CmsParamSub implements Serializable{

	private Integer id;
	private String subParamEn;
	private CmsParamTop paramTop;
	private String subParamNm;
	private Integer paramTyp = 2;
	private String paramDesc;
	private ParamStatusEnum stt;
	private DictEnum payAcctIdn; //付款账户
	private BooleanEnum isReceipt;        //是否开票
	private Date crtTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubParamEn() {
		return subParamEn;
	}
	public void setSubParamEn(String subParamEn) {
		this.subParamEn = subParamEn;
	}
	public CmsParamTop getParamTop() {
		return paramTop;
	}
	public void setParamTop(CmsParamTop paramTop) {
		this.paramTop = paramTop;
	}
	public String getSubParamNm() {
		return subParamNm;
	}
	public void setSubParamNm(String subParamNm) {
		this.subParamNm = subParamNm;
	}
	public Integer getParamTyp() {
		return paramTyp;
	}
	public void setParamTyp(Integer paramTyp) {
		this.paramTyp = paramTyp;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public ParamStatusEnum getStt() {
		return stt;
	}
	public void setStt(ParamStatusEnum stt) {
		this.stt = stt;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public String getParamDesc() {
		return paramDesc;
	}
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	public DictEnum getPayAcctIdn() {
		return payAcctIdn;
	}
	public void setPayAcctIdn(DictEnum payAcctIdn) {
		this.payAcctIdn = payAcctIdn;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public BooleanEnum getIsReceipt() {
		return isReceipt;
	}
	public void setIsReceipt(BooleanEnum isReceipt) {
		this.isReceipt = isReceipt;
	}
	
	
}
