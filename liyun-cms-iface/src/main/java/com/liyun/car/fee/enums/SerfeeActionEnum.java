package com.liyun.car.fee.enums;

import com.liyun.car.common.service.Enum;

public enum SerfeeActionEnum implements Enum{

	NULL("","--请选择--"),
	SUBMIT("01","确认结果并提交复核"),
	MODIFY("02","上传服务费调整文件"),
	APPROVAL("03","服务费计算结果复核"),
	RECHECK("04","确认结果并提交财务检核"),
	DOWCAL("05","检核服务费计算结果"),
	FREEZE("06","冻结服务费批次操作"),
	UNFREEZE("07","解冻服务费批次操作"),
	FOREND("08","强制结束服务费流程");
	
	private String name;
	private String value;
	
	private SerfeeActionEnum(String value,String name){
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
