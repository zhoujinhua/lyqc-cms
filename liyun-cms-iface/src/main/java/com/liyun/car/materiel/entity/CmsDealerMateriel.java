package com.liyun.car.materiel.entity;

import java.util.Date;
import org.activiti.engine.task.Task;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.materiel.enums.DealerMaterielStatusEnum;


public class CmsDealerMateriel implements java.io.Serializable {

	// Fields

	public static String FLOW_NAME = "materiel";
	private Integer mtrlAppCode;
	private Integer dealerCode;
	private String dealerName;
	private Integer companyCode;
	private String companyName;
	private CmsMaterielInfo info;
	private String mtrlCode;
	private String mtrlNm;
	private Integer aMtrlCnt;
	private Integer rMtrlCnt;
	private Double rMtrlAmt;
	private String trackNum;
	private DealerMaterielStatusEnum status;
	private Date appDt;
	private Date grantDt;
	private Date cofimDt;
	private String remarks;
	
	private Task task;
	
	// Constructors

	/** default constructor */
	public CmsDealerMateriel() {
	}

	// Property accessors

	public Integer getMtrlAppCode() {
		return this.mtrlAppCode;
	}

	public void setMtrlAppCode(Integer mtrlAppCode) {
		this.mtrlAppCode = mtrlAppCode;
	}

	public Integer getDealerCode() {
		return this.dealerCode;
	}

	public void setDealerCode(Integer dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Integer getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	public String getMtrlCode() {
		return this.mtrlCode;
	}

	public void setMtrlCode(String mtrlCode) {
		this.mtrlCode = mtrlCode;
	}

	public String getMtrlNm() {
		return this.mtrlNm;
	}

	public void setMtrlNm(String mtrlNm) {
		this.mtrlNm = mtrlNm;
	}

	public Integer getaMtrlCnt() {
		return aMtrlCnt;
	}

	public void setaMtrlCnt(Integer aMtrlCnt) {
		this.aMtrlCnt = aMtrlCnt;
	}

	public Integer getrMtrlCnt() {
		return rMtrlCnt;
	}

	public void setrMtrlCnt(Integer rMtrlCnt) {
		this.rMtrlCnt = rMtrlCnt;
	}

	public Double getrMtrlAmt() {
		return rMtrlAmt;
	}

	public void setrMtrlAmt(Double rMtrlAmt) {
		this.rMtrlAmt = rMtrlAmt;
	}

	public String getTrackNum() {
		return this.trackNum;
	}

	public void setTrackNum(String trackNum) {
		this.trackNum = trackNum;
	}

	@JsonSerialize(using = EnumSerializer.class)
	public DealerMaterielStatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(DealerMaterielStatusEnum status) {
		this.status = status;
	}

	public Date getAppDt() {
		return this.appDt;
	}

	public void setAppDt(Date appDt) {
		this.appDt = appDt;
	}

	public Date getGrantDt() {
		return this.grantDt;
	}

	public void setGrantDt(Date grantDt) {
		this.grantDt = grantDt;
	}

	public Date getCofimDt() {
		return cofimDt;
	}

	public void setCofimDt(Date cofimDt) {
		this.cofimDt = cofimDt;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public CmsMaterielInfo getInfo() {
		return info;
	}

	public void setInfo(CmsMaterielInfo info) {
		this.info = info;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	
}