package com.liyun.car.dealer.enums;

import com.liyun.car.common.service.Enum;

/**
 * 销售区域
 * @author zhoufei
 *
 */
public enum SaleAreaEnum implements Enum{

	NULL("","--请选择--"),
	HD1("华东1区","华东1区"),
	HD2("华东2区","华东2区"),
	HN("华南区","华南区"),
	XN("西南区","西南区"),
	XB("西北区","西北区"),
	DB("东北区","东北区"),
	HD("华东区","华东区"),
	HB("华北区","华北区");
	
	private String name;
	private String value;
	
	private SaleAreaEnum(String value,String name){
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
