package com.liyun.car.dealer.entity;

import java.io.Serializable;

import com.liyun.car.common.enums.BooleanEnum;

public class CmsAmDealerCompany implements Serializable,Cloneable{
	
	private Integer id;
	
	private Integer userId;
	
	private String userName;
	
	private CmsDealerCompany company;
	
	private BooleanEnum isPrimary;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public CmsDealerCompany getCompany() {
		return company;
	}

	public void setCompany(CmsDealerCompany cmsDealerCompany) {
		this.company = cmsDealerCompany;
	}

	public BooleanEnum getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(BooleanEnum isPrimary) {
		this.isPrimary = isPrimary;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


}
