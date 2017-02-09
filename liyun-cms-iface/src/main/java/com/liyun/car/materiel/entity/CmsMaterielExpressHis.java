package com.liyun.car.materiel.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流查询
 * @author zhoufei
 *
 */
public class CmsMaterielExpressHis implements Serializable{

	private Integer id;
	private String no;
	private String recContent;
	private Integer userName;
	private String trueName;
	private Date crtTime;
	
	
	public CmsMaterielExpressHis(String no, String recContent,
			Integer userName, String trueName, Date crtTime) {
		super();
		this.no = no;
		this.recContent = recContent;
		this.userName = userName;
		this.trueName = trueName;
		this.crtTime = crtTime;
	}
	public CmsMaterielExpressHis() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getRecContent() {
		return recContent;
	}
	public void setRecContent(String recContent) {
		this.recContent = recContent;
	}
	public Integer getUserName() {
		return userName;
	}
	public void setUserName(Integer userName) {
		this.userName = userName;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	
	
}
