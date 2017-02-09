package com.liyun.car.fee.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.fee.enums.SerfeeActionEnum;
import com.liyun.car.fee.enums.SerfeeStatusEnum;

/**
 * 服务费操作流水表
 * @author zhoufei
 *
 */
public class CmsDealerSerfeeFlow implements Serializable{

	private Integer id;
	private String feeMon;
	private SerfeeStatusEnum preStt;
	private SerfeeStatusEnum nextStt;
	private SerfeeActionEnum action;
	private BooleanEnum typ;
	private String fileDir;
	private String userName;
	private String remark;
	private String trueName;
	private Date crtTime = new Date();
	
	
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
	@JsonSerialize(using = EnumSerializer.class)
	public SerfeeStatusEnum getPreStt() {
		return preStt;
	}
	public void setPreStt(SerfeeStatusEnum preStt) {
		this.preStt = preStt;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public SerfeeStatusEnum getNextStt() {
		return nextStt;
	}
	public void setNextStt(SerfeeStatusEnum nextStt) {
		this.nextStt = nextStt;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public SerfeeActionEnum getAction() {
		return action;
	}
	public void setAction(SerfeeActionEnum action) {
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
