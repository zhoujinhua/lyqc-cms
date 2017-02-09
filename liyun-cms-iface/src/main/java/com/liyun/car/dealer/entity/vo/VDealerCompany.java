package com.liyun.car.dealer.entity.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.activiti.engine.task.Task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.dealer.enums.BillPayStatusEnum;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.dealer.enums.DealerTypeEnum;

public class VDealerCompany implements Serializable{
	
	public static final String FLOW_NAME = "company";
	private Integer companyCode;
	private String companyName;
	private String province;
	private String city;
	private DealerStatusEnum status;
	private BillPayStatusEnum payStt;
	private Date updateTime;
	private Integer tempCompanyCode;
	private DealerTypeEnum companyType;
	private BooleanEnum isDeposit;
	private Double depositAmt;
	
	private List<VAmDealerCompany> amDealerCompanys;
	private String amUserIds;
	
	private Task task;
	
	public VDealerCompany(Integer companyCode) {
		super();
		this.companyCode = companyCode;
	}
	
	public VDealerCompany(Integer companyCode, Integer tempCompanyCode) {
		super();
		this.companyCode = companyCode;
		this.tempCompanyCode = tempCompanyCode;
	}

	public VDealerCompany() {
		super();
	}
	public Integer getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public DealerStatusEnum getStatus() {
		return status;
	}
	public void setStatus(DealerStatusEnum status) {
		this.status = status;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public BillPayStatusEnum getPayStt() {
		return payStt;
	}
	public void setPayStt(BillPayStatusEnum payStt) {
		this.payStt = payStt;
	}
	public Integer getTempCompanyCode() {
		return tempCompanyCode;
	}
	public void setTempCompanyCode(Integer tempCompanyCode) {
		this.tempCompanyCode = tempCompanyCode;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public DealerTypeEnum getCompanyType() {
		return companyType;
	}
	public void setCompanyType(DealerTypeEnum companyType) {
		this.companyType = companyType;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public BooleanEnum getIsDeposit() {
		return isDeposit;
	}
	public void setIsDeposit(BooleanEnum isDeposit) {
		this.isDeposit = isDeposit;
	}
	public Double getDepositAmt() {
		return depositAmt;
	}
	public void setDepositAmt(Double depositAmt) {
		this.depositAmt = depositAmt;
	}

	public List<VAmDealerCompany> getAmDealerCompanys() {
		return amDealerCompanys;
	}

	public void setAmDealerCompanys(List<VAmDealerCompany> amDealerCompanys) {
		this.amDealerCompanys = amDealerCompanys;
	}

	public String getAmUserIds() {
		if(this.amDealerCompanys!=null && !this.amDealerCompanys.isEmpty()){
			String part = "";
			for(VAmDealerCompany amDealerCompany : this.amDealerCompanys){
				part += amDealerCompany.getUserId()+",";
			}
			return part;
		}
		return amUserIds;
	}
	
}
