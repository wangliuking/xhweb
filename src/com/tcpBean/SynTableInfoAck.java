package com.tcpBean;

import java.util.List;

public class SynTableInfoAck {
	private String cmdtype = "syntableinfoack";
	private String serialnumber;
	private String userid;
	private List<GetTableInfoAck> tableinfolist;

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

	public List<GetTableInfoAck> getTableinfolist() {
		return tableinfolist;
	}

	public void setTableinfolist(List<GetTableInfoAck> tableinfolist) {
		this.tableinfolist = tableinfolist;
	}

	@Override
	public String toString() {
		return "SynTableInfoAck{" +
				"cmdtype='" + cmdtype + '\'' +
				", serialnumber='" + serialnumber + '\'' +
				", userid='" + userid + '\'' +
				", tableinfolist=" + tableinfolist +
				'}';
	}
}
