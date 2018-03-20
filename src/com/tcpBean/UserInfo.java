package com.tcpBean;
 
/**
 * app获取用户信息
 * 
 * cmdtype (getuserinfo)
 * userid
 * serialnumber			--流水号
 * 
 * @author 12878
 *
 */

public class UserInfo {
	private String cmdtype = "getuserinfo";
	private String userid;
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
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	@Override
	public String toString() {
		return "UserInfo [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", serialnumber=" + serialnumber + "]";
	}
	
	
}
