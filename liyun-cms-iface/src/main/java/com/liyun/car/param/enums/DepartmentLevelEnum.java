package com.liyun.car.param.enums;

import com.liyun.car.common.service.Enum;

public enum DepartmentLevelEnum implements Enum{

	NULL("","--请选择--"),
	LEVELONE("1","机构"),
	LEVELTWO("2","部门"),
	LEVELTHR("3","小组");
	
	private String name;
	private String value;
	
	private DepartmentLevelEnum(String value,String name){
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
