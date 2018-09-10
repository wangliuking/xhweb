package com.tcpBean;

/**
 * app故障处理审核请求
 * 
 * cmdtype(errcheck)
 * serialnumber
 * userid
 * longitude		--GPS经度坐标
 * latitude			--GPS纬度坐标
 * address			--地址
 * 
 * @author 12878
 *
 */

public class ErrCheck {
	private String cmdtype = "errcheck";
	private String serialnumber;
	private String userid;
	private String hungorder;
	private String longitude;
	private String latitude;
	private String address;
	public String getCmdtype() {
		return cmdtype;
	}
	public void setCmdtype(String cmdtype) {
		this.cmdtype = cmdtype;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getHungorder() {
		return hungorder;
	}

	public void setHungorder(String hungorder) {
		this.hungorder = hungorder;
	}

	@Override
	public String toString() {
		return "ErrCheck{" +
				"cmdtype='" + cmdtype + '\'' +
				", serialnumber='" + serialnumber + '\'' +
				", userid='" + userid + '\'' +
				", hungorder='" + hungorder + '\'' +
				", longitude='" + longitude + '\'' +
				", latitude='" + latitude + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
