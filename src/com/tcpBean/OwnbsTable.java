package com.tcpBean;

import java.util.List;
import java.util.Map;

/**
 * app 800M自建基站巡检表提交
 * 
 * cmdtype(ownbstable)
 * serialnumber
 * userid
 * bsname
 * bsid
 * bslevel
 * checkman
 * bstype
 * date
 * ammeternumber		--电表数
 * message				--自建基站巡检数组，每组包含2个字段，分别为执行情况、备注，每组具体内容与巡检表对应
 * remainwork			--遗留问题
 * 
 * @author 12878
 *
 */

public class OwnbsTable {
	private String cmdtype = "ownbstable";
	private String serialnumber;
	private String userid;
	private String bsname;
	private String bsid;
	private String bslevel;
	private String checkman;
	private String bstype;
	private String date;
	private String ammeternumber;
	private List<Map<String,Object>> message;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmmeternumber() {
		return ammeternumber;
	}
	public void setAmmeternumber(String ammeternumber) {
		this.ammeternumber = ammeternumber;
	}
	public List<Map<String, Object>> getMessage() {
		return message;
	}
	public void setMessage(List<Map<String, Object>> message) {
		this.message = message;
	}
	public String getRemainwork() {
		return remainwork;
	}
	public void setRemainwork(String remainwork) {
		this.remainwork = remainwork;
	}
	@Override
	public String toString() {
		return "OwnbsTable [cmdtype=" + cmdtype + ", serialnumber="
				+ serialnumber + ", userid=" + userid + ", bsname=" + bsname
				+ ", bsid=" + bsid + ", bslevel=" + bslevel + ", checkman="
				+ checkman + ", bstype=" + bstype + ", date=" + date
				+ ", ammeternumber=" + ammeternumber + ", message=" + message
				+ ", remainwork=" + remainwork + "]";
	}
}
