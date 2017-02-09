package com.liyun.car.dealer.enums;

import com.liyun.car.common.service.Enum;

public enum BillPayStatusEnum implements Enum{

	/*NULL("","--请选择--"),*/
	NMFF("1","正常发放"),
	STFF("2","暂缓发放");
	
	private String name;
	private String value;
	
	private BillPayStatusEnum(String value,String name){
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
