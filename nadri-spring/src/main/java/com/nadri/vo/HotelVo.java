package com.nadri.vo;

import java.io.InputStream;

public class HotelVo {

	private int hotelId;
	private String hotelName;
	private String hotelContent;
	private double hotelLatitude;
	private double hotelLongitude;
	private int hotelCityId;
	private String hotelAddress;
	private int hotelAdd;
	private String hotelImageURL;
	private InputStream hotelImageData;

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelContent() {
		return hotelContent;
	}

	public void setHotelContent(String hotelContent) {
		this.hotelContent = hotelContent;
	}

	public double getHotelLatitude() {
		return hotelLatitude;
	}

	public void setHotelLatitude(double hotelLatitude) {
		this.hotelLatitude = hotelLatitude;
	}

	public double getHotelLongitude() {
		return hotelLongitude;
	}

	public void setHotelLongitude(double hotelLongitude) {
		this.hotelLongitude = hotelLongitude;
	}

	public String getHotelAddress() {
		return hotelAddress;
	}

	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}

	public int getHotelAdd() {
		return hotelAdd;
	}

	public void setHotelAdd(int hotelAdd) {
		this.hotelAdd = hotelAdd;
	}

	public String getHotelImageURL() {
		return hotelImageURL;
	}

	public void setHotelImageURL(String hotelImageURL) {
		this.hotelImageURL = hotelImageURL;
	}

	public InputStream getHotelImageData() {
		return hotelImageData;
	}

	public void setHotelImageData(InputStream hotelImageData) {
		this.hotelImageData = hotelImageData;
	}

	public int getHotelCityId() {
		return hotelCityId;
	}

	public void setHotelCityId(int hotelCityId) {
		this.hotelCityId = hotelCityId;
	}

	@Override
	public String toString() {
		return "HotelVo [hotelId=" + hotelId + ", hotelName=" + hotelName + ", hotelContent=" + hotelContent
				+ ", hotelLatitude=" + hotelLatitude + ", hotelLongitude=" + hotelLongitude + ", hotelCityId="
				+ hotelCityId + ", hotelAddress=" + hotelAddress + ", hotelAdd=" + hotelAdd + ", hotelImageURL="
				+ hotelImageURL + ", hotelImageData=" + hotelImageData + "]";
	}

}