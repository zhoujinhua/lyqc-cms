package com.liyun.car.activity.enums;

import com.liyun.car.common.service.Enum;

public enum SubjectStatusEnum implements Enum{

	PREON("0","待启用"),
	ON("1","启用"),
	OFF("2","停用");
	
	private String name;
	private String value;
	
	private SubjectStatusEnum(String value,String name){
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
