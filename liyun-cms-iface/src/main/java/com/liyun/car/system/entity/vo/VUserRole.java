package com.liyun.car.system.entity.vo;

import java.io.Serializable;

public class VUserRole implements Serializable{

	private Integer userId;
	private String userName;
	private Integer orgCode;
	private String userPosition;
	private String email;
	
	public VUserRole(Integer userId, Integer orgCode) {
		super();
		this.userId = userId;
		this.orgCode = orgCode;
	}
	public VUserRole() {
		super();
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(Integer orgCode) {
		this.orgCode = orgCode;
	}
	public String getUserPosition() {
		return userPosition;
	}
	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
