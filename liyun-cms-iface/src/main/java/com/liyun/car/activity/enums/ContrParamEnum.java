package com.liyun.car.activity.enums;

import java.util.ArrayList;
import java.util.List;
import com.liyun.car.common.service.Enum;

public enum ContrParamEnum implements Enum{
	Rcarloanamount("rcarloanAmount","批复车辆贷款金额","1"),
	Rloanamount("rloanAmount","批复贷款金额","1"),
	Rgpsfee("rGPSFee","GPS费用","1"),
	Ryanbaofee("ryanbaoFee","批复延保费","1"),
	Rloanperiods("rloanPeriods","批复申请还款期限(月)","1"),
	Rloanrate("rloanRate","批复申请贷款年利率(%)","1"),
	rDisTrueE("rDisTrueE","贴息金额","1"),
	rYanbaoFee("rYanbaoFee","融第二年保险金额","1"),
	gpsRebate("gpsRebate","GPS返佣金额","1"),
	rAccountFee("rAccountFee","账户管理费","1"),
	rComFee("rComFee","平台费金额","1"),
	
	
	;
	
	private String name;
	private String value;
	private String flag; //是否是金额
	
	private ContrParamEnum(String value,String name,String flag){
		this.value = value;
		this.name = name;
		this.flag = flag;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getValue() {
		return this.value;
	}
	
	public String getFlag() {
		return flag;
	}

	public static String getReachTypeEnumZtree(){
		StringBuffer sb = new StringBuffer("[{id:\"999999\",pId:\"0\",name:\"达标类型\",isParent:true,nocheck:true},");
		int i = 0;
		for(ContrParamEnum reachTypeEnum : ContrParamEnum.values()){
			sb.append("{id:\""+(i+10000)+"\",pId:\"999999\",name:\""+reachTypeEnum.getName()+"\",code:\""+reachTypeEnum.getValue()+"\"},");
			i++;
		}
		return sb.toString().substring(0,sb.length()-1)+"]";
	}
	
	public static String getNameByValue(String enumValue){
		if(enumValue!=null && !"".equals(enumValue)){
			for(ContrParamEnum reachTypeEnum : ContrParamEnum.values()){
				if(enumValue.equals(reachTypeEnum.getValue())){
					return reachTypeEnum.getName();
				}
			}
		}
		return null;
	}
	
	public static List<ContrParamEnum> getAmountParamEnum(){
		List<ContrParamEnum> list = new ArrayList<ContrParamEnum>();
		for(ContrParamEnum contrParamEnum : ContrParamEnum.values()){
			if(contrParamEnum.getFlag().equals("1")){
				list.add(contrParamEnum);
			}
		}
		return list;
	}
}
