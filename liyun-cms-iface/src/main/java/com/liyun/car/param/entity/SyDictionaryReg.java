package com.liyun.car.param.entity;

/**
 * SyDictionaryReg entity. @author MyEclipse Persistence Tools
 */

public class SyDictionaryReg implements java.io.Serializable {

	// Fields
	private String regCode;
	private String regName;
	private String regCodePar;
	private String regLevel;
	private String memo;

	// Constructors

	/** default constructor */
	public SyDictionaryReg() {
	}

	/** minimal constructor */
	public SyDictionaryReg(String regCode, String regName, String regLevel) {
		this.regCode = regCode;
		this.regName = regName;
		this.regLevel = regLevel;
	}

	/** full constructor */
	public SyDictionaryReg(String regCode, String regName, String regCodePar,
			String regLevel, String memo) {
		this.regCode = regCode;
		this.regName = regName;
		this.regCodePar = regCodePar;
		this.regLevel = regLevel;
		this.memo = memo;
	}

	// Property accessors

	public String getRegCode() {
		return this.regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getRegName() {
		return this.regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getRegCodePar() {
		return this.regCodePar;
	}

	public void setRegCodePar(String regCodePar) {
		this.regCodePar = regCodePar;
	}

	public String getRegLevel() {
		return this.regLevel;
	}

	public void setRegLevel(String regLevel) {
		this.regLevel = regLevel;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}