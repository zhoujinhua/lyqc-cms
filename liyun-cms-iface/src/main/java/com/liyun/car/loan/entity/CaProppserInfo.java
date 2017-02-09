package com.liyun.car.loan.entity;

import java.util.Date;

/**
 * CaProppserInfo entity. @author MyEclipse Persistence Tools
 * 申请人信息
 */

public class CaProppserInfo implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer proppserId;
	private String idType;
	private String idno;
	private String proppserName;
	private String psw;
	private String birth;
	private String sex;
	private String marriage;
	private String education;
	private String province;
	private String district;
	private String city;
	private String address;
	private String nowProvince;
	private String nowCity;
	private String nowDistrict;
	private String nowAddress;
	private String nowPostalcode;
	private String nowTel;
	private Short nowLivingTime;
	private String houseOwner ;
	private Date firstWorkTime;
	private String nowIndustry;
	private String nowPosition;
	private String incomeSource;
	private Double incomeMonth;
	private Double incomeOth;
	private String nowCompany;
	private String nowUnitKind;
	private String nowUnitProvince;
	private String nowUnitCity;
	private String nowUnitAddress;
	private Short nowUnitScale;
	private String nowUnitTel;
	private String nowUnitPostalcode;
	private String isDriverLicense;
	private String firstDriverNo;
	private String relationDFl;
	private String driverName;
	private String driverNo;
	private String isAssure;
	private String mobile;
	private String email;
	private String relationEContact;
	private String EContactMobile;
	private String EContact;
	private String status;
	private String remakes;
	
	private String creditRecords;
	private String mobiles;
	private String relationEContacts;
	private String EContactsMobile;
	private String EContacts;
	
	private String isJxlReport;
	
	public CaProppserInfo() {
	}

	public CaProppserInfo(Integer proppserId) {
		this.proppserId = proppserId;
	}

	public Integer getProppserId() {
		return this.proppserId;
	}

	public void setProppserId(Integer proppserId) {
		this.proppserId = proppserId;
	}

	public String getIdType() {
		return this.idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getProppserName() {
		return this.proppserName;
	}

	public void setProppserName(String proppserName) {
		this.proppserName = proppserName;
	}

	public String getPsw() {
		return this.psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getBirth() {
		return this.birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNowProvince() {
		return this.nowProvince;
	}

	public void setNowProvince(String nowProvince) {
		this.nowProvince = nowProvince;
	}

	public String getNowCity() {
		return this.nowCity;
	}

	public void setNowCity(String nowCity) {
		this.nowCity = nowCity;
	}

	public String getNowDistrict() {
		return this.nowDistrict;
	}

	public void setNowDistrict(String nowDistrict) {
		this.nowDistrict = nowDistrict;
	}

	public String getNowAddress() {
		return this.nowAddress;
	}

	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress;
	}

	public String getNowPostalcode() {
		return this.nowPostalcode;
	}

	public void setNowPostalcode(String nowPostalcode) {
		this.nowPostalcode = nowPostalcode;
	}

	public String getNowTel() {
		return this.nowTel;
	}

	public void setNowTel(String nowTel) {
		this.nowTel = nowTel;
	}

	public Short getNowLivingTime() {
		return this.nowLivingTime;
	}

	public void setNowLivingTime(Short nowLivingTime) {
		this.nowLivingTime = nowLivingTime;
	}
	
	public String getHouseOwner() {
		return houseOwner;
	}

	public void setHouseOwner(String houseOwner) {
		this.houseOwner = houseOwner;
	}

	public Date getFirstWorkTime() {
		return this.firstWorkTime;
	}

	public void setFirstWorkTime(Date firstWorkTime) {
		this.firstWorkTime = firstWorkTime;
	}

	public String getNowIndustry() {
		return this.nowIndustry;
	}

	public void setNowIndustry(String nowIndustry) {
		this.nowIndustry = nowIndustry;
	}

	public String getNowPosition() {
		return this.nowPosition;
	}

	public void setNowPosition(String nowPosition) {
		this.nowPosition = nowPosition;
	}

	public String getIncomeSource() {
		return this.incomeSource;
	}

	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}

	public Double getIncomeMonth() {
		return this.incomeMonth;
	}

	public void setIncomeMonth(Double incomeMonth) {
		this.incomeMonth = incomeMonth;
	}

	public Double getIncomeOth() {
		return this.incomeOth;
	}

	public void setIncomeOth(Double incomeOth) {
		this.incomeOth = incomeOth;
	}

	public String getNowCompany() {
		return this.nowCompany;
	}

	public void setNowCompany(String nowCompany) {
		this.nowCompany = nowCompany;
	}

	public String getNowUnitKind() {
		return this.nowUnitKind;
	}

	public void setNowUnitKind(String nowUnitKind) {
		this.nowUnitKind = nowUnitKind;
	}

	public String getNowUnitProvince() {
		return this.nowUnitProvince;
	}

	public void setNowUnitProvince(String nowUnitProvince) {
		this.nowUnitProvince = nowUnitProvince;
	}

	public String getNowUnitCity() {
		return this.nowUnitCity;
	}

	public void setNowUnitCity(String nowUnitCity) {
		this.nowUnitCity = nowUnitCity;
	}

	public String getNowUnitAddress() {
		return this.nowUnitAddress;
	}

	public void setNowUnitAddress(String nowUnitAddress) {
		this.nowUnitAddress = nowUnitAddress;
	}

	public Short getNowUnitScale() {
		return this.nowUnitScale;
	}

	public void setNowUnitScale(Short nowUnitScale) {
		this.nowUnitScale = nowUnitScale;
	}

	public String getNowUnitTel() {
		return this.nowUnitTel;
	}

	public void setNowUnitTel(String nowUnitTel) {
		this.nowUnitTel = nowUnitTel;
	}

	public String getNowUnitPostalcode() {
		return this.nowUnitPostalcode;
	}

	public void setNowUnitPostalcode(String nowUnitPostalcode) {
		this.nowUnitPostalcode = nowUnitPostalcode;
	}

	public String getIsDriverLicense() {
		return this.isDriverLicense;
	}

	public void setIsDriverLicense(String isDriverLicense) {
		this.isDriverLicense = isDriverLicense;
	}

	public String getFirstDriverNo() {
		return this.firstDriverNo;
	}

	public void setFirstDriverNo(String firstDriverNo) {
		this.firstDriverNo = firstDriverNo;
	}

	public String getRelationDFl() {
		return this.relationDFl;
	}

	public void setRelationDFl(String relationDFl) {
		this.relationDFl = relationDFl;
	}

	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverNo() {
		return this.driverNo;
	}

	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRelationEContact() {
		return this.relationEContact;
	}

	public void setRelationEContact(String relationEContact) {
		this.relationEContact = relationEContact;
	}

	public String getEContactMobile() {
		return this.EContactMobile;
	}

	public void setEContactMobile(String EContactMobile) {
		this.EContactMobile = EContactMobile;
	}

	public String getEContact() {
		return this.EContact;
	}

	public void setEContact(String EContact) {
		this.EContact = EContact;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemakes() {
		return this.remakes;
	}

	public void setRemakes(String remakes) {
		this.remakes = remakes;
	}

	public String getIsAssure() {
		return isAssure;
	}

	public void setIsAssure(String isAssure) {
		this.isAssure = isAssure;
	}

	public String getCreditRecords() {
		return creditRecords;
	}

	public void setCreditRecords(String creditRecords) {
		this.creditRecords = creditRecords;
	}
	
	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getRelationEContacts() {
		return relationEContacts;
	}

	public void setRelationEContacts(String relationEContacts) {
		this.relationEContacts = relationEContacts;
	}

	public String getEContactsMobile() {
		return EContactsMobile;
	}

	public void setEContactsMobile(String eContactsMobile) {
		EContactsMobile = eContactsMobile;
	}

	public String getEContacts() {
		return EContacts;
	}

	public void setEContacts(String eContacts) {
		EContacts = eContacts;
	}

	public String getIsJxlReport() {
		return isJxlReport;
	}

	public void setIsJxlReport(String isJxlReport) {
		this.isJxlReport = isJxlReport;
	}

	@Override
	public String toString() {
		return "CaProppserInfo [proppserId=" + proppserId + ", idType="
				+ idType + ", idno=" + idno + ", proppserName=" + proppserName
				+ ", psw=" + psw + ", birth=" + birth + ", sex=" + sex
				+ ", marriage=" + marriage + ", education=" + education
				+ ", province=" + province + ", district=" + district
				+ ", city=" + city + ", address=" + address + ", nowProvince="
				+ nowProvince + ", nowCity=" + nowCity + ", nowDistrict="
				+ nowDistrict + ", nowAddress=" + nowAddress
				+ ", nowPostalcode=" + nowPostalcode + ", nowTel=" + nowTel
				+ ", nowLivingTime=" + nowLivingTime + ", houseOwner="
				+ houseOwner + ", firstWorkTime=" + firstWorkTime
				+ ", nowIndustry=" + nowIndustry + ", nowPosition="
				+ nowPosition + ", incomeSource=" + incomeSource
				+ ", incomeMonth=" + incomeMonth + ", incomeOth=" + incomeOth
				+ ", nowCompany=" + nowCompany + ", nowUnitKind=" + nowUnitKind
				+ ", nowUnitProvince=" + nowUnitProvince + ", nowUnitCity="
				+ nowUnitCity + ", nowUnitAddress=" + nowUnitAddress
				+ ", nowUnitScale=" + nowUnitScale + ", nowUnitTel="
				+ nowUnitTel + ", nowUnitPostalcode=" + nowUnitPostalcode
				+ ", isDriverLicense=" + isDriverLicense + ", firstDriverNo="
				+ firstDriverNo + ", relationDFl=" + relationDFl
				+ ", driverName=" + driverName + ", driverNo=" + driverNo
				+ ", isAssure=" + isAssure + ", mobile=" + mobile + ", email="
				+ email + ", relationEContact=" + relationEContact
				+ ", EContactMobile=" + EContactMobile + ", EContact="
				+ EContact + ", status=" + status + ", remakes=" + remakes
				+ ", creditRecords=" + creditRecords + ", mobiles=" + mobiles
				+ ", relationEContacts=" + relationEContacts
				+ ", EContactsMobile=" + EContactsMobile + ", EContacts="
				+ EContacts + ", isJxlReport=" + isJxlReport + "]";
	}
}