package com.liyun.car.dealer.enums;

import com.liyun.car.common.service.Enum;

public enum DealerTypeEnum implements Enum{

	NULL("","--请选择--"),
	DD("1","DD"),
	SP("2","SP");
	
	private String name;
	private String value;
	
	private DealerTypeEnum(String value,String name){
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
