package com.liyun.car.materiel.enums;

import com.liyun.car.common.service.Enum;

public enum DealerMaterielActionEnum implements Enum {
	SUBMIT("1","门店提交审核"),
	APPROVAL("2","SO审核"),
	SEND("3","物料发送"),
    CONFIRM("4","经销商签收"),
    SYSCFM("5","系统自动签收"),
    FOREND("6","强制结束流程");
	
	private String name;
	private String value;
	
	private DealerMaterielActionEnum(String value,String name){
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
