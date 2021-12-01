package com.nadri.vo;

public class PlanVo {
	private int planId;
	private String planName;
	private String planStart;
	private String planEnd;
	private int planNo;
	private int planDay;
	private int planHotelId;
	private int planUserID;
	private int planPlaceID;
	private int planRestaurantId;
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanStart() {
		return planStart;
	}
	public void setPlanStart(String planStart) {
		this.planStart = planStart;
	}
	public String getPlanEnd() {
		return planEnd;
	}
	public void setPlanEnd(String planEnd) {
		this.planEnd = planEnd;
	}
	public int getPlanNo() {
		return planNo;
	}
	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}
	public int getPlanDay() {
		return planDay;
	}
	public void setPlanDay(int planDay) {
		this.planDay = planDay;
	}
	public int getPlanHotelId() {
		return planHotelId;
	}
	public void setPlanHotelId(int planHotelId) {
		this.planHotelId = planHotelId;
	}
	public int getPlanUserID() {
		return planUserID;
	}
	public void setPlanUserID(int planUserID) {
		this.planUserID = planUserID;
	}
	public int getPlanPlaceID() {
		return planPlaceID;
	}
	public void setPlanPlaceID(int planPlaceID) {
		this.planPlaceID = planPlaceID;
	}
	public int getPlanRestaurantId() {
		return planRestaurantId;
	}
	public void setPlanRestaurantId(int planRestaurantId) {
		this.planRestaurantId = planRestaurantId;
	}
	@Override
	public String toString() {
		return "PlanVo [planId=" + planId + ", planName=" + planName + ", planStart=" + planStart + ", planEnd="
				+ planEnd + ", planNo=" + planNo + ", planDay=" + planDay + ", planHotelId=" + planHotelId
				+ ", planUserID=" + planUserID + ", planPlaceID=" + planPlaceID + ", planRestaurantId="
				+ planRestaurantId + "]";
	}
	
	

}
