package com.liyun.car.loan.entity;

import java.io.Serializable;

public class CaProduct implements Serializable{

	private Integer id;
	private String pName;
	private Integer status = 1;  //取状态为启用的产品
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
