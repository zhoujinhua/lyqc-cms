package com.liyun.car.activity.entity;

import java.io.Serializable;

import com.liyun.car.activity.enums.ReachTypeEnum;
import com.liyun.car.activity.enums.RuleCycleEnum;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.hibernate.utils.EnumUtils;
import com.liyun.car.param.usertype.DictEnum;

public class CmsDealerRuleProp implements Serializable{
	
	private Integer id;
	private CmsActivityRule rule;
	private String isOld;
	private String carTyp;
	private String cyc;
	private String reachTyp;
	private String propValue;
	
	private String isOldZh;
	private String carTypZh;
	private String cycZh;
	private String reachTypZh;
	
	
	
	public CmsDealerRuleProp() {
		super();
	}
	public CmsDealerRuleProp(String isOld, String carTyp, String cyc,
			String reachTyp) {
		super();
		this.isOld = isOld;
		this.carTyp = carTyp;
		this.cyc = cyc;
		this.reachTyp = reachTyp;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CmsActivityRule getRule() {
		return rule;
	}
	public void setRule(CmsActivityRule rule) {
		this.rule = rule;
	}
	public String getIsOld() {
		return isOld;
	}
	public void setIsOld(String isOld) {
		this.isOld = isOld;
	}
	public String getCarTyp() {
		return carTyp;
	}
	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}
	public String getCyc() {
		return cyc;
	}
	public void setCyc(String cyc) {
		this.cyc = cyc;
	}
	public String getReachTyp() {
		return reachTyp;
	}
	public void setReachTyp(String reachTyp) {
		this.reachTyp = reachTyp;
	}
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	public String getIsOldZh() {
		if(this.isOld!=null && !"".equals(this.isOld)){
			String[] params = this.isOld.split(",");
			String isOldChina = "";
			for(String param : params){
				isOldChina += EnumUtils.valueOf(BooleanEnum.class, param).getValue()+",";
			}
			return isOldChina;
		}
		return isOldZh;
	}
	public String getCarTypZh() {
		if(this.carTyp!=null && !"".equals(this.carTyp)){
			String[] params = this.carTyp.split(",");
			String isOldChina = "";
			for(String param : params){
				isOldChina += DictEnum.nameOf("fwcl", param)+",";
			}
			return isOldChina;
		}
		return carTypZh;
	}
	public String getCycZh() {
		if(this.cyc!=null && !"".equals(this.cyc)){
			String[] params = this.cyc.split(",");
			String isOldChina = "";
			for(String param : params){
				isOldChina += EnumUtils.valueOf(RuleCycleEnum.class, param).getValue()+",";
			}
			return isOldChina;
		}
		return cycZh;
	}
	public String getReachTypZh() {
		if(this.reachTyp!=null && !"".equals(this.reachTyp)){
			String[] params = this.reachTyp.split(",");
			String isOldChina = "";
			for(String param : params){
				isOldChina += EnumUtils.valueOf(ReachTypeEnum.class, param).getValue()+",";
			}
			return isOldChina;
		}
		return reachTypZh;
	}
	

}
