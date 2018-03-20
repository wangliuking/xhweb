package com.tcpBean;
 
/**
 * 获取自建基站信息返回
 * 
 * cmdtype(getownbsinfoack)
 * userid
 * bsid
 * bsname
 * bslevel
 * ack 
 * 
 * @author 12878
 *
 */

public class GetOwnbsInfoAck {
	private String cmdtype = "getownbsinfoack";
	private String userid;
	private String bsid;
	private String bsname;
	private String bslevel;
	private String ack;
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
	public String getBsname() {
		return bsname;
	}
	public void setBsname(String bsname) {
		this.bsname = bsname;
	}
	public String getBslevel() {
		return bslevel;
	}
	public void setBslevel(String bslevel) {
		this.bslevel = bslevel;
	}
	public String getAck() {
		return ack;
	}
	public void setAck(String ack) {
		this.ack = ack;
	}
	@Override
	public String toString() {
		return "GetOwnbsInfoAck [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", bsid=" + bsid + ", bsname=" + bsname + ", bslevel="
				+ bslevel + ", ack=" + ack + "]";
	}
	

}
