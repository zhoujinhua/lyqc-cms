package com.liyun.car.system.entity;

import com.liyun.car.param.entity.SyDepartment;

/**
 * SyUserDeparment entity. @author MyEclipse Persistence Tools
 */

public class SyUserDeparment implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String userPostion;
	private Integer departmentId;
	
	private SyDepartment department;

	private String userName;
	private String trueName;
	

	//封装 和数据库没关系  审批流程 查询信贷经理使用
	private String departmentName;
	
	private String userPostionPage;

	// Constructors

	/** default constructor */
	public SyUserDeparment() {
	}

	public SyUserDeparment(String userPostion, String departmentName) {
		this.userPostion = userPostion;
		this.departmentName = departmentName;
	}
	
	public SyUserDeparment(Integer userId) {
		super();
		this.userId = userId;
	}

	public String getUserPostion() {
		return this.userPostion;
	}

	public void setUserPostion(String userPostion) {
		this.userPostion = userPostion;
		this.setUserPostionPage("");
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getUserPostionPage() {
		return userPostionPage;
	}

	public void setUserPostionPage(String userPostionPage) {
		if(this.userPostion == null || "".equals(this.userPostion)) {
			return;
		}
		String postion = this.userPostion;
		if("FB".equals(postion)){
			this.userPostionPage = "贷前审核岗" ;
		}else if("FA".equals(postion)){
			this.userPostionPage = "贷后审核岗";
		}else if("FO".equals(postion)){
			this.userPostionPage = "信贷员";
		}else if("FM".equals(postion)){
			this.userPostionPage = "信贷经理";
		}else if("LA".equals(postion)){
			this.userPostionPage = "放款审批岗";
		}else if("LM".equals(postion)){
			this.userPostionPage = "放款管理岗";
		}else if("PB".equals(postion)){
			this.userPostionPage = "PBOC岗";
		}
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SyDepartment getDepartment() {
		return department;
	}

	public void setDepartment(SyDepartment department) {
		this.department = department;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	

}