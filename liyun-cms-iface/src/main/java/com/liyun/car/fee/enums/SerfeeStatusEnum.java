package com.liyun.car.fee.enums;

import com.liyun.car.common.service.Enum;

public enum SerfeeStatusEnum implements Enum{

	NULL("","--请选择--"),
	CALMIT("01","计算完成"),
	PREAPP("02","待复核"),
	APPYES("03","复核通过"),
	APPNO("04","复核不通过"),
	GENZH("05","提交财务复核"),
	CWNO("06","财务退回"),
	CWYES("07","财务确认");
	
	private String name;
	private String value;
	
	private SerfeeStatusEnum(String value,String name){
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
