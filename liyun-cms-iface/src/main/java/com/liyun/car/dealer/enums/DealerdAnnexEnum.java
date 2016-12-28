package com.liyun.car.dealer.enums;

import com.liyun.car.common.service.Enum;

public enum DealerdAnnexEnum implements Enum{

	CODE1("1","经销商上线信息表","0","1"),
	CODE2("2","经销商营业执照","0","1"),
	CODE3("3","组织机构代码证","1","1"),
	CODE4("4","税务登记证","1","1"),
	CODE5("5","开户许可证","0","1"),
	CODE6("6","法人身份证复印件","0","1"),
	CODE7("7","放款账户确认书","0","1"),
	CODE8("8","有效银行汇款回执","0","1"),
	CODE9("9","放款人身份证","0","1"),
	CODE10("10","放款银行卡","0","1"),
	CODE11("11","一般纳税人资质证","0","1"),
	CODE12("12","经销商印鉴卡","0","1");
	
	private String name;
	private String value;
	private String code; //公户私户 1公户  0私户 2公私都有 
	private String need; //是否必须 1是 0 否
	
	private DealerdAnnexEnum(String value,String name,String code,String need){
		this.name = name;
		this.value = value;
		this.code = code;
		this.need = need;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getValue() {
		return this.value;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNeed() {
		return need;
	}

	public void setNeed(String need) {
		this.need = need;
	}
}
