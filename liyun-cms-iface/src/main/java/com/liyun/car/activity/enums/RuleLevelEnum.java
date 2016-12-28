package com.liyun.car.activity.enums;

import com.liyun.car.common.service.Enum;

public enum RuleLevelEnum implements Enum{
	NULL("","--请选择--"),
	APP("0","合同"),
	/*DEALER("1","经销商")*/;
	
	private String name;
	private String value;
	
	private RuleLevelEnum(String value,String name){
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
