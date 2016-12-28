package com.liyun.car.system.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.system.enums.SexEnum;
import com.liyun.car.system.enums.UserStatusEnum;
import com.liyun.car.system.enums.UserTypeEnum;


/**
 * SyUser entity. @author MyEclipse Persistence Tools
 * 用户ID生成策略(10位正整数)：
 * 		用户暂时区分为  ： 美利车金融用户 |经销商用户
 * 			美利车金融用户：部门ID(8位)+序列号
 * 			经销商用户区分为： 经销商单位用户|经销商门店用户
 * 				删除 经销商单位用户：单位ID(5位)+9+序列号(4位)
 * 				经销商门店用户：任意一个经销商门店编号(8位)+序列号
 */

public class SyUser implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String userName;
	private String trueName;
	private String cardType;
	private String cardId;
	private String password;
	private String birthday;
	private SexEnum sex;
	private String province;
	private String city;
	private String email;
	private String phone;
	private String address;
	private String postalCode;
	private String head;
	private Date createTime;
	private Date lastTime;
	private Integer loginTimes;
	private UserStatusEnum userStatus;
	private UserTypeEnum userType;
	
	private SyUserDeparment userDeparment;
	//验证码
	private String randcode;
	
	@JsonIgnore
	private List<SyUserDealer> syUserDealers = new ArrayList<SyUserDealer>(0);
	@JsonIgnore
	private List<SyPermSet> permSets;
	
	private String dealerCode;
	
	// Constructors

	/** default constructor */
	public SyUser() {
	}
	
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@JsonSerialize(using = EnumSerializer.class) 
	public SexEnum getSex() {
		return this.sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getHead() {
		return this.head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonSerialize(using = EnumSerializer.class) 
	public UserStatusEnum getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(UserStatusEnum userStatus) {
		this.userStatus = userStatus;
	}

	@JsonSerialize(using = EnumSerializer.class) 
	public UserTypeEnum getUserType() {
		return this.userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}
	
	/**
	 * @return the loginTimes
	 */
	public Integer getLoginTimes() {
		return loginTimes;
	}

	/**
	 * @param loginTimes the loginTimes to set
	 */
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	/**
	 * @return the lastTime
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime the lastTime to set
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getRandcode() {
		return randcode;
	}

	public void setRandcode(String randcode) {
		this.randcode = randcode;
	}

	public SyUserDeparment getUserDeparment() {
		return userDeparment;
	}

	public void setUserDeparment(SyUserDeparment userDeparment) {
		this.userDeparment = userDeparment;
	}

	public List<SyPermSet> getPermSets() {
		return permSets;
	}

	public void setPermSets(List<SyPermSet> permSets) {
		this.permSets = permSets;
	}

	public List<SyUserDealer> getSyUserDealers() {
		return syUserDealers;
	}

	public void setSyUserDealers(List<SyUserDealer> syUserDealers) {
		this.syUserDealers = syUserDealers;
	}

	public String getDealerCode() {
		if(this.syUserDealers!=null && !this.syUserDealers.isEmpty()){
			String code = "";
			 for(SyUserDealer dealer : this.syUserDealers){
				 code += dealer.getDealer().getDealerCode() + ",";
			 }
			 return code;
		}
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}


}