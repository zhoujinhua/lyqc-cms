package com.liyun.car.dealer.enums;

import com.liyun.car.common.service.Enum;

public enum DealerActionEnum implements Enum{

	NULL("","--请选择--"),
	CONLINE("1","经销商上下线审核"),
	DONLINE("2","门店上下线审核");
	
	private String name;
	private String value;
	
	private DealerActionEnum(String value,String name){
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
