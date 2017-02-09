package com.liyun.car.loan.enums;

import com.liyun.car.common.service.Enum;
/**
 * 
 * @author zhoufei
 * 贷款期限
 */
public enum LoanPeriods2Enum implements Enum{

	SIX("6","6"),
	TWELVE("12","12"),
	EIGHTEEN("18","18"),
	TWENTYFOUR("24","24"),
	THIRTY("30","30"),
	THIRTYSIX("36","36");
	
	private String name;
	private String value;
	
	private LoanPeriods2Enum(String value,String name){
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
	
	public static String getLoanPeriodsEnumZtree(){
		StringBuffer sb = new StringBuffer("[{id:\"999999\",pId:\"0\",name:\"贷款期限\",isParent:true},");
		int i = 0;
		for(LoanPeriods2Enum loanPeriodsEnum : LoanPeriods2Enum.values()){
			sb.append("{id:\""+(i+100)+"\",pId:\"999999\",name:\""+loanPeriodsEnum.getName()+"\",code:\""+loanPeriodsEnum.getValue()+"\"},");
			i++;
		}
		return sb.toString().substring(0,sb.length()-1)+"]";
	}
}
