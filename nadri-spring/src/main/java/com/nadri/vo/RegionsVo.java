package com.nadri.vo;

public class RegionsVo {
	private int regionId;
	private String regionName;
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	@Override
	public String toString() {
		return "RegionsVo [regionId=" + regionId + ", regionName=" + regionName + "]";
	}
	
	

}
