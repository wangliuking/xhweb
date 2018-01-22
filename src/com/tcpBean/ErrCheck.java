package com.tcpBean;

/**
 * app故障处理审核请求
 * 
 * cmdtype(errcheck)
 * serialnumber
 * userid
 * 
 * @author 12878
 *
 */

public class ErrCheck {
	private String cmdtype = "errcheck";
	private String serialnumber;
	private String userid;
	private String longitude;
	private String latitude;
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
	@Override
	public String toString() {
		return "ErrCheck [cmdtype=" + cmdtype + ", serialnumber="
				+ serialnumber + ", userid=" + userid + ", longitude="
				+ longitude + ", latitude=" + latitude + "]";
	}
	
}
