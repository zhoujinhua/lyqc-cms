package com.liyun.car.fee.entity;

import java.io.Serializable;
import java.util.Date;

import org.activiti.engine.task.Task;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.fee.enums.SerfeeStatusEnum;

public class CmsSerfeeMonInfo implements Serializable{

	public static String FLOW_NAME = "serFee";
	private String feeMon;
	private Integer companyCnt;
	private Integer contrCnt;
	private Double contrAmt;
	private Double serfee;
	private Double serfeeRatio;
	private SerfeeStatusEnum stt;
	private BooleanEnum isFreeze;
	private String serfeeDir;
	private String trueName;
	private Date crtTime;
	private Integer submitUser;
	
	private Task task;
	
	public String getFeeMon() {
		return feeMon;
	}
	public void setFeeMon(String feeMon) {
		this.feeMon = feeMon;
	}
	public Integer getCompanyCnt() {
		return companyCnt;
	}
	public void setCompanyCnt(Integer companyCnt) {
		this.companyCnt = companyCnt;
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
	public SerfeeStatusEnum getStt() {
		return stt;
	}
	public void setStt(SerfeeStatusEnum stt) {
		this.stt = stt;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public BooleanEnum getIsFreeze() {
		return isFreeze;
	}
	public void setIsFreeze(BooleanEnum isFreeze) {
		this.isFreeze = isFreeze;
	}
	public String getSerfeeDir() {
		return serfeeDir;
	}
	public void setSerfeeDir(String serfeeDir) {
		this.serfeeDir = serfeeDir;
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
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public Integer getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(Integer submitUser) {
		this.submitUser = submitUser;
	}
	
	
}
