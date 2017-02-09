package com.liyun.car.system.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.common.serializer.EnumSerializer;


/**
 * 权限集管理
 * @author zhoufei
 *
 */
public class SyPermSet implements Serializable{

	private Integer id;
	private String permName;
	private String permType;
	private String permDesc;
	private ParamStatusEnum permStatus;
	private Date createTime;
	private Date updateTime;
	private String createUser;
	
	@JsonIgnore
	private List<SyItem> items;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		this.permName = permName;
	}
	public String getPermType() {
		return permType;
	}
	public void setPermType(String permType) {
		this.permType = permType;
	}
	public String getPermDesc() {
		return permDesc;
	}
	public void setPermDesc(String permDesc) {
		this.permDesc = permDesc;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public ParamStatusEnum getPermStatus() {
		return permStatus;
	}
	public void setPermStatus(ParamStatusEnum permStatus) {
		this.permStatus = permStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public List<SyItem> getItems() {
		return items;
	}
	public void setItems(List<SyItem> items) {
		this.items = items;
	}
	
	
}
