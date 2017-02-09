package com.liyun.car.dealer.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyun.car.dealer.entity.vo.VDealerCompany;
import com.liyun.car.dealer.enums.AccountTypeEnum;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.dealer.enums.DealerTypeEnum;
import com.liyun.car.dealer.enums.SaleAreaEnum;
import com.liyun.car.system.entity.SyUser;



public class CmsDealer implements java.io.Serializable,Cloneable {

	private Integer dealerCode;
	private String dealerName;
	private DealerTypeEnum dealerType;
	private Integer companyCode;
	private VDealerCompany company;
	private String recAccountName;
	private String recAccountCard;
	private String recAccountBank;
	private String recAccountBank2; //开户银行
	private String recAccountNo;
	
	private String province;
	private String city;
	private SaleAreaEnum saleArea;
	private String address;
	private String rtamil;
	@JsonIgnore
	private SyUser am;
	private String sv;
	private String gmName;
	private String gmMobile;
	private String gmPhone;
	private String gmEmail;
	private String smName;
	private String smMobile;
	private String smPhone;
	private String smPsw;
	private String smEamil;
	private String fmName;
	private String fmMobile;
	private String fmPhone;
	private String fmEamil;
	private String fmPsw;
	private String mmName;
	private String mmMobile;
	private String mmPhone;
	private String mmEamil;
	private String saleName;
	private String saleMobile;
	private String salePhone;
	private String saleEmail;
	private String trainName;
	private String trainMobile;
	private String trainPhone;
	private String trainEmail;
	private Date clcType;
	private String serverInvoiceName;
	private Character serverInvoiceType;
	private String dealerEmail;
	private String contactPerson;
	private String accountName;
	private String remarks1;
	private String interestRate;
	private String provinceRate;
	private DealerStatusEnum status;
	private Date onlineTime;
	private Date updateTime;
	private String remarks;
	//150113
	private String bankProvince;
	private String bankCity;
	private Integer tempDealerCode;
	private String createUser;
	
	@JsonIgnore
	private List<CmsDealerFile> dealerFiles;
	
	private AccountTypeEnum accountType;
	public CmsDealer() {
		super();
	}
	
	public CmsDealer(Integer dealerCode, DealerStatusEnum status) {
		super();
		this.dealerCode = dealerCode;
		this.status = status;
	}



	public Integer getDealerCode() {
		return this.dealerCode;
	}


	public void setDealerCode(Integer dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return this.dealerName;
	}

  	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public DealerTypeEnum getDealerType() {
		return this.dealerType;
	}

	public void setDealerType(DealerTypeEnum dealerType) {
		this.dealerType = dealerType;
	}

	public VDealerCompany getCompany() {
		return this.company;
	}
	
	public void setCompany(VDealerCompany company) {
		this.company = company;
	}
	
	public String getRecAccountName() {
		return this.recAccountName;
	}

	public void setRecAccountName(String recAccountName) {
		this.recAccountName = recAccountName;
	}

	public String getRecAccountCard() {
		return this.recAccountCard;
	}

	public void setRecAccountCard(String recAccountCard) {
		this.recAccountCard = recAccountCard;
	}

	public String getRecAccountBank() {
		return this.recAccountBank;
	}

	public void setRecAccountBank(String recAccountBank) {
		this.recAccountBank = recAccountBank;
	}
	
	public String getRecAccountBank2() {
		return recAccountBank2;
	}

	public void setRecAccountBank2(String recAccountBank2) {
		this.recAccountBank2 = recAccountBank2;
	}

	public String getRecAccountNo() {
		return this.recAccountNo;
	}

	public void setRecAccountNo(String recAccountNo) {
		this.recAccountNo = recAccountNo;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRtamil() {
		return this.rtamil;
	}

	public void setRtamil(String rtamil) {
		this.rtamil = rtamil;
	}

	public SyUser getAm() {
		return this.am;
	}

	public void setAm(SyUser am) {
		this.am = am;
	}

	public String getSv() {
		return this.sv;
	}

	public void setSv(String sv) {
		this.sv = sv;
	}

	public String getGmName() {
		return this.gmName;
	}

	public void setGmName(String gmName) {
		this.gmName = gmName;
	}

	public String getGmMobile() {
		return this.gmMobile;
	}

	public void setGmMobile(String gmMobile) {
		this.gmMobile = gmMobile;
	}

	public String getGmPhone() {
		return this.gmPhone;
	}

	public void setGmPhone(String gmPhone) {
		this.gmPhone = gmPhone;
	}

	public String getGmEmail() {
		return this.gmEmail;
	}

	public void setGmEmail(String gmEmail) {
		this.gmEmail = gmEmail;
	}

	public String getSmName() {
		return this.smName;
	}

	public void setSmName(String smName) {
		this.smName = smName;
	}

	public String getSmMobile() {
		return this.smMobile;
	}

	public void setSmMobile(String smMobile) {
		this.smMobile = smMobile;
	}

	public String getSmPhone() {
		return this.smPhone;
	}

	public void setSmPhone(String smPhone) {
		this.smPhone = smPhone;
	}

	public String getSmPsw() {
		return this.smPsw;
	}

	public void setSmPsw(String smPsw) {
		this.smPsw = smPsw;
	}

	public String getSmEamil() {
		return this.smEamil;
	}

	public void setSmEamil(String smEamil) {
		this.smEamil = smEamil;
	}

	public String getFmName() {
		return this.fmName;
	}

	public void setFmName(String fmName) {
		this.fmName = fmName;
	}

	public String getFmMobile() {
		return this.fmMobile;
	}

	public void setFmMobile(String fmMobile) {
		this.fmMobile = fmMobile;
	}

	public String getFmPhone() {
		return this.fmPhone;
	}

	public void setFmPhone(String fmPhone) {
		this.fmPhone = fmPhone;
	}

	public String getFmEamil() {
		return this.fmEamil;
	}

	public void setFmEamil(String fmEamil) {
		this.fmEamil = fmEamil;
	}

	public String getFmPsw() {
		return this.fmPsw;
	}

	public void setFmPsw(String fmPsw) {
		this.fmPsw = fmPsw;
	}

	public String getMmName() {
		return this.mmName;
	}

	public void setMmName(String mmName) {
		this.mmName = mmName;
	}

	public String getMmMobile() {
		return this.mmMobile;
	}

	public void setMmMobile(String mmMobile) {
		this.mmMobile = mmMobile;
	}

	public String getMmPhone() {
		return this.mmPhone;
	}

	public void setMmPhone(String mmPhone) {
		this.mmPhone = mmPhone;
	}

	public String getMmEamil() {
		return this.mmEamil;
	}

	public void setMmEamil(String mmEamil) {
		this.mmEamil = mmEamil;
	}

	public String getSaleName() {
		return this.saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getSaleMobile() {
		return this.saleMobile;
	}

	public void setSaleMobile(String saleMobile) {
		this.saleMobile = saleMobile;
	}

	public String getSalePhone() {
		return this.salePhone;
	}

	public void setSalePhone(String salePhone) {
		this.salePhone = salePhone;
	}

	public String getSaleEmail() {
		return this.saleEmail;
	}

	public void setSaleEmail(String saleEmail) {
		this.saleEmail = saleEmail;
	}

	public String getTrainName() {
		return this.trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTrainMobile() {
		return this.trainMobile;
	}

	public void setTrainMobile(String trainMobile) {
		this.trainMobile = trainMobile;
	}

	public String getTrainPhone() {
		return this.trainPhone;
	}

	public void setTrainPhone(String trainPhone) {
		this.trainPhone = trainPhone;
	}

	public String getTrainEmail() {
		return this.trainEmail;
	}

	public void setTrainEmail(String trainEmail) {
		this.trainEmail = trainEmail;
	}

	public Date getClcType() {
		return this.clcType;
	}

	public void setClcType(Date clcType) {
		this.clcType = clcType;
	}

	public String getServerInvoiceName() {
		return this.serverInvoiceName;
	}

	public void setServerInvoiceName(String serverInvoiceName) {
		this.serverInvoiceName = serverInvoiceName;
	}

	public Character getServerInvoiceType() {
		return this.serverInvoiceType;
	}

	public void setServerInvoiceType(Character serverInvoiceType) {
		this.serverInvoiceType = serverInvoiceType;
	}

	public String getDealerEmail() {
		return this.dealerEmail;
	}

	public void setDealerEmail(String dealerEmail) {
		this.dealerEmail = dealerEmail;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getRemarks1() {
		return this.remarks1;
	}

	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}

	public String getInterestRate() {
		return this.interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getProvinceRate() {
		return this.provinceRate;
	}

	public void setProvinceRate(String provinceRate) {
		this.provinceRate = provinceRate;
	}

	public DealerStatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(DealerStatusEnum status) {
		this.status = status;
	}

	public Date getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public SaleAreaEnum getSaleArea() {
		return saleArea;
	}

	public void setSaleArea(SaleAreaEnum saleArea) {
		this.saleArea = saleArea;
	}

	public Integer getTempDealerCode() {
		return tempDealerCode;
	}

	public void setTempDealerCode(Integer tempDealerCode) {
		this.tempDealerCode = tempDealerCode;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public AccountTypeEnum getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountTypeEnum accountType) {
		this.accountType = accountType;
	}

	public List<CmsDealerFile> getDealerFiles() {
		return dealerFiles;
	}

	public void setDealerFiles(List<CmsDealerFile> dealerFiles) {
		this.dealerFiles = dealerFiles;
	}

	public Integer getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}