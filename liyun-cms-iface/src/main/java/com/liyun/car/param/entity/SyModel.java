package com.liyun.car.param.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.param.enums.ModelTypeEnum;

/**
 * 模板管理
 * @author zhoufei
 *
 */
public class SyModel implements Serializable{
	
	private Integer id;
	private String code;
	private String name;
	private String content;
	private ParamStatusEnum status;
	private ModelTypeEnum modelType;
	private Integer userId;
	private Date createTime;
	private String remark;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public ModelTypeEnum getModelType() {
		return modelType;
	}
	public void setModelType(ModelTypeEnum modelType) {
		this.modelType = modelType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public ParamStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ParamStatusEnum status) {
		this.status = status;
	}
	
}
