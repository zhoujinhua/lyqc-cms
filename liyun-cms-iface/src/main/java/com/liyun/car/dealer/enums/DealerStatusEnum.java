package com.liyun.car.dealer.enums;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.liyun.car.common.service.Enum;
import com.liyun.car.common.utils.BeanInvokeUtils;

import net.sf.json.JSONObject;

public enum DealerStatusEnum implements Enum{

	NULL("","--请选择--","",""),
	NEW("1","新建","1","1"),
	ONLINEPREDEPTLEADER("2","上线申请待区域总监审核","1",""),
	ONLINEPRESO("3","上线申请待SO审核","1",""),
	ONLINEPRESOSD("4","上线申请待市场调查专员面试","1",""),
	ONLINEPREBOSS("5","上线申请待销售总监审核","1",""),
	ONLINEDEPTLEADERBACK("6","上线申请区域总监退回","1","1"),
	ONLINESOBACK("7","上线申请SO退回","1","1"),
	ONLINESOSDBACK("8","上线申请市场调查专员退回","1","1"),
	ONLINEBOSSBACK("9","上线申请销售总监退回","1","1"),
	OFFLINEPREDEPTLEADER("10","启用(下线申请待区域总监审核)","0","2"),
	OFFLINEPRESO("11","启用(下线申请待SO审核)","0","2"),
	OFFLINEPREBOSS("12","启用(下线申请待销售总监审核)","0","2"),
	OFFLINEDEPTLEADERBACK("13","启用(下线申请区域总监退回)","0","2"),
	OFFLINESOBACK("14","启用(下线申请SO退回)","0","2"),
	OFFLINEBOSSBACK("15","启用(下线申请销售总监退回)","0","2"),
	ONLINEPRE("16","待上线","1","2"),
	ONLINE("17","启用","0","2"),
	OFFLINEPRE("18","启用(待下线)","0","2"),
	OFFLINE("19","停用","0","");
	
	private String name;
	private String value;
	private String onLineFlag; //上下线标记
	private String editFlag; //编辑标记  1：AM可编辑  2：非AM可编辑
	
	private DealerStatusEnum(String value,String name,String onLineFlag,String editFlag){
		this.name = name;
		this.value = value;
		this.onLineFlag = onLineFlag;
		this.editFlag = editFlag;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getValue() {
		return this.value;
	}
	
	public String getOnLineFlag() {
		return onLineFlag;
	}

	public void setOnLineFlag(String onLineFlag) {
		this.onLineFlag = onLineFlag;
	}

	public String getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

	//单位门店正常状态
	public static DealerStatusEnum[] getOnOrPreLineStatus(){
		return new DealerStatusEnum[]{OFFLINEPREDEPTLEADER,OFFLINEPRESO,OFFLINEPREBOSS,OFFLINEDEPTLEADERBACK,
				OFFLINESOBACK,OFFLINEBOSSBACK,ONLINEPRE,ONLINE,OFFLINEPRE,OFFLINE};
	}
	
	public static DealerStatusEnum[] getOnlineStatus(){
		return new DealerStatusEnum[]{OFFLINEPREDEPTLEADER,OFFLINEPRESO,OFFLINEPREBOSS,OFFLINEDEPTLEADERBACK,
				OFFLINESOBACK,OFFLINEBOSSBACK,ONLINE,OFFLINEPRE};
	}
	public String getJson() throws Exception {
		Field[] fields = DealerStatusEnum.class.getDeclaredFields();
		Map<String,String> map = new HashMap<String,String>();
		for(Field field : fields){
			if(field.getType().getSimpleName().equals("String")){
				map.put(field.getName(), String.valueOf(BeanInvokeUtils.invokeMethod(this, field.getName())));
			}
		}
		return JSONObject.fromObject(map).toString();
	}

	public DealerStatusEnum getJson2(){
		return this;
	}
}
