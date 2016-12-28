package com.liyun.car.activity.enums;

import com.liyun.car.common.service.Enum;

public enum SubjectTypeEnum implements Enum{

	FIRST("1","一级科目"),
	SECOND("2","二级科目");
	
	private String name;
	private String value;
	
	private SubjectTypeEnum(String value,String name){
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
