package com.liyun.car.param.usertype;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.param.entity.SyDict;
import com.liyun.car.param.service.DictService;

public class DictEnum implements Serializable{
	
	private static DictService dictService ;
	
	private String code;
	private String name;
	private String value;
	
	public DictEnum() {
		
	}
	
	public DictEnum(String code) {
		super();
		this.code = code;
	}

	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	protected void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static DictService getDictService() {
		return dictService;
	}

	public static void setDictService(DictService dictService) {
		DictEnum.dictService = dictService;
	}

	/**
	 * 缺省的列表，当字典在字典表表中不存在时，取此列表
	 * 在子类中应覆盖此方法
	 * @return
	 */
	protected DictEnum[] getDefaultList(){
		return null;
	}
	
	/**
	 * 创建枚举实例
	 * @param value
	 * @param name
	 * @return
	 */
	protected static DictEnum create(String value,String name,String code){
		try {
			DictEnum dictEnum = new DictEnum();
			dictEnum.setName(name);
			dictEnum.setValue(value);
			dictEnum.setCode(code);
			return dictEnum;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(obj instanceof DictEnum){
			return ((DictEnum) obj).getCode().equals(this.getCode());
		}else{
			return this.getValue().equals(obj);
		}
	}
	
	public int hashCode(){
		return this.getCode().hashCode();
	}
	
	public static DictEnum valueOf(Class<?> clazz, Object value){
		SyDict dict = new SyDict();
		dict.setCode(String.valueOf(value));
		dict.setStatus(ParamStatusEnum.ON);
		
		List<SyDict> dicts = dictService.getEntitysByParams(dict, OperMode.EQ, "code","status");
		if(dicts == null || dicts.isEmpty()){
			return null;
		}
		dict = dicts.get(0);
		return create(dict.getValue(), dict.getName(), dict.getCode());
	}
	
	public static String nameOf(Object code, Object value){
		Collection<SyDict> cs = values(code);
		if(cs!=null && !cs.isEmpty()){
			for(SyDict c : cs){
				if(c.getValue()!=null && c.getValue().equals(value)){
					return c.getName();
				}
			}
		}
		return null;
	}
	
	public static Collection<SyDict> values(Object value){
		SyDict dict = new SyDict();
		dict.setDict(new SyDict(null, String.valueOf(value)));
		dict.setStatus(ParamStatusEnum.ON);
		dict.getDict().setStatus(ParamStatusEnum.ON);
		
		List<SyDict> dicts = dictService.getEntitysByParams(dict, OperMode.EQ, "status","dict.code");
		return dicts;
	}
}
