package com.nadri.vo;

import java.io.InputStream;

public class RestaurantVo {
	private int restaurantId;
	private String restaurantName;
	private String restaurantContent;
	private double restaurantLatitude;
	private double restaurantLongitude;
	private int restaurantCityId;
	private String restaurantAddress;
	private int restaurantAdd;
	private String restaurantImageURL;
	private InputStream restaurantImageData;

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantContent() {
		return restaurantContent;
	}

	public void setRestaurantContent(String restaurantContent) {
		this.restaurantContent = restaurantContent;
	}

	public double getRestaurantLatitude() {
		return restaurantLatitude;
	}

	public double getRestaurantLongitude() {
		return restaurantLongitude;
	}

	public String getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}

	public int getRestaurantAdd() {
		return restaurantAdd;
	}

	public void setRestaurantAdd(int restaurantAdd) {
		this.restaurantAdd = restaurantAdd;
	}

	public String getRestaurantImageURL() {
		return restaurantImageURL;
	}

	public void setRestaurantImageURL(String restaurantImageURL) {
		this.restaurantImageURL = restaurantImageURL;
	}

	public InputStream getRestaurantImageData() {
		return restaurantImageData;
	}

	public void setRestaurantImageData(InputStream restaurantImageData) {
		this.restaurantImageData = restaurantImageData;
	}

	public void setRestaurantLatitude(double restaurantLatitude) {
		this.restaurantLatitude = restaurantLatitude;
	}

	public void setRestaurantLongitude(double restaurantLongitude) {
		this.restaurantLongitude = restaurantLongitude;
	}

	public int getRestaurantCityId() {
		return restaurantCityId;
	}

	public void setRestaurantCityId(int restaurantCityId) {
		this.restaurantCityId = restaurantCityId;
	}

	@Override
	public String toString() {
		return "RestaurantVo [restaurantId=" + restaurantId + ", restaurantName=" + restaurantName
				+ ", restaurantContent=" + restaurantContent + ", restaurantLatitude=" + restaurantLatitude
				+ ", restaurantLongitude=" + restaurantLongitude + ", restaurantCityId=" + restaurantCityId
				+ ", restaurantAddress=" + restaurantAddress + ", restaurantAdd=" + restaurantAdd
				+ ", restaurantImageURL=" + restaurantImageURL + ", restaurantImageData=" + restaurantImageData + "]";
	}

}