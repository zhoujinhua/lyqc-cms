package com.liyun.car.activity.enums;

import com.liyun.car.common.service.Enum;

public enum RuleRewardTypeEnum implements Enum{

	NULL("","--请选择--"),
	REWARD("1","奖励"),
	FINE("2","惩罚");
	
	private String name;
	private String value;
	
	private RuleRewardTypeEnum(String value,String name){
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
