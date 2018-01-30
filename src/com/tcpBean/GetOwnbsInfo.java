package com.tcpBean;

/**
 * 获取自建基站信息
 * 
 * cmdtype(getownbsinfo)
 * userid
 * bsid
 * 
 * @author 12878
 *
 */

public class GetOwnbsInfo {
	private String cmdtype = "getownbsinfo";
	private String userid;
	private String bsid;
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
	@Override
	public String toString() {
		return "GetOwnbsInfo [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", bsid=" + bsid + "]";
	}
	
}
