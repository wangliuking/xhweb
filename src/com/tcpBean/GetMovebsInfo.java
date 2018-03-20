package com.tcpBean;
 
/**
 * 获取移动基站信息
 * cmdtype(getmovebsinfo)
 * userid
 * bsid
 * 
 * @author 12878
 *
 */

public class GetMovebsInfo {
	private String cmdtype = "getmovebsinfo";
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
		return "GetMovebsInfo [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", bsid=" + bsid + "]";
	}
	
}
