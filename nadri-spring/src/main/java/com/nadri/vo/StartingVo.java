package com.nadri.vo;

import java.io.InputStream;

public class StartingVo {
	private int startId;
	private String startName;
	private String startContent;
	private Double startLatitude;
	private Double startLongitude;
	private int startCityId;
	private String startAddress;
	private String startImageURL;
	private InputStream startImageData;

	public String getStartImageURL() {
		return startImageURL;
	}

	public void setStartImageURL(String startImageURL) {
		this.startImageURL = startImageURL;
	}

	public InputStream getStartImageData() {
		return startImageData;
	}

	public void setStartImageData(InputStream startImageData) {
		this.startImageData = startImageData;
	}

	public int getStartId() {
		return startId;
	}

	public void setStartId(int startId) {
		this.startId = startId;
	}

	public String getStartName() {
		return startName;
	}

	public void setStartName(String startName) {
		this.startName = startName;
	}

	public String getStartContent() {
		return startContent;
	}

	public void setStartContent(String startContent) {
		this.startContent = startContent;
	}

	public Double getStartLatitude() {
		return startLatitude;
	}

	public void setStartLatitude(Double startLatitude) {
		this.startLatitude = startLatitude;
	}

	public Double getStartLongitude() {
		return startLongitude;
	}

	public void setStartLongitude(Double startLongitude) {
		this.startLongitude = startLongitude;
	}

	public int getStartCityId() {
		return startCityId;
	}

	public void setStartCityId(int startCityId) {
		this.startCityId = startCityId;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	@Override
	public String toString() {
		return "StartingVo [startId=" + startId + ", startName=" + startName + ", startContent=" + startContent
				+ ", startLatitude=" + startLatitude + ", startLongitude=" + startLongitude + ", startCityId="
				+ startCityId + ", startAddress=" + startAddress + ", startImageURL=" + startImageURL
				+ ", startImageData=" + startImageData + "]";
	}

}
