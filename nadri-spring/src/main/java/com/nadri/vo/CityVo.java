package com.nadri.vo;

import java.io.InputStream;

public class CityVo {
	private int cityId;
	private String cityName;
	private Double cityLatitude;
	private Double cityLongitude;
	private String cityImageURL;
	private int cityRegionId;
	private InputStream cityImageData;

	public CityVo() {
		super();
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Double getCityLatitude() {
		return cityLatitude;
	}

	public void setCityLatitude(Double cityLatitude) {
		this.cityLatitude = cityLatitude;
	}

	public Double getCityLongitude() {
		return cityLongitude;
	}

	public void setCityLongitude(Double cityLongitude) {
		this.cityLongitude = cityLongitude;
	}

	public String getCityImageURL() {
		return cityImageURL;
	}

	public void setCityImageURL(String cityImageURL) {
		this.cityImageURL = cityImageURL;
	}

	public int getCityRegionId() {
		return cityRegionId;
	}

	public void setCityRegionId(int cityRegionId) {
		this.cityRegionId = cityRegionId;
	}

	public InputStream getCityImageData() {
		return cityImageData;
	}

	public void setCityImageData(InputStream cityImageData) {
		this.cityImageData = cityImageData;
	}

	@Override
	public String toString() {
		return "CityVo [cityId=" + cityId + ", cityName=" + cityName + ", cityLatitude=" + cityLatitude
				+ ", cityLongitude=" + cityLongitude + ", cityImageURL=" + cityImageURL + ", cityRegionId="
				+ cityRegionId + ", cityImageData=" + cityImageData + "]";
	}

}
