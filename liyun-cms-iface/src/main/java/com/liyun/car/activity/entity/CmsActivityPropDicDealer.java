package com.liyun.car.activity.entity;

import java.io.Serializable;
import java.util.Date;

import com.liyun.car.activity.enums.ReachTypeEnum;
import com.liyun.car.activity.enums.RuleCycleEnum;
import com.liyun.car.common.enums.BooleanEnum;

/**
 * 
 * @author zhoufei
 *
 */
public class CmsActivityPropDicDealer implements Serializable{

	private String propCode;
	private String propName;
	private String propDesc;
	private BooleanEnum isOld;
	private String carTyp;
	private RuleCycleEnum cyc;
	private ReachTypeEnum reachTyp;
	private Date crtTime;
	
	public String getPropCode() {
		return propCode;
	}
	public void setPropCode(String propCode) {
		this.propCode = propCode;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public String getPropDesc() {
		return propDesc;
	}
	public void setPropDesc(String propDesc) {
		this.propDesc = propDesc;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public BooleanEnum getIsOld() {
		return isOld;
	}
	public void setIsOld(BooleanEnum isOld) {
		this.isOld = isOld;
	}
	public String getCarTyp() {
		return carTyp;
	}
	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}
	public RuleCycleEnum getCyc() {
		return cyc;
	}
	public void setCyc(RuleCycleEnum cyc) {
		this.cyc = cyc;
	}
	public ReachTypeEnum getReachTyp() {
		return reachTyp;
	}
	public void setReachTyp(ReachTypeEnum reachTyp) {
		this.reachTyp = reachTyp;
	}
	
}
