package com.liyun.car.workflow.entity.vo;

import java.io.Serializable;

public class CmsMonitorBean implements Serializable{

	private Object dBean;
	private CmsTask task;
	
	
	public CmsMonitorBean() {
		super();
	}
	public CmsMonitorBean(Object dBean, CmsTask task) {
		super();
		this.dBean = dBean;
		this.task = task;
	}
	public Object getdBean() {
		return dBean;
	}
	public void setdBean(Object dBean) {
		this.dBean = dBean;
	}
	public CmsTask getTask() {
		return task;
	}
	public void setTask(CmsTask task) {
		this.task = task;
	}
	
}
