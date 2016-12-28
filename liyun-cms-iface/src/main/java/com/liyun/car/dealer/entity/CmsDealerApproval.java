package com.liyun.car.dealer.entity;

import java.io.Serializable;
import java.util.Date;

import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.dealer.enums.DealerActionEnum;
import com.liyun.car.dealer.enums.DealerStatusEnum;

public class CmsDealerApproval implements Serializable{

	private Integer id;
	private Integer code;
	private DealerStatusEnum preStt;
	private DealerStatusEnum nextStt;
	private DealerActionEnum action;
	private BooleanEnum typ;
	private String remark;
	private String userName;
	private String trueName;
	private Date crtTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public DealerStatusEnum getPreStt() {
		return preStt;
	}
	public void setPreStt(DealerStatusEnum preStt) {
		this.preStt = preStt;
	}
	public DealerStatusEnum getNextStt() {
		return nextStt;
	}
	public void setNextStt(DealerStatusEnum nextStt) {
		this.nextStt = nextStt;
	}
	public BooleanEnum getTyp() {
		return typ;
	}
	public void setTyp(BooleanEnum typ) {
		this.typ = typ;
	}
	public DealerActionEnum getAction() {
		return action;
	}
	public void setAction(DealerActionEnum action) {
		this.action = action;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
