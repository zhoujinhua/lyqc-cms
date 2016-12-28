package com.liyun.car.dealer.entity;

import java.io.Serializable;
import java.util.Date;

import com.liyun.car.param.usertype.DictEnum;

public class CmsDealerFile implements Serializable{
	
	private Integer id;
	private Integer code;
	private DictEnum fileType;
	private String fileName;
	private String fileDir;
	private Date crtTime;
	
	public CmsDealerFile() {
		super();
	}
	public CmsDealerFile(Integer code, DictEnum fileType,
			String fileName, String fileDir, Date crtTime) {
		super();
		this.code = code;
		this.fileType = fileType;
		this.fileName = fileName;
		this.fileDir = fileDir;
		this.crtTime = crtTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public DictEnum getFileType() {
		return fileType;
	}
	public void setFileType(DictEnum fileType) {
		this.fileType = fileType;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
