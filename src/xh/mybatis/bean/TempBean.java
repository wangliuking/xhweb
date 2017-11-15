package xh.mybatis.bean;

public class TempBean {
	private String bsId;
	private String name;
	private String period;
	private String lat;
	private String lng;
	private String chnumber;
	private String type;
	private String zone;
	private String address;
	private String level;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getChnumber() {
		return chnumber;
	}
	public void setChnumber(String chnumber) {
		this.chnumber = chnumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBsId() {
		return bsId;
	}
	public void setBsId(String bsId) {
		this.bsId = bsId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "TempBean [bsId=" + bsId + ", name=" + name + ", period="
				+ period + ", lat=" + lat + ", lng=" + lng + ", chnumber="
				+ chnumber + ", type=" + type + ", zone=" + zone + ", address="
				+ address + ", level=" + level + "]";
	}
	
	
}
