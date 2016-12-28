package com.liyun.car.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件发送历史
 * @author zhoufei
 *
 */
public class SyEmailLog implements Serializable{

	private Integer id;
	private String batchNo;
	/*private Integer companyCode;
	private String companyName;
	private String feeMons;*/
	private String status;
	private String mailAddress;
	private String ccMailAddress;
	private String remark;
	private Date crtTime;
	
	public SyEmailLog(/*Integer companyCode,String companyName, String feeMons,*/
			String batchNo,String status,String mailAddress,String ccMailAddress, String remark,Date crtTime) {
		super();
		/*this.companyCode = companyCode;
		this.companyName = companyName;*/
		this.batchNo = batchNo;
		/*this.feeMons = feeMons;*/
		this.status = status;
		this.mailAddress = mailAddress;
		this.ccMailAddress = ccMailAddress;
		this.crtTime = crtTime;
	}
	public SyEmailLog() {
		super();
	}
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/*public Integer getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}
	public String getFeeMons() {
		return feeMons;
	}
	public void setFeeMons(String feeMons) {
		this.feeMons = feeMons;
	}*/
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	/*public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}*/
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getCcMailAddress() {
		return ccMailAddress;
	}
	public void setCcMailAddress(String ccMailAddress) {
		this.ccMailAddress = ccMailAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
