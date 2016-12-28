package com.liyun.car.activity.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.common.serializer.EnumSerializer;

public class CmsActivityRuleGroup implements Serializable{

	private Integer id;
	private String exCode;
	private String exName;
	private ParamStatusEnum status;
	private CmsActivityInfo activityInfo;
	private String remark;
	private Date createTime;
	
	@JsonIgnore
	private List<CmsActivityRuleEx> exProps;
	@JsonIgnore
	private String propRuleIds;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setPropRuleIds(String propRuleIds) {
		this.propRuleIds = propRuleIds;
	}
	public String getExCode() {
		return exCode;
	}
	public void setExCode(String exCode) {
		this.exCode = exCode;
	}
	public String getExName() {
		return exName;
	}
	public void setExName(String exName) {
		this.exName = exName;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public ParamStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ParamStatusEnum status) {
		this.status = status;
	}
	public CmsActivityInfo getActivityInfo() {
		return activityInfo;
	}
	public void setActivityInfo(CmsActivityInfo activityInfo) {
		this.activityInfo = activityInfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<CmsActivityRuleEx> getExProps() {
		return exProps;
	}
	public void setExProps(List<CmsActivityRuleEx> exProps) {
		this.exProps = exProps;
	}
	public String getPropRuleIds() {
		if(this.exProps!=null &&!this.exProps.isEmpty()){
			String propId = "";
			for(CmsActivityRuleEx prop : this.exProps){
				propId += prop.getRuleId() + ",";
			}
			return propId;
		}
		return propRuleIds;
	}
}
