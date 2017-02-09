package com.liyun.car.system.enums;

import com.liyun.car.common.service.Enum;

public enum UserStatusEnum implements Enum{

	NULL("","--请选择--"),
	N("N","已生效"),
	F("F","停用");
	
	private String name;
	private String value;
	
	private UserStatusEnum(String value,String name){
		this.name = name;
		this.value = value;
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
