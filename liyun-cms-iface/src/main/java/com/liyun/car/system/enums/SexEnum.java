package com.liyun.car.system.enums;

import com.liyun.car.common.service.Enum;

public enum SexEnum implements Enum{

	NULL("","--"),
	M("M","男"),
	F("F","女");
	
	private String name;
	private String value;
	
	private SexEnum(String value,String name){
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
	
	public static SexEnum[] valuesNotNull(){
		return new SexEnum[]{M,F};
	}
	
	/*public static String getNameByValue(String value){
		for(SexEnum sexEnum : SexEnum.values()){
			if(sexEnum.getValue().equals(value)){
				return sexEnum.getName()	;
			}
		}
		return null;
	}*/
}
