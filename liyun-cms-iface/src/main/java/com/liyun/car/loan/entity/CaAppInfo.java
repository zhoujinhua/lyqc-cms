package com.liyun.car.loan.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.param.usertype.DictEnum;

/**
 * 车贷申请单信息
 */

public class CaAppInfo implements java.io.Serializable {
	
	// Fields
	private static final long serialVersionUID = 1L;
	private Integer appNo;
	private String appCode;
	private CaProppserInfo proppserInfo;
	private CaCarInfo carInfo;
	private Integer dealerCode;
	private String dealerName;
	private Integer companyCode;
	private String companyName;
	private BooleanEnum isOld;
	private String isLcv;
	private Double salePrice;
	private BooleanEnum isGps;
	private Double gpsFee;
	private String isGpsLoan;
	
	//车贷贷款金额
	private Double ACarloanAmount;
	private Double ALoanAmount;
	private Double AInitPayment;
	private Double AInitScale;
	
	//申请还款期限
	private Integer ALoanPeriods;
	private Double ALoanRate;
	private String loanRecord;
	private Double appScore;
	private Double RLoanAmount;
	private Double RInitPayment;
	private Double RInitScale;
	private Short RLoanPeriods;
	private Double RLoanRate;
	private String repAccountBank;
	private String repAccountNo;
	private String repAccountName;
	private Date createTime;
	
	//提交时间
	private Date appTime;
	//更新时间需要保存到秒比较
	private Timestamp updateTime;
	private Date loanerApprovalTime;
	private Date manageApprovalTime;
	private Date bloanDataTime;
	private Date bloanApprovalTime;
	private Date aloanDataTime;
	private Date aloanApprovalTime;
	private Date loanTime;
	private String aloanArrive;
	private String cancelType;
	private String cancelReason;
	private String status;
	private String remarks;
	private String isAssure;
	
	private Integer userId;
	
	//登记人
	private String userName;
	//是否延后抵押
	private String isDeferMortgage;
	//流程顺序标识号
	private Integer flowSeq;
	//产品编码
	private Integer productCode;
	//产品名称
	private String productName;
	//批复gps费用
	private Double RGpsFee;
	//150116
	private Double ASecureFee;
	private Double RSecureFee;
	//150130
	private String isPrintW;
	private BooleanEnum isHouse;
	private String loanRemarks;
	private String fyRemarks;//150818 复议备注
	private String bloanRemarks;
	private String sysaRemarks;
	private Date sysaRate;	
	private String sysaStatus;
	private String sysaResult;
	private String refuseStatus ; 
	private Double extraLoanamount ; //额外借款费用 批复贷款金额*额外借款系数
	private String paymentCh ; //放款渠道150628
	private String paymentChZn ; //放款渠道150722
	private String paymentChEn ; //放款渠道160218
	private Date dateCh; ; //放款渠道150628
	private Double discountE;//规则贴息金额 150709
	private Double discountTrueE;//实际贴息金额 150717
	private Double disARate;//贴息后利率 150709
	private Double RDiscountTrueE;//批复实际贴息金额 150727
	private String loanStatus;
	//151027
	private Double AComRate;
	private Double AComFee;
	private Double AYanbaoFee;
	private Integer AYanbaoTc;
	private String AYanbaoTcName;
	private Double RComFee;
	private Double RYanbaoFee;
	private String authCh;//鉴权渠道ID
	private String pledModelId;//抵押合同模板ID
	//
	private String isCreditReport;
	private String modelIds;
	private String chApprovalStatus; //渠道审批状态
	//
	private String postLoanStatus;//贷后资料上传状态
	private String fileNumber;
	private String loanAfterRemarks;
	private String loanAfterCondition;
	private String isCredit;//是否自带征信
	
	private Double RACarloanAmount; //批复车辆贷款金额 160513改为保存到数据库
	private Double accountFee; //账户管理费，给经销商的,原账户管理费改为平台费
	private Double RaccountFee; //批复账户管理费
	private String isPrepay;
	private Date prepayDate ;
	
	private String rateLevel;
	private String RRateLevel;
	
	
	private String isLcvZh	;
	
	public CaAppInfo() {
	}
	
	public Integer getAppNo() {
		return this.appNo;
	}

	public void setAppNo(Integer appNo) {
		this.appNo = appNo;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public CaProppserInfo getProppserInfo() {
		return proppserInfo;
	}

	public void setProppserInfo(CaProppserInfo proppserInfo) {
		this.proppserInfo = proppserInfo;
	}

	public CaCarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CaCarInfo carInfo) {
		this.carInfo = carInfo;
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

	public Double getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getGpsFee() {
		return this.gpsFee;
	}

	public void setGpsFee(Double gpsFee) {
		this.gpsFee = gpsFee;
	}

	public String getIsGpsLoan() {
		return this.isGpsLoan;
	}

	public void setIsGpsLoan(String isGpsLoan) {
		this.isGpsLoan = isGpsLoan;
	}

	public Double getACarloanAmount() {
		return this.ACarloanAmount;
	}

	public void setACarloanAmount(Double ACarloanAmount) {
		this.ACarloanAmount = ACarloanAmount;
	}

	public Double getALoanAmount() {
		return this.ALoanAmount;
	}

	public void setALoanAmount(Double ALoanAmount) {
		this.ALoanAmount = ALoanAmount;
	}

	public Double getAInitPayment() {
		return this.AInitPayment;
	}

	public void setAInitPayment(Double AInitPayment) {
		this.AInitPayment = AInitPayment;
	}

	public Double getAInitScale() {
		return this.AInitScale;
	}

	public void setAInitScale(Double AInitScale) {
		this.AInitScale = AInitScale;
	}

	public Integer getALoanPeriods() {
		return this.ALoanPeriods;
	}

	public void setALoanPeriods(Integer ALoanPeriods) {
		this.ALoanPeriods = ALoanPeriods;
	}

	public Double getALoanRate() {
		return this.ALoanRate;
	}

	public void setALoanRate(Double ALoanRate) {
		this.ALoanRate = ALoanRate;
	}

	public String getLoanRecord() {
		return this.loanRecord;
	}

	public void setLoanRecord(String loanRecord) {
		this.loanRecord = loanRecord;
	}

	public Double getAppScore() {
		return this.appScore;
	}

	public void setAppScore(Double appScore) {
		this.appScore = appScore;
	}

	public Double getRLoanAmount() {
		return this.RLoanAmount;
	}

	public void setRLoanAmount(Double RLoanAmount) {
		this.RLoanAmount = RLoanAmount;
	}

	public Double getRInitPayment() {
		return this.RInitPayment;
	}

	public void setRInitPayment(Double RInitPayment) {
		this.RInitPayment = RInitPayment;
	}

	public Double getRInitScale() {
		return this.RInitScale;
	}

	public void setRInitScale(Double RInitScale) {
		this.RInitScale = RInitScale;
	}

	public Short getRLoanPeriods() {
		return this.RLoanPeriods;
	}

	public void setRLoanPeriods(Short RLoanPeriods) {
		this.RLoanPeriods = RLoanPeriods;
	}

	public Double getRLoanRate() {
		return this.RLoanRate;
	}

	public void setRLoanRate(Double RLoanRate) {
		this.RLoanRate = RLoanRate;
	}

	public String getRepAccountBank() {
		return this.repAccountBank;
	}

	public void setRepAccountBank(String repAccountBank) {
		this.repAccountBank = repAccountBank;
	}

	public String getRepAccountNo() {
		return this.repAccountNo;
	}

	public void setRepAccountNo(String repAccountNo) {
		this.repAccountNo = repAccountNo;
	}

	public String getRepAccountName() {
		return this.repAccountName;
	}

	public void setRepAccountName(String repAccountName) {
		this.repAccountName = repAccountName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAppTime() {
		return this.appTime;
	}

	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Date getLoanerApprovalTime() {
		return this.loanerApprovalTime;
	}

	public void setLoanerApprovalTime(Date loanerApprovalTime) {
		this.loanerApprovalTime = loanerApprovalTime;
	}

	public Date getManageApprovalTime() {
		return this.manageApprovalTime;
	}

	public void setManageApprovalTime(Date manageApprovalTime) {
		this.manageApprovalTime = manageApprovalTime;
	}

	public Date getBloanDataTime() {
		return this.bloanDataTime;
	}

	public void setBloanDataTime(Date bloanDataTime) {
		this.bloanDataTime = bloanDataTime;
	}

	public Date getBloanApprovalTime() {
		return this.bloanApprovalTime;
	}

	public void setBloanApprovalTime(Date bloanApprovalTime) {
		this.bloanApprovalTime = bloanApprovalTime;
	}

	public Date getAloanDataTime() {
		return this.aloanDataTime;
	}

	public void setAloanDataTime(Date aloanDataTime) {
		this.aloanDataTime = aloanDataTime;
	}

	public Date getAloanApprovalTime() {
		return this.aloanApprovalTime;
	}

	public void setAloanApprovalTime(Date aloanApprovalTime) {
		this.aloanApprovalTime = aloanApprovalTime;
	}

	public Date getLoanTime() {
		return this.loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public String getAloanArrive() {
		return this.aloanArrive;
	}

	public void setAloanArrive(String aloanArrive) {
		this.aloanArrive = aloanArrive;
	}

	public String getCancelType() {
		return this.cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

	public String getCancelReason() {
		return this.cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsAssure() {
		return this.isAssure;
	}

	public void setIsAssure(String isAssure) {
		this.isAssure = isAssure;
	}
	
	public String getIsPrintW() {
		return isPrintW;
	}
	public void setIsPrintW(String isPrintW) {
		this.isPrintW = isPrintW;
	}
	public String getLoanRemarks() {
		return loanRemarks;
	}
	public void setLoanRemarks(String loanRemarks) {
		this.loanRemarks = loanRemarks;
	}public String getFyRemarks() {
		return fyRemarks;
	}
	public void setFyRemarks(String fyRemarks) {
		this.fyRemarks = fyRemarks;
	}
	public String getBloanRemarks() {
		return bloanRemarks;
	}
	public void setBloanRemarks(String bloanRemarks) {
		this.bloanRemarks = bloanRemarks;
	}
	public String getSysaRemarks() {
		return sysaRemarks;
	}
	public void setSysaRemarks(String sysaRemarks) {
		this.sysaRemarks = sysaRemarks;
	}
	public Date getSysaRate() {
		return sysaRate;
	}
	public void setSysaRate(Date sysaRate) {
		this.sysaRate = sysaRate;
	}
	public String getSysaStatus() {
		return sysaStatus;
	}
	public void setSysaStatus(String sysaStatus) {
		this.sysaStatus = sysaStatus;
	}
	public String getSysaResult() {
		return sysaResult;
	}
	public void setSysaResult(String sysaResult) {
		this.sysaResult = sysaResult;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsDeferMortgage() {
		return isDeferMortgage;
	}


	public void setIsDeferMortgage(String isDeferMortgage) {
		this.isDeferMortgage = isDeferMortgage;
	}


	public Integer getFlowSeq() {
		return flowSeq;
	}


	public void setFlowSeq(Integer flowSeq) {
		this.flowSeq = flowSeq;
	}

	public Integer getProductCode() {
		return productCode;
	}


	public void setProductCode(Integer productCode) {
		this.productCode = productCode;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getRGpsFee() {
		return RGpsFee;
	}

	public Double getASecureFee() {
		return ASecureFee;
	}
	
	public void setASecureFee(Double aSecureFee) {
		ASecureFee = aSecureFee;
	}
	public Double getRSecureFee() {
		return RSecureFee;
	}
	
	public void setRSecureFee(Double rSecureFee) {
		RSecureFee = rSecureFee;
	}
	
	public void setRGpsFee(Double rGpsFee) {
		RGpsFee = rGpsFee;
	}
	
	public String getRefuseStatus() {
		return refuseStatus;
	}
	public void setRefuseStatus(String refuseStatus) {
		this.refuseStatus = refuseStatus;
	}
	
	public String getPaymentCh() {
		return paymentCh;
	}
	public void setPaymentCh(String paymentCh) {
		this.paymentCh = paymentCh;
	}
	public Date getDateCh() {
		return dateCh;
	}
	public void setDateCh(Date dateCh) {
		this.dateCh = dateCh;
	}
	public String getIsPrepay() {
		return isPrepay;
	}
	public void setIsPrepay(String string) {
		this.isPrepay = string;
	}
	public Date getPrepayDate() {
		return prepayDate;
	}
	public void setPrepayDate(Date prepayDate) {
		this.prepayDate = prepayDate;
	}
	public Double getRACarloanAmount() {
		return RACarloanAmount;
	}
	public void setRACarloanAmount(Double rACarloanAmount) {
		RACarloanAmount = rACarloanAmount;
	}
	public Double getExtraLoanamount() {
		return extraLoanamount;
	}
	public void setExtraLoanamount(Double extraLoanamount) {
		this.extraLoanamount = extraLoanamount;
	}
	public Double getDiscountE() {
		return discountE;
	}
	public void setDiscountE(Double discountE) {
		this.discountE = discountE;
	}
	public Double getDisARate() {
		return disARate;
	}
	public void setDisARate(Double disARate) {
		this.disARate = disARate;
	}
	public Double getDiscountTrueE() {
		return discountTrueE;
	}
	public void setDiscountTrueE(Double discountTrueE) {
		this.discountTrueE = discountTrueE;
	}
	public String getPaymentChZn() {
		return paymentChZn;
	}
	public void setPaymentChZn(String paymentChZn) {
		this.paymentChZn = paymentChZn;
	}
	public Double getRDiscountTrueE() {
		return RDiscountTrueE;
	}
	public void setRDiscountTrueE(Double rDiscountTrueE) {
		RDiscountTrueE = rDiscountTrueE;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getIsCreditReport() {
		return isCreditReport;
	}
	public void setIsCreditReport(String isCreditReport) {
		this.isCreditReport = isCreditReport;
	}
	public String getModelIds() {
		return modelIds;
	}
	public void setModelIds(String modelIds) {
		this.modelIds = modelIds;
	}
	public Double getAComRate() {
		return AComRate;
	}
	public void setAComRate(Double aComRate) {
		AComRate = aComRate;
	}
	public Double getAComFee() {
		return AComFee;
	}
	public void setAComFee(Double aComFee) {
		AComFee = aComFee;
	}
	public Double getAYanbaoFee() {
		return AYanbaoFee;
	}
	public void setAYanbaoFee(Double aYanbaoFee) {
		AYanbaoFee = aYanbaoFee;
	}
	public Integer getAYanbaoTc() {
		return AYanbaoTc;
	}
	public void setAYanbaoTc(Integer aYanbaoTc) {
		AYanbaoTc = aYanbaoTc;
	}
	public String getAYanbaoTcName() {
		return AYanbaoTcName;
	}
	public void setAYanbaoTcName(String aYanbaoTcName) {
		AYanbaoTcName = aYanbaoTcName;
	}
	public Double getRComFee() {
		return RComFee;
	}
	public void setRComFee(Double rComFee) {
		RComFee = rComFee;
	}
	public Double getRYanbaoFee() {
		return RYanbaoFee;
	}
	public void setRYanbaoFee(Double rYanbaoFee) {
		RYanbaoFee = rYanbaoFee;
	}
	public String getChApprovalStatus() {
		return chApprovalStatus;
	}
	public void setChApprovalStatus(String chApprovalStatus) {
		this.chApprovalStatus = chApprovalStatus;
	}
	public String getAuthCh() {
		return authCh;
	}
	public void setAuthCh(String authCh) {
		this.authCh = authCh;
	}
	public String getPledModelId() {
		return pledModelId;
	}
	public void setPledModelId(String pledModelId) {
		this.pledModelId = pledModelId;
	}
	public String getPaymentChEn() {
		return paymentChEn;
	}
	public void setPaymentChEn(String paymentChEn) {
		this.paymentChEn = paymentChEn;
	}
	public String getPostLoanStatus() {
		return postLoanStatus;
	}
	public void setPostLoanStatus(String postLoanStatus) {
		this.postLoanStatus = postLoanStatus;
	}
	public String getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	public String getLoanAfterRemarks() {
		return loanAfterRemarks;
	}
	public void setLoanAfterRemarks(String loanAfterRemarks) {
		this.loanAfterRemarks = loanAfterRemarks;
	}
	public String getLoanAfterCondition() {
		return loanAfterCondition;
	}
	public void setLoanAfterCondition(String loanAfterCondition) {
		this.loanAfterCondition = loanAfterCondition;
	}
	public String getIsCredit() {
		return isCredit;
	}
	public void setIsCredit(String isCredit) {
		this.isCredit = isCredit;
	}
	public Double getAccountFee() {
	    return accountFee;
	}
	public void setAccountFee(Double accountFee) {
	    this.accountFee = accountFee;
	}
	public Double getRaccountFee() {
	    return RaccountFee;
	}
	public void setRaccountFee(Double raccountFee) {
	    RaccountFee = raccountFee;
	}

	public String getRateLevel() {
		return rateLevel;
	}

	public void setRateLevel(String rateLevel) {
		this.rateLevel = rateLevel;
	}

	public String getRRateLevel() {
		return RRateLevel;
	}

	public void setRRateLevel(String rRateLevel) {
		RRateLevel = rRateLevel;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public BooleanEnum getIsOld() {
		return isOld;
	}

	public void setIsOld(BooleanEnum isOld) {
		this.isOld = isOld;
	}
	public String getIsLcv() {
		return isLcv;
	}

	public void setIsLcv(String isLcv) {
		this.isLcv = isLcv;
	}
	
	public String getIsLcvZh() {
		return DictEnum.nameOf("fwcl", this.isLcv);
	}

	@JsonSerialize(using = EnumSerializer.class) 
	public BooleanEnum getIsGps() {
		return isGps;
	}

	public void setIsGps(BooleanEnum isGps) {
		this.isGps = isGps;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public BooleanEnum getIsHouse() {
		return isHouse;
	}

	public void setIsHouse(BooleanEnum isHouse) {
		this.isHouse = isHouse;
	}
	
}