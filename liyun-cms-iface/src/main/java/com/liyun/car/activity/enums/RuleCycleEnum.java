package com.liyun.car.activity.enums;

import com.liyun.car.common.service.Enum;

public enum RuleCycleEnum implements Enum{
	HOLE("0","历史整个周期"),
	ONEMON("1","近一个月"),
	TWOMON("2","近二个月"),
	THRMON("3","近三个月"),
	FORMON("4","近四个月"),
	FIVMON("5","近五个月"),
	SIXMON("6","近六个月"),
	CURYEAR("7","当年"),
	YESYEAR("8","去年");
	
	private String name;
	private String value;
	
	private RuleCycleEnum(String value,String name){
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
	
	public static String getRuleCycleEnumZtree(){
		StringBuffer sb = new StringBuffer("[{id:\"999999\",pId:\"0\",name:\"周期\",isParent:true},");
		int i = 0;
		for(RuleCycleEnum ruleCycleEnum : RuleCycleEnum.values()){
			sb.append("{id:\""+(i+10000)+"\",pId:\"999999\",name:\""+ruleCycleEnum.getName()+"\",code:\""+ruleCycleEnum.getValue()+"\"},");
			i++;
		}
		return sb.toString().substring(0,sb.length()-1)+"]";
	}
	
	public static String getNameByValue(String enumValue){
		if(enumValue!=null && !"".equals(enumValue)){
			for(RuleCycleEnum ruleCycleEnum : RuleCycleEnum.values()){
				if(enumValue.equals(ruleCycleEnum.getValue())){
					return ruleCycleEnum.getName();
				}
			}
		}
		return null;
	}
}
