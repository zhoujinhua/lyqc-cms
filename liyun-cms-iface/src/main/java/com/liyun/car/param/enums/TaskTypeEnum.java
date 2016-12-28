package com.liyun.car.param.enums;

import com.liyun.car.common.service.Enum;
/**
 * 任务类型
 * @author zhoufei
 *
 */
public enum TaskTypeEnum implements Enum{
	
	MATERIEL("materiel","物料申请审核","100203"),
	COMPANY("company","经销商上下线审核","100302"),
	DEALER("dealer","经销商门店上下线审核", "100304"),
	SERFEE("serFee","服务费复核", "100503"),
	DC("dc","经销商任务","");
	
	private String name;
	private String value;
	private String itemId; //权限ID
	
	private TaskTypeEnum(String value,String name,String itemId){
		this.name = name;
		this.value = value;
		this.itemId = itemId;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	
}
