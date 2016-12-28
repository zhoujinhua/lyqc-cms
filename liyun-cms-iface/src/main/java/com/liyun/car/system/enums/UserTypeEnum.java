package com.liyun.car.system.enums;

import com.liyun.car.common.service.Enum;

public enum UserTypeEnum implements Enum{

	NULL("","--请选择--"),
	M("M","美利车用户"),
	/*C("C","合作商户用户"),*/
	/*DC("DC","经销商单位用户"),*/
	DS("DS","经销商门店用户");
	
	private String name;
	private String value;
	
	private UserTypeEnum(String value,String name){
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
