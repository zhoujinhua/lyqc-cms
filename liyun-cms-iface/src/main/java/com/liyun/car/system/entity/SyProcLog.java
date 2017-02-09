package com.liyun.car.system.entity;

import java.io.Serializable;
import java.util.Date;

public class SyProcLog implements Serializable{

	private Long logId;
	
	private Date rcdDt;
	
	private String procEnglishName;
	
	private String procChineseName;
	
	private Date strDt;
	
	private Date endDt;
	
	private Integer runDt;
	
	private Integer stepNum;
	
	private Integer rcdCnt;
	
	private String rtnSqlcode;
	
	private String rtnMsg;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Date getRcdDt() {
		return rcdDt;
	}

	public void setRcdDt(Date rcdDt) {
		this.rcdDt = rcdDt;
	}

	public String getProcEnglishName() {
		return procEnglishName;
	}

	public void setProcEnglishName(String procEnglishName) {
		this.procEnglishName = procEnglishName;
	}

	public String getProcChineseName() {
		return procChineseName;
	}

	public void setProcChineseName(String procChineseName) {
		this.procChineseName = procChineseName;
	}

	public Date getStrDt() {
		return strDt;
	}

	public void setStrDt(Date strDt) {
		this.strDt = strDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Integer getRunDt() {
		return runDt;
	}

	public void setRunDt(Integer runDt) {
		this.runDt = runDt;
	}

	public Integer getStepNum() {
		return stepNum;
	}

	public void setStepNum(Integer stepNum) {
		this.stepNum = stepNum;
	}

	public Integer getRcdCnt() {
		return rcdCnt;
	}

	public void setRcdCnt(Integer rcdCnt) {
		this.rcdCnt = rcdCnt;
	}

	public String getRtnSqlcode() {
		return rtnSqlcode;
	}

	public void setRtnSqlcode(String rtnSqlcode) {
		this.rtnSqlcode = rtnSqlcode;
	}

	public String getRtnMsg() {
		return rtnMsg;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

}
