package com.liyun.car.activity.entity;

import java.io.Serializable;
import java.util.Date;

public class CmsActivityDealer implements Serializable{

	private Integer id;
	private CmsActivityInfo activityInfo;
	private Integer companyCode;
	private String companyName;
	private Date crtTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CmsActivityInfo getActivityInfo() {
		return activityInfo;
	}
	public void setActivityInfo(CmsActivityInfo activityInfo) {
		this.activityInfo = activityInfo;
	}
	public Integer getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
