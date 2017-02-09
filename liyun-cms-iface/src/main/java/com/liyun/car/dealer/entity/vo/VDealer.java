package com.liyun.car.dealer.entity.vo;

import java.io.Serializable;
import java.util.Date;

import org.activiti.engine.task.Task;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.system.entity.SyUser;

public class VDealer implements Serializable{
	
	public static String FLOW_NAME = "dealer";
	private Integer dealerCode;
	private Integer companyCode;
	private String companyName;
	private String dealerName;
	private String province;
	private String city;
	private SyUser am;
	private DealerStatusEnum status;
	private DealerStatusEnum companyStatus;
	private Date updateTime;
	private Integer tempDealerCode;
	
	private Task task;
	
	public VDealer(Integer dealerCode) {
		super();
		this.dealerCode = dealerCode;
	}
	public VDealer() {
		super();
	}
	public Integer getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(Integer dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Integer getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
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
	@JsonSerialize(using = EnumSerializer.class) 
	public DealerStatusEnum getStatus() {
		return status;
	}
	public void setStatus(DealerStatusEnum status) {
		this.status = status;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public DealerStatusEnum getCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(DealerStatusEnum companyStatus) {
		this.companyStatus = companyStatus;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public SyUser getAm() {
		return am;
	}
	public void setAm(SyUser am) {
		this.am = am;
	}
	public Integer getTempDealerCode() {
		return tempDealerCode;
	}
	public void setTempDealerCode(Integer tempDealerCode) {
		this.tempDealerCode = tempDealerCode;
	}
	
}
