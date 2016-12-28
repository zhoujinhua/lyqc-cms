package com.liyun.car.loan.entity;

import java.io.Serializable;

public class UaChannel implements Serializable{

	private Integer id;
	private String enName;
	private String status = "1";
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
