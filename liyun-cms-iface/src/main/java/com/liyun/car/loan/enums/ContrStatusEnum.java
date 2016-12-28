package com.liyun.car.loan.enums;

import com.liyun.car.common.service.Enum;

public enum ContrStatusEnum implements Enum{
	NULL("","--请选择--"),
	NORMAL("1","正常"),
	CANCLE("0","取消");
	
	private String name;
	private String value;
	
	private ContrStatusEnum(String value,String name){
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
