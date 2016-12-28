package com.liyun.car.fee.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 服务费放款文件下载历史
 * @author zhoufei
 *
 */
public class CmsDealerSerfeeMonFile implements Serializable{

	private Integer id;
	private String feeMon;
	private String fileName;
	private String fileDir;
	private Date crtTime;
	private String userName;
	private String trueName;
	
	public CmsDealerSerfeeMonFile(String feeMon, String fileName,
			String fileDir, Date crtTime, String userName, String trueName) {
		super();
		this.feeMon = feeMon;
		this.fileName = fileName;
		this.fileDir = fileDir;
		this.crtTime = crtTime;
		this.userName = userName;
		this.trueName = trueName;
	}
	public CmsDealerSerfeeMonFile() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFeeMon() {
		return feeMon;
	}
	public void setFeeMon(String feeMon) {
		this.feeMon = feeMon;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	
}
