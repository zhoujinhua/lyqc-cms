package com.liyun.car.activity.enums;

import com.liyun.car.common.service.Enum;

public enum ActivityCycleEnum implements Enum{
	NULL("","--请选择--"),
	WHOLE("0","整个活动周期"),
	EACHMON("1","每月");
	
	private String name;
	private String value;
	
	private ActivityCycleEnum(String value,String name){
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
