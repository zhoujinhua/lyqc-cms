package com.liyun.car.activity.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.common.serializer.EnumSerializer;

/**
 * 顶级科目
 * @author zhoufei
 *
 */

public class CmsParamTop implements Serializable{

	private Integer id;
	private String topParamEn;
	private String topParamNm;
	private Integer paramTyp = 1;
	private ParamStatusEnum stt;
	private Date crtTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTopParamEn() {
		return topParamEn;
	}
	public void setTopParamEn(String topParamEn) {
		this.topParamEn = topParamEn;
	}
	public String getTopParamNm() {
		return topParamNm;
	}
	public void setTopParamNm(String topParamNm) {
		this.topParamNm = topParamNm;
	}
	public Integer getParamTyp() {
		return paramTyp;
	}
	public void setParamTyp(Integer paramTyp) {
		this.paramTyp = paramTyp;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public ParamStatusEnum getStt() {
		return stt;
	}
	public void setStt(ParamStatusEnum stt) {
		this.stt = stt;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	
	
}
