package com.liyun.car.fee.enums;

import com.liyun.car.common.service.Enum;

public enum SerfeeBillActionEnum implements Enum{

	NULL("","--请选择--"),
	CODE1("01","SO确认生成账单"),
	CODE2("02","邮件发送账单"),
	CODE3("03","经销商用户发票登记"),
	CODE4("04","美利用户发票登记"),
	CODE5("05","暂缓服务费发放"),
	CODE6("06","下载放款文件"),
	CODE7("07","美利用户确认放款结清"),
	CODE8("08","经销商用户确认放款结清"),
	CODE9("09","财务退回服务费计算结果"),
	CODE10("10","财务确认服务费计算结果");
	
	private String name;
	private String value;
	
	private SerfeeBillActionEnum(String value,String name){
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
