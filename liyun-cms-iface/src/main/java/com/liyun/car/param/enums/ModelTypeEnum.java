package com.liyun.car.param.enums;

import com.liyun.car.common.service.Enum;

/**
 * 模板
 * @author zhoufei
 *
 */
public enum ModelTypeEnum implements Enum{

	NULL("","--"),
	MAIL("01","邮件模板");
	
	private String name;
	private String value;
	
	private ModelTypeEnum(String value,String name){
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
