package com.liyun.car.fee.entity;

import java.io.Serializable;
import java.util.Date;

public class CmsDealerSerfeeAdjFile implements Serializable{

	private Integer id;
	private String feeMon;
	private Integer companyCode;
	private String companyName;
	private String subParamEn;
	private Double paramAmt;
	private Date upTime;
	private String fileDir;
	
	public CmsDealerSerfeeAdjFile(String feeMon, Integer companyCode,
			String companyName, String subParamEn, Double paramAmt,
			Date upTime, String fileDir) {
		super();
		this.feeMon = feeMon;
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.subParamEn = subParamEn;
		this.paramAmt = paramAmt;
		this.upTime = upTime;
		this.fileDir = fileDir;
	}
	public CmsDealerSerfeeAdjFile() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFeeMon() {
		return feeMon;
	}
	public void setFeeMon(String feeMon) {
		this.feeMon = feeMon;
	}
	public Integer getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSubParamEn() {
		return subParamEn;
	}
	public void setSubParamEn(String subParamEn) {
		this.subParamEn = subParamEn;
	}
	public Double getParamAmt() {
		return paramAmt;
	}
	public void setParamAmt(Double paramAmt) {
		this.paramAmt = paramAmt;
	}
	public Date getUpTime() {
		return upTime;
	}
	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	
	
}
