package com.liyun.car.activity.enums;

import com.liyun.car.common.service.Enum;

public enum ReachAmountEnum implements Enum{
	appCnt("appCnt","申请数"),
	ctrCnt("ctrCnt","合同数"),
	ctrAmt("ctrAmt","合同金额"),
	exRatio("exRatio","转化率"),
	bdebtCnt("bdebtCnt","坏账数"),
	bdebtCntRatio("bdebtCntRatio","坏账数占比"),
	bdebtAmt("bdebtAmt","坏账金额"),
	bdebtAmtRatio("bdebtAmtRatio","坏账金额占比"),
	gpsCnt("gpsCnt","GPS数量"),
	gpsCntRatio("gpsCntRatio","GPS数量占比"),
	gpsAmt("gpsAmt","GPS金额"),
	CODE15("gpsAmtRatio","GPS金额占比");
	
	private String name;
	private String value;
	
	private ReachAmountEnum(String value,String name){
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
	
	public static String getNameByValue(String enumValue){
		if(enumValue!=null && !"".equals(enumValue)){
			for(ReachAmountEnum reachTypeEnum : ReachAmountEnum.values()){
				if(enumValue.equals(reachTypeEnum.getValue())){
					return reachTypeEnum.getName();
				}
			}
		}
		return null;
	}
}
