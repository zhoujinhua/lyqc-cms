package com.liyun.car.activity.enums;

import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.service.Enum;
import com.liyun.car.param.entity.SyDict;
import com.liyun.car.param.usertype.DictEnum;

public enum ReachTypeEnum implements Enum{
	CODE1("1","申请数最大值"),
	CODE2("2","申请数最小值"),
	CODE3("3","合同数最大值"),
	CODE4("4","合同数最小值"),
	CODE5("5","合同金额最大值"),
	CODE6("6","合同金额最小值"),
	CODE7("7","转化率最大值"),
	CODE8("8","转化率最小值"),
	CODE9("9","坏账数最大值"),
	CODE10("10","坏账数最小值"),
	CODE11("11","坏账数占比最大值"),
	CODE12("12","坏账数占比最小值"),
	CODE13("13","坏账金额最大值"),
	CODE14("14","坏账金额最小值"),
	CODE15("15","坏账金额占比最大值"),
	CODE16("16","坏账金额占比最小值"),
	CODE17("17","GPS数量最大值"),
	CODE18("18","GPS数量最小值"),
	CODE19("19","GPS数量占比最大值"),
	CODE20("20","GPS数量占比最小值"),
	CODE21("21","GPS金额最大值"),
	CODE22("22","GPS金额最小值"),
	CODE23("23","GPS金额占比最大值"),
	CODE24("24","GPS金额占比最小值");
	
	private String name;
	private String value;
	
	private ReachTypeEnum(String value,String name){
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
	
	
	public static String getReachTypeEnumZtree(){
		StringBuffer sb = new StringBuffer("[{id:\"999999\",pId:\"0\",name:\"达标类型\",isParent:true,nocheck:true},");
		int i = 0;
		for(ReachTypeEnum reachTypeEnum : ReachTypeEnum.values()){
			sb.append("{id:\""+(i+10000)+"\",pId:\"999999\",name:\""+reachTypeEnum.getName()+"\",code:\""+reachTypeEnum.getValue()+"\"},");
			i++;
		}
		return sb.toString().substring(0,sb.length()-1)+"]";
	}
	
	public static String getNameByValue(String enumValue){
		if(enumValue!=null && !"".equals(enumValue)){
			for(ReachTypeEnum reachTypeEnum : ReachTypeEnum.values()){
				if(enumValue.equals(reachTypeEnum.getValue())){
					return reachTypeEnum.getName();
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		for(BooleanEnum booleanEnum : BooleanEnum.values()){
			for(SyDict carTypeEnum : DictEnum.values("fwcl")){
				for(RuleCycleEnum ruleCycleEnum : RuleCycleEnum.values()){
					for(ReachTypeEnum reachTypeEnum : ReachTypeEnum.values()){
						System.out.println("insert into cms_activity_prop_dic_dealer (prop_code,prop_name,is_old,car_typ,cyc,reach_typ) values ('"+booleanEnum.getValue()+carTypeEnum.getValue()+ruleCycleEnum.getValue()+reachTypeEnum.getValue()+"'"
								+ ",'"+booleanEnum.getValue()+carTypeEnum.getValue()+ruleCycleEnum.getValue()+reachTypeEnum.getValue()+"','"+booleanEnum.getValue()+"','"+carTypeEnum.getValue()+"','"+ruleCycleEnum.getValue()+"','"+reachTypeEnum.getValue()+"');");
						//System.out.println(reachTypeEnum.getValue());
					}
				}
			}
		}
	}
}
