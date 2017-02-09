package com.liyun.car.activity.enums;

import com.liyun.car.common.service.Enum;

public enum RuleReturnTypeEnum implements Enum{

	NULL("","--请选择--"),
	POINT("1","点数"),
	MONEY("2","金额");
	
	private String name;
	private String value;
	
	private RuleReturnTypeEnum(String value,String name){
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
