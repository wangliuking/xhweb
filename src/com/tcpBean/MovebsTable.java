package com.tcpBean;
 
import java.util.List;
import java.util.Map;

/**
 * app 800M移动基站巡检表提交
 * 
 * cmdtype(movebstable)
 * serialnumber
 * userid
 * bsname
 * bsid
 * bslevel
 * checkman
 * bstype
 * commitdate
 * longitude			--GPS经度坐标	
 * latitude				--GPS纬度坐标
 * address	
 * message				--移动基站巡检数组，每组包含2个字段，分别为执行情况、备注，每组具体内容与巡检表对应
 * remainwork			--遗留问题
 * 
 * @author 12878
 *
 */

public class MovebsTable {
	private String cmdtype = "movebstable";
	private String serialnumber;
	private String userid;
	private String bsname;
	private String bsid;
	private String bslevel;
	private String checkman;
	private String bstype;
	private String commitdate;
	private String longitude;
	private String latitude;
	private String address;
	private List<Map<String,String>> message;
	private String remainwork;
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
	public String getBsname() {
		return bsname;
	}
	public void setBsname(String bsname) {
		this.bsname = bsname;
	}
	public String getBsid() {
		return bsid;
	}
	public void setBsid(String bsid) {
		this.bsid = bsid;
	}
	public String getBslevel() {
		return bslevel;
	}
	public void setBslevel(String bslevel) {
		this.bslevel = bslevel;
	}
	public String getCheckman() {
		return checkman;
	}
	public void setCheckman(String checkman) {
		this.checkman = checkman;
	}
	public String getBstype() {
		return bstype;
	}
	public void setBstype(String bstype) {
		this.bstype = bstype;
	}
	public List<Map<String, String>> getMessage() {
		return message;
	}
	public void setMessage(List<Map<String, String>> message) {
		this.message = message;
	}
	public String getRemainwork() {
		return remainwork;
	}
	public void setRemainwork(String remainwork) {
		this.remainwork = remainwork;
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
	public String getCommitdate() {
		return commitdate;
	}
	public void setCommitdate(String commitdate) {
		this.commitdate = commitdate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "MovebsTable [cmdtype=" + cmdtype + ", serialnumber="
				+ serialnumber + ", userid=" + userid + ", bsname=" + bsname
				+ ", bsid=" + bsid + ", bslevel=" + bslevel + ", checkman="
				+ checkman + ", bstype=" + bstype + ", commitdate="
				+ commitdate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", address=" + address + ", message=" + message
				+ ", remainwork=" + remainwork + "]";
	}
	
}
