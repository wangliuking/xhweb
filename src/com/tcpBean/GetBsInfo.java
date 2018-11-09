package com.tcpBean;
 
/**
 * 获取基站详细信息
 * cmdtype(getmovebsinfo)
 * userid
 * bsid
 * 
 * @author 12878
 *
 */

public class GetBsInfo {
	private String cmdtype = "getbsinfo";
	private String userid;
	private String bsid;
	private String serialnumber;
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
	public String getBsid() {
		return bsid;
	}
	public void setBsid(String bsid) {
		this.bsid = bsid;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	@Override
	public String toString() {
		return "GetBsInfo{" +
				"cmdtype='" + cmdtype + '\'' +
				", userid='" + userid + '\'' +
				", bsid='" + bsid + '\'' +
				", serialnumber='" + serialnumber + '\'' +
				'}';
	}
}
