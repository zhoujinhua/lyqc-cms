package com.liyun.car.activity.enums;

import com.liyun.car.common.service.Enum;

public enum ActivityStatusEnum implements Enum{
	NULL("","--请选择--"),
	PRE("2","待运行"),
	OFF("0","已下线"),
	ON("1","已运行");
	
	private String name;
	private String value;
	
	private ActivityStatusEnum(String value,String name){
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
