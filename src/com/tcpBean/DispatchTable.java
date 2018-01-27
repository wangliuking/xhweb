package com.tcpBean;

import java.util.List;
import java.util.Map;

/**
 * app调度台作业表提交
 * 
 * cmdtype(dispatchtable)
 * serialnumber
 * userid
 * dispatchname			--网管名称
 * dispatchplace		--地点
 * commitdate			--日期
 * checkman				--巡检人
 * longitude			--GPS经度坐标
 * latitude				--GPS纬度坐标
 * address
 * message				--调度台巡检数组，每组包含3个字段，分别为检查状况、问题描述、处理状况及遗留问题,每组具体内容与巡检表对应
 * 
 * @author 12878
 *
 */

public class DispatchTable {
	private String cmdtype = "dispatchtable";
	private String serialnumber;
	private String userid;
	private String dispatchname;
	private String dispatchplace;
	private String commitdate;
	private String checkman;
	private String longitude;
	private String latitude;
	private String address;
	private List<Map<String,String>> message;
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
	public String getDispatchname() {
		return dispatchname;
	}
	public void setDispatchname(String dispatchname) {
		this.dispatchname = dispatchname;
	}
	public String getDispatchplace() {
		return dispatchplace;
	}
	public void setDispatchplace(String dispatchplace) {
		this.dispatchplace = dispatchplace;
	}
	public String getCommitdate() {
		return commitdate;
	}
	public void setCommitdate(String commitdate) {
		this.commitdate = commitdate;
	}
	public String getCheckman() {
		return checkman;
	}
	public void setCheckman(String checkman) {
		this.checkman = checkman;
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
	public List<Map<String, String>> getMessage() {
		return message;
	}
	public void setMessage(List<Map<String, String>> message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "DispatchTable [cmdtype=" + cmdtype + ", serialnumber="
				+ serialnumber + ", userid=" + userid + ", dispatchname="
				+ dispatchname + ", dispatchplace=" + dispatchplace
				+ ", commitdate=" + commitdate + ", checkman=" + checkman
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", address=" + address + ", message=" + message + "]";
	}
	
}
