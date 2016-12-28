package com.liyun.car.loan.enums;

import com.liyun.car.common.service.Enum;

public enum CarType2Enum implements Enum{
	CYC("0","乘用车"),
	LCV("1","LCV"),
	MMPV("2","MMPV"),
	SWC("3","商务车");
	
	private String name;
	private String value;
	
	private CarType2Enum(String value,String name){
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
	
	public static CarType2Enum[] getCarTypeEnums(){
		return new CarType2Enum[]{CYC,LCV,MMPV};
	}
	
	public static String getCarTypeEnumZtree(){
		StringBuffer sb = new StringBuffer("[{id:\"999999\",pId:\"0\",name:\"车型\",isParent:true},");
		int i = 0;
		for(CarType2Enum carTypeEnum : CarType2Enum.getCarTypeEnums()){
			sb.append("{id:\""+(i+10000)+"\",pId:\"999999\",name:\""+carTypeEnum.getName()+"\",code:\""+carTypeEnum.getValue()+"\"},");
			i++;
		}
		return sb.toString().substring(0,sb.length()-1)+"]";
	}
	
	public static String getNameByValue(String enumValue){
		if(enumValue!=null && !"".equals(enumValue)){
			for(CarType2Enum carTypeEnum : CarType2Enum.values()){
				if(enumValue.equals(carTypeEnum.getValue())){
					return carTypeEnum.getName();
				}
			}
		}
		return null;
	}
}
