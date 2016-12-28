package com.liyun.car.materiel.entity;

import java.io.Serializable;
import java.util.Date;

import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.materiel.enums.DealerMaterielActionEnum;
import com.liyun.car.materiel.enums.DealerMaterielStatusEnum;

public class CmsDealerMaterielApproval implements Serializable{

	private Integer id;
	private Integer mtrlAppCode;
	private DealerMaterielStatusEnum preStt;
	private DealerMaterielStatusEnum nextStt;
	private DealerMaterielActionEnum action;
	private BooleanEnum typ;
	private String remark;
	private String userName;
	private String trueName;
	private Date crtTime;
	
	public CmsDealerMaterielApproval(Integer id, Integer mtrlAppCode) {
		super();
		this.id = id;
		this.mtrlAppCode = mtrlAppCode;
	}
	public CmsDealerMaterielApproval() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMtrlAppCode() {
		return mtrlAppCode;
	}
	public void setMtrlAppCode(Integer mtrlAppCode) {
		this.mtrlAppCode = mtrlAppCode;
	}
	public DealerMaterielStatusEnum getPreStt() {
		return preStt;
	}
	public void setPreStt(DealerMaterielStatusEnum preStt) {
		this.preStt = preStt;
	}
	public DealerMaterielStatusEnum getNextStt() {
		return nextStt;
	}
	public void setNextStt(DealerMaterielStatusEnum nextStt) {
		this.nextStt = nextStt;
	}
	public DealerMaterielActionEnum getAction() {
		return action;
	}
	public void setAction(DealerMaterielActionEnum action) {
		this.action = action;
	}
	public BooleanEnum getTyp() {
		return typ;
	}
	public void setTyp(BooleanEnum typ) {
		this.typ = typ;
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
