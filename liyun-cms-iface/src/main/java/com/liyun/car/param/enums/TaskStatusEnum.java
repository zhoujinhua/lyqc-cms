package com.liyun.car.param.enums;

import com.liyun.car.common.service.Enum;
/**
 * 任务类型
 * @author zhoufei
 *
 */
public enum TaskStatusEnum implements Enum{
	
	PRE("01","待处理"),
	RUN("02","处理中"),
	END("03","处理完结");
	
	private String name;
	private String value;
	
	private TaskStatusEnum(String value,String name){
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
