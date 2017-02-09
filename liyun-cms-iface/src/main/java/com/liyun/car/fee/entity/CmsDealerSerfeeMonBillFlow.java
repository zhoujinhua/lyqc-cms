package com.liyun.car.fee.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.fee.enums.SerfeeBillActionEnum;
import com.liyun.car.fee.enums.SerfeeBillStatusEnum;

/**
 * 服务费账单操作流水表
 * @author zhoufei
 *
 */
public class CmsDealerSerfeeMonBillFlow implements Serializable{

	private Integer id;
	private String feeMon;
	private Integer companyCode;
	private SerfeeBillStatusEnum preStt;
	private SerfeeBillStatusEnum nextStt;
	private SerfeeBillActionEnum action;
	private BooleanEnum typ;
	private String fileDir;
	private String userName;
	private String remark;
	private String trueName;
	private Date crtTime;
	
	public CmsDealerSerfeeMonBillFlow(SerfeeBillStatusEnum preStt,
			SerfeeBillStatusEnum nextStt, BooleanEnum typ,SerfeeBillActionEnum action) {
		super();
		this.preStt = preStt;
		this.nextStt = nextStt;
		this.typ = typ;
		this.action = action;
	}
	public CmsDealerSerfeeMonBillFlow() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	@JsonSerialize(using = EnumSerializer.class) 
	public SerfeeBillStatusEnum getPreStt() {
		return preStt;
	}
	public void setPreStt(SerfeeBillStatusEnum preStt) {
		this.preStt = preStt;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public SerfeeBillStatusEnum getNextStt() {
		return nextStt;
	}
	public void setNextStt(SerfeeBillStatusEnum nextStt) {
		this.nextStt = nextStt;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public SerfeeBillActionEnum getAction() {
		return action;
	}
	public void setAction(SerfeeBillActionEnum action) {
		this.action = action;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public BooleanEnum getTyp() {
		return typ;
	}
	public void setTyp(BooleanEnum typ) {
		this.typ = typ;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	
	
	
	
}
