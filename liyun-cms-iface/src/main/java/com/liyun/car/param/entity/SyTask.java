package com.liyun.car.param.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.param.enums.TaskStatusEnum;
import com.liyun.car.param.enums.TaskTypeEnum;

/**
 * 待办信息表
 * 页面列所有待办和处理中以及当天处理完结的任务
 * @author zhoufei
 *
 */
public class SyTask implements Serializable{

	private Integer id;
	private String name;
	private TaskTypeEnum type;
	private TaskStatusEnum status;
	private String desc;
	private Date createTime;
	private Date finishTime;
	private String assoCode;
	private Integer userId;
	private BooleanEnum isHandler;
	
	
	
	public SyTask(String name, TaskTypeEnum type, TaskStatusEnum status,
			String desc, Date createTime, String assoCode, Integer userId) {
		super();
		this.name = name;
		this.type = type;
		this.status = status;
		this.desc = desc;
		this.createTime = createTime;
		this.assoCode = assoCode;
		this.userId = userId;
	}
	public SyTask() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public TaskTypeEnum getType() {
		return type;
	}
	public void setType(TaskTypeEnum type) {
		this.type = type;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public TaskStatusEnum getStatus() {
		return status;
	}
	public void setStatus(TaskStatusEnum status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAssoCode() {
		return assoCode;
	}
	public void setAssoCode(String assoCode) {
		this.assoCode = assoCode;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public BooleanEnum getIsHandler() {
		return isHandler;
	}
	public void setIsHandler(BooleanEnum isHandler) {
		this.isHandler = isHandler;
	}
	
	
}
