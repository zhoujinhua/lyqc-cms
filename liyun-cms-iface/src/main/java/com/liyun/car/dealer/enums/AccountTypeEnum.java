package com.liyun.car.dealer.enums;

import com.liyun.car.common.service.Enum;

public enum AccountTypeEnum implements Enum{

	NULL("","--请选择--"),
	PUBLIC("1","公户"),
	PRIVATE("0","私户");
	
	private String name;
	private String value;
	
	private AccountTypeEnum(String value,String name){
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
