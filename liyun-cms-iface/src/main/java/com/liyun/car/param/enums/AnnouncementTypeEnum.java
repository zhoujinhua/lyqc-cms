package com.liyun.car.param.enums;

import com.liyun.car.common.service.Enum;

public enum AnnouncementTypeEnum implements Enum{

	NULL("","--"),
	AA("01","活动公告"),
	YA("02","运维公告"),
	MA("03","消息通知");
	
	private String name;
	private String value;
	
	private AnnouncementTypeEnum(String value,String name){
		this.value = value;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
