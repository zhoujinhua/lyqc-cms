package com.liyun.car.dealer.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.dealer.enums.AccountTypeEnum;
import com.liyun.car.dealer.enums.BillPayStatusEnum;
import com.liyun.car.dealer.enums.DealerTypeEnum;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.dealer.enums.SaleAreaEnum;


/**
 * SyDealerCompany entity. @author MyEclipse Persistence Tools
 */

public class SyDealerCompany implements java.io.Serializable,Cloneable {

	// Fields

	private Integer companyCode;
	private String companyName;
	private DealerTypeEnum companyType;
	private String rateArea;
	private String province;
	private String city;
	private SaleAreaEnum saleArea;
	private String address;
	private Character brandType;
	private String brandName;
	private String dealerEmail;
	private String contactPerson;
	private String accountName;
	private String tel;
	private String appNo;
	private BooleanEnum isVip;
	private Date onlineTime;
	private Date updateTime;
	private DealerStatusEnum status;
	private String remarks;

	private String accountIdno;
	private String accountBank;
	private String accountSubBank;
	private String bankProvince;
	private String bankCity;
	private String accountNo;
	private Integer tempCompanyCode;
	private AccountTypeEnum accountType;
	private String createUser;
	private BooleanEnum isDeposit;
	private Double depositAmt;
	
	//账单发放状态
	private BillPayStatusEnum payStt;
	@JsonIgnore
	private List<SyAmDealerCompany> amDealerCompanys;
	@JsonIgnore
	private String amUserIds;
	@JsonIgnore
	private String amUserNames;
	@JsonIgnore
	private SyAmDealerCompany amDealerCompany;
	@JsonIgnore
	private List<CmsDealerFile> dealerFiles;
	@JsonIgnore
	private List<SyDealer> dealers;

	// Constructors

	public SyDealerCompany() {
	}

	public SyDealerCompany(Integer companyCode, DealerStatusEnum status) {
		super();
		this.companyCode = companyCode;
		this.status = status;
	}

	public Integer getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public DealerTypeEnum getCompanyType() {
		return this.companyType;
	}
	
	public void setCompanyType(DealerTypeEnum companyType) {
		this.companyType = companyType;
	}

	public String getRateArea() {
		return this.rateArea;
	}

	public void setRateArea(String rateArea) {
		this.rateArea = rateArea;
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

	public SaleAreaEnum getSaleArea() {
		return this.saleArea;
	}

	public void setSaleArea(SaleAreaEnum saleArea) {
		this.saleArea = saleArea;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Character getBrandType() {
		return this.brandType;
	}

	public void setBrandType(Character brandType) {
		this.brandType = brandType;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAppNo() {
		return this.appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public BooleanEnum getIsVip() {
		return this.isVip;
	}

	public void setIsVip(BooleanEnum isVip) {
		this.isVip = isVip;
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

	public DealerStatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(DealerStatusEnum status) {
		this.status = status;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getTempCompanyCode() {
		return tempCompanyCode;
	}

	public void setTempCompanyCode(Integer tempCompanyCode) {
		this.tempCompanyCode = tempCompanyCode;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getAccountIdno() {
		return accountIdno;
	}

	public void setAccountIdno(String accountIdno) {
		this.accountIdno = accountIdno;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountSubBank() {
		return accountSubBank;
	}

	public void setAccountSubBank(String accountSubBank) {
		this.accountSubBank = accountSubBank;
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

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public AccountTypeEnum getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountTypeEnum accountType) {
		this.accountType = accountType;
	}

	public BillPayStatusEnum getPayStt() {
		return payStt;
	}

	public void setPayStt(BillPayStatusEnum payStt) {
		this.payStt = payStt;
	}

	public List<SyAmDealerCompany> getAmDealerCompanys() {
		return amDealerCompanys;
	}

	public void setAmDealerCompanys(List<SyAmDealerCompany> amDealerCompanys) {
		this.amDealerCompanys = amDealerCompanys;
	}

	public String getAmUserIds() {
		if(this.amDealerCompanys!=null && !this.amDealerCompanys.isEmpty()){
			String part = "";
			for(SyAmDealerCompany amDealerCompany : this.amDealerCompanys){
				part += amDealerCompany.getUserId()+",";
			}
			return part;
		}
		return amUserIds;
	}

	public void setAmUserIds(String amUserIds) {
		this.amUserIds = amUserIds;
	}

	public String getAmUserNames() {
		if(this.amDealerCompanys!=null && !this.amDealerCompanys.isEmpty()){
			String part = "";
			for(SyAmDealerCompany amDealerCompany : this.amDealerCompanys){
				part += amDealerCompany.getUserName()+",";
			}
			return part;
		}
		return amUserNames;
	}

	public void setAmUserNames(String amUserNames) {
		this.amUserNames = amUserNames;
	}

	public SyAmDealerCompany getAmDealerCompany() {
		if(this.amDealerCompanys!=null && !this.amDealerCompanys.isEmpty()){
			for(SyAmDealerCompany amDealerCompany : this.amDealerCompanys){
				if(amDealerCompany.getIsPrimary() == BooleanEnum.YES){
					return amDealerCompany;
				}
			}
		}
		return amDealerCompany;
	}

	public void setAmDealerCompany(SyAmDealerCompany amDealerCompany) {
		this.amDealerCompany = amDealerCompany;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public List<CmsDealerFile> getDealerFiles() {
		return dealerFiles;
	}

	public void setDealerFiles(List<CmsDealerFile> dealerFiles) {
		this.dealerFiles = dealerFiles;
	}

	public List<SyDealer> getDealers() {
		return dealers;
	}

	public void setDealers(List<SyDealer> dealers) {
		this.dealers = dealers;
	}

	public void addSyDealer(SyDealer syDealer) {
		if(getDealers()==null){
			setDealers(new ArrayList<SyDealer>());
		}
		getDealers().add(syDealer);
	}

	public void addAmDealerCompanys(SyAmDealerCompany syAmDealerCompany) {
		if(getAmDealerCompanys()==null){
			setAmDealerCompanys(new ArrayList<SyAmDealerCompany>());
		}
		getAmDealerCompanys().add(syAmDealerCompany);
		syAmDealerCompany.setCompany(this);		
	}

	public BooleanEnum getIsDeposit() {
		return isDeposit;
	}

	public void setIsDeposit(BooleanEnum isDeposit) {
		this.isDeposit = isDeposit;
	}

	public Double getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(Double depositAmt) {
		this.depositAmt = depositAmt;
	}

}