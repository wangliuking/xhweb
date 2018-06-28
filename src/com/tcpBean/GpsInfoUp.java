package com.tcpBean;

import java.util.List;
import java.util.Map;

/**
 * app在线用户上传位置信息
 * 
 * cmdtype（gpsinfoup）
 * userid         
 * name			--名称
 * longitude			--GPS经度坐标
 * latitude				--GPS纬度坐标
 * address               --地理位置信息
 * @author 12878
 *
 */

public class GpsInfoUp {
	private String cmdtype = "gpsinfoup";
	private String userid;
	private String name;
	private String longitude;
	private String latitude;
	private String address;
	public String getCmdtype() {
		return cmdtype;
	}
	public void setCmdtype(String cmdtype) {
		this.cmdtype = cmdtype;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	@Override
	public String toString() {
		return "GpsInfoUp [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", name=" + name + ", longitude=" + longitude + ", latitude="
				+ latitude + ", address=" + address + "]";
	}

}
