package com.liyun.car.materiel.enums;

import com.liyun.car.common.service.Enum;

public enum DealerMaterielStatusEnum implements Enum {
	NEW("1","新建"),
	PRECHECK("2","待审核"),
    SONO("3","so审批不通过"),
	SOYES("4","so审批通过"),
	SOSEND("5","物料已发出"),
	CONFIRM("6","确认签收"),
	AUTOCONFIRM("7","自动签收");
	
	private String name;
	private String value;
	
	private DealerMaterielStatusEnum(String value,String name){
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
