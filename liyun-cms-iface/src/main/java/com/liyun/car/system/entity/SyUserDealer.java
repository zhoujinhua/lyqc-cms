package com.liyun.car.system.entity;

import com.liyun.car.dealer.entity.SyDealer;

/**
 * SyUserDealer entity. @author MyEclipse Persistence Tools
 */

public class SyUserDealer implements java.io.Serializable {

	private Integer id;
	private SyDealer dealer;
	private SyUser user;
	private String userPostion;
	
	// Constructors

	/** default constructor */
	public SyUserDealer() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	/*public Integer getDealerCode() {
		return dealerCode;
	}


	public void setDealerCode(Integer dealerCode) {
		this.dealerCode = dealerCode;
	}*/
	
	


	public SyUser getUser() {
		return user;
	}


	public SyDealer getDealer() {
		return dealer;
	}


	public void setDealer(SyDealer dealer) {
		this.dealer = dealer;
	}


	public void setUser(SyUser user) {
		this.user = user;
	}


	public String getUserPostion() {
		return this.userPostion;
	}

	public void setUserPostion(String userPostion) {
		this.userPostion = userPostion;
	}

}