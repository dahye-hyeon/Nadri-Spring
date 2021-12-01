package com.nadri.vo;

import java.io.InputStream;

public class PlaceVo {
	private int placeId;
	private String placeName;
	private String placeContent;
	private double placeLatitude;
	private double placeLongitude;
	private int placeCityId;
	private String placeAddress;
	private int placeAdd;
	private String placeImageURL;
	private InputStream placeImageData;

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceContent() {
		return placeContent;
	}

	public void setPlaceContent(String placeContent) {
		this.placeContent = placeContent;
	}

	public double getPlaceLatitude() {
		return placeLatitude;
	}

	public double getPlaceLongitude() {
		return placeLongitude;
	}

	public String getPlaceAddress() {
		return placeAddress;
	}

	public void setPlaceAddress(String placeAddress) {
		this.placeAddress = placeAddress;
	}

	public int getPlaceAdd() {
		return placeAdd;
	}

	public void setPlaceAdd(int placeAdd) {
		this.placeAdd = placeAdd;
	}

	public String getPlaceImageURL() {
		return placeImageURL;
	}

	public void setPlaceImageURL(String placeImageURL) {
		this.placeImageURL = placeImageURL;
	}

	public InputStream getPlaceImageData() {
		return placeImageData;
	}

	public void setPlaceImageData(InputStream placeImageData) {
		this.placeImageData = placeImageData;
	}

	public void setPlaceLatitude(double placeLatitude) {
		this.placeLatitude = placeLatitude;
	}

	public void setPlaceLongitude(double placeLongitude) {
		this.placeLongitude = placeLongitude;
	}

	public int getPlaceCityId() {
		return placeCityId;
	}

	public void setPlaceCityId(int placeCityId) {
		this.placeCityId = placeCityId;
	}

	@Override
	public String toString() {
		return "PlaceVo [placeId=" + placeId + ", placeName=" + placeName + ", placeContent=" + placeContent
				+ ", placeLatitude=" + placeLatitude + ", placeLongitude=" + placeLongitude + ", placeCityId="
				+ placeCityId + ", placeAddress=" + placeAddress + ", placeAdd=" + placeAdd + ", placeImageURL="
				+ placeImageURL + ", placeImageData=" + placeImageData + "]";
	}

}