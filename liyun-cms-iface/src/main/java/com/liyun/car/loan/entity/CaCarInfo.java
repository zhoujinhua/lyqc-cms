package com.liyun.car.loan.entity;

import java.util.Date;

/**
 * CaCarInfo entity. @author MyEclipse Persistence Tools
 * 车辆信息
 */

public class CaCarInfo implements java.io.Serializable {

	// Fields
	private Integer carId;
	private String carBrand;
	
	//车系
	private String carSeries;  
	private String carStyles;
	//150114
	private String carYear;
	private String carDisTon;
	private String carGearBox;
	private Double carNewPrice;
	
	//车辆识别代码
	private String carIdentify;
	private String engineNo;
	private String carColor;
	private String isOld;
	private Integer carMiles;
	private Date carFirstBook;
	private String carSaleName;
	private String carSaleIdno;
	private String remakes;
	//150208 二手车评估信息
	private String acarCondtion;
	private Double acarPrice;
	private Double floatingPro;
	private Double acarPriceChange;
	private String plateNumber;//车牌号
	private Date mortgageDate;//抵押日期
	private Double openFare;//开票价
	

	// Constructors

	public CaCarInfo() {
	}

	public CaCarInfo(Integer carId) {
		this.carId = carId;
	}
	
	public Integer getCarId() {
		return this.carId;
	}
	
	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public String getCarBrand() {
		return this.carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarSeries() {
		return this.carSeries;
	}

	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}

	public String getCarStyles() {
		return this.carStyles;
	}

	public void setCarStyles(String carStyles) {
		this.carStyles = carStyles;
	}
	
	public String getCarYear() {
		return carYear;
	}

	public void setCarYear(String carYear) {
		this.carYear = carYear;
	}

	public String getCarDisTon() {
		return carDisTon;
	}

	public void setCarDisTon(String carDisTon) {
		this.carDisTon = carDisTon;
	}

	public String getCarGearBox() {
		return carGearBox;
	}

	public void setCarGearBox(String carGearBox) {
		this.carGearBox = carGearBox;
	}

	public Double getCarNewPrice() {
		return this.carNewPrice;
	}

	public void setCarNewPrice(Double carNewPrice) {
		this.carNewPrice = carNewPrice;
	}

	public String getCarIdentify() {
		return this.carIdentify;
	}

	public void setCarIdentify(String carIdentify) {
		this.carIdentify = carIdentify;
	}

	public String getEngineNo() {
		return this.engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getCarColor() {
		return this.carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getIsOld() {
		return this.isOld;
	}

	public void setIsOld(String isOld) {
		this.isOld = isOld;
	}

	public Integer getCarMiles() {
		return this.carMiles;
	}

	public void setCarMiles(Integer carMiles) {
		this.carMiles = carMiles;
	}

	public Date getCarFirstBook() {
		return this.carFirstBook;
	}

	public void setCarFirstBook(Date carFirstBook) {
		this.carFirstBook = carFirstBook;
	}

	public String getCarSaleName() {
		return this.carSaleName;
	}

	public void setCarSaleName(String carSaleName) {
		this.carSaleName = carSaleName;
	}

	public String getCarSaleIdno() {
		return this.carSaleIdno;
	}

	public void setCarSaleIdno(String carSaleIdno) {
		this.carSaleIdno = carSaleIdno;
	}

	public String getRemakes() {
		return this.remakes;
	}

	public void setRemakes(String remakes) {
		this.remakes = remakes;
	}

	public String getAcarCondtion() {
		return acarCondtion;
	}

	public void setAcarCondtion(String acarCondtion) {
		this.acarCondtion = acarCondtion;
	}

	public Double getAcarPrice() {
		return acarPrice;
	}

	public void setAcarPrice(Double acarPrice) {
		this.acarPrice = acarPrice;
	}

	public Double getFloatingPro() {
		return floatingPro;
	}

	public void setFloatingPro(Double floatingPro) {
		this.floatingPro = floatingPro;
	}

	public Double getAcarPriceChange() {
		return acarPriceChange;
	}

	public void setAcarPriceChange(Double acarPriceChange) {
		this.acarPriceChange = acarPriceChange;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Date getMortgageDate() {
		return mortgageDate;
	}

	public void setMortgageDate(Date mortgageDate) {
		this.mortgageDate = mortgageDate;
	}

	public Double getOpenFare() {
		return openFare;
	}

	public void setOpenFare(Double openFare) {
		this.openFare = openFare;
	}

	@Override
	public String toString() {
		return "CaCarInfo [carId=" + carId + ", carBrand=" + carBrand
				+ ", carSeries=" + carSeries + ", carStyles=" + carStyles
				+ ", carYear=" + carYear + ", carDisTon=" + carDisTon
				+ ", carGearBox=" + carGearBox + ", carNewPrice=" + carNewPrice
				+ ", carIdentify=" + carIdentify + ", engineNo=" + engineNo
				+ ", carColor=" + carColor + ", isOld=" + isOld + ", carMiles="
				+ carMiles + ", carFirstBook=" + carFirstBook
				+ ", carSaleName=" + carSaleName + ", carSaleIdno="
				+ carSaleIdno + ", remakes=" + remakes + ", acarCondtion="
				+ acarCondtion + ", acarPrice=" + acarPrice + ", floatingPro="
				+ floatingPro + ", acarPriceChange=" + acarPriceChange
				+ ", plateNumber=" + plateNumber + ", mortgageDate="
				+ mortgageDate + ", openFare=" + openFare + "]";
	}

}