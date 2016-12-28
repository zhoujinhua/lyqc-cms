package com.liyun.car.system.entity;

import java.sql.Timestamp;

import javax.persistence.Id;

/**
 * SyShortmsgLog entity. @author MyEclipse Persistence Tools
 */

public class SyShortmsgLog implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2571628978382400319L;
	@Id
	private Long logId;
	private Integer SUserId;
	private String SUserName;
	private String RUserType;
	private Long RUserId;
	private String RUserName;
	private String RMobile;
	private Timestamp sendTime;
	private String content;
	private String status;
	private String remarks1;

	// Constructors

	/** default constructor */
	public SyShortmsgLog() {
	}

	/** minimal constructor */
	public SyShortmsgLog(String SUserName, String RUserType, String RUserName,
			String RMobile, Timestamp sendTime) {
		this.SUserName = SUserName;
		this.RUserType = RUserType;
		this.RUserName = RUserName;
		this.RMobile = RMobile;
		this.sendTime = sendTime;
	}

	/** full constructor */
	public SyShortmsgLog(Integer SUserId, String SUserName, String RUserType,
			Long RUserId, String RUserName, String RMobile, Timestamp sendTime,
			String content, String status, String remarks1) {
		this.SUserId = SUserId;
		this.SUserName = SUserName;
		this.RUserType = RUserType;
		this.RUserId = RUserId;
		this.RUserName = RUserName;
		this.RMobile = RMobile;
		this.sendTime = sendTime;
		this.content = content;
		this.status = status;
		this.remarks1 = remarks1;
	}

	// Property accessors

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Integer getSUserId() {
		return this.SUserId;
	}

	public void setSUserId(Integer SUserId) {
		this.SUserId = SUserId;
	}

	public String getSUserName() {
		return this.SUserName;
	}

	public void setSUserName(String SUserName) {
		this.SUserName = SUserName;
	}

	public String getRUserType() {
		return this.RUserType;
	}

	public void setRUserType(String RUserType) {
		this.RUserType = RUserType;
	}

	public Long getRUserId() {
		return this.RUserId;
	}

	public void setRUserId(Long RUserId) {
		this.RUserId = RUserId;
	}

	public String getRUserName() {
		return this.RUserName;
	}

	public void setRUserName(String RUserName) {
		this.RUserName = RUserName;
	}

	public String getRMobile() {
		return this.RMobile;
	}

	public void setRMobile(String RMobile) {
		this.RMobile = RMobile;
	}

	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks1() {
		return this.remarks1;
	}

	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}

}