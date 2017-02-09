package com.liyun.car.fee.enums;

import com.liyun.car.common.service.Enum;

public enum SerfeeBillStatusEnum implements Enum{

	NULL("","--请选择--"),
	INIT("01","账单初始化"),
	/*ZDCP("02","账单生成"),
	ZDPS("03","账单待发送"),*/
	ZDAS("04","账单已发送"),
	ZDSE("05","账单发送失败"),
	FPAS("06","发票已发送"),
	FPAR("07","发票已接收"),
	FKWA("08","等待放款"),
	FKWC("09","已放款结清"),
	QRWC("10","经销商确认收款");
	
	private String name;
	private String value;
	
	private SerfeeBillStatusEnum(String value,String name){
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

	/**
	 * 获取发票登记可见账单状态
	 * @return
	 */
	public static SerfeeBillStatusEnum[] getInvoiceBillStatus(){
		return new SerfeeBillStatusEnum[]{/*ZDPS,*/ZDAS,ZDSE,FPAS,FPAR,FKWA,FKWC};
	}
	
	/**
	 * 获取账单管理可见的账单状态
	 * @return
	 */
	public static SerfeeBillStatusEnum[] getBillManagBillStatus(){
		return new SerfeeBillStatusEnum[]{/*ZDCP,ZDPS,*/INIT,ZDAS,ZDSE,FPAS,FPAR,FKWA,FKWC};
	}
	
	/**
	 * 获取经销商可见账单状态
	 */
	public static SerfeeBillStatusEnum[] getBillOwnerBillStatus(){
		return new SerfeeBillStatusEnum[]{/*ZDPS,*/ZDAS,ZDSE,FPAS,FPAR,FKWA,FKWC,QRWC};
	}
}
