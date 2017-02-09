package com.liyun.car.param.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.common.serializer.EnumSerializer;

/**
 * 发送者
 * @author zhoufei
 *
 */
public class SyMailSender implements Serializable{

	private Integer id;
	private String userName;
	private String email;
	private String password;
	private BooleanEnum isDefault;
	private Date createTime;
	private ParamStatusEnum status;
	private String remark;
	
	public SyMailSender(Integer id, ParamStatusEnum status) {
		super();
		this.id = id;
		this.status = status;
	}
	public SyMailSender() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public BooleanEnum getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(BooleanEnum isDefault) {
		this.isDefault = isDefault;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public ParamStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ParamStatusEnum status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
