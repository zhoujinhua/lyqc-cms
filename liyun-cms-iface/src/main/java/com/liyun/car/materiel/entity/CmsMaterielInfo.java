package com.liyun.car.materiel.entity;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.param.usertype.DictEnum;


/**
 * CmsMaterialInfo entity. @author MyEclipse Persistence Tools
 */

public class CmsMaterielInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String mtrlCode;
	private String mtrlNm;
	private DictEnum mtrlTyp;
	private DictEnum mtrlUnit;
	private String mtrlBrand;
	private ParamStatusEnum status;
	private Double price;
	private Date upTime;
	private String remarks;
	
	// Constructors

	/** default constructor */
	public CmsMaterielInfo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMtrlCode() {
		return mtrlCode;
	}

	public void setMtrlCode(String mtrlCode) {
		this.mtrlCode = mtrlCode;
	}

	public String getMtrlNm() {
		return mtrlNm;
	}

	public void setMtrlNm(String mtrlNm) {
		this.mtrlNm = mtrlNm;
	}

	public DictEnum getMtrlTyp() {
		return mtrlTyp;
	}

	public void setMtrlTyp(DictEnum mtrlTyp) {
		this.mtrlTyp = mtrlTyp;
	}

	public DictEnum getMtrlUnit() {
		return mtrlUnit;
	}

	public void setMtrlUnit(DictEnum mtrlUnit) {
		this.mtrlUnit = mtrlUnit;
	}

	public String getMtrlBrand() {
		return mtrlBrand;
	}

	public void setMtrlBrand(String mtrlBrand) {
		this.mtrlBrand = mtrlBrand;
	}
	@JsonSerialize(using = EnumSerializer.class)
	public ParamStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ParamStatusEnum status) {
		this.status = status;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getUpTime() {
		return upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}