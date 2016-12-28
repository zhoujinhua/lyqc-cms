package com.liyun.car.dealer.entity;

import java.io.Serializable;
import java.util.Date;

public class CmsSalesManageInfoHis implements Serializable{

	private Integer id;
	private Integer salesId;
	private String salesName;
	private String post;
	private Integer ManageDealer;
	private Date startTime;
	private Date endTime;
	
	public CmsSalesManageInfoHis(Integer id, Integer manageDealer) {
		super();
		this.id = id;
		ManageDealer = manageDealer;
	}
	public CmsSalesManageInfoHis() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSalesId() {
		return salesId;
	}
	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Integer getManageDealer() {
		return ManageDealer;
	}
	public void setManageDealer(Integer manageDealer) {
		ManageDealer = manageDealer;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
