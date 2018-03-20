package com.tcpBean;
 
import java.util.List;
import java.util.Map;

/**
 * app返回用户信息
 * 
 * cmdtype (userinfoack)
 * userid
 * username 			--用户姓名
 * serialnumber			--流水号
 * bslist				--基站列表，数组的数组，包含该用户所负责的所有基站的ID和基站名称
 * 
 * @author 12878
 *
 */

public class UserInfoAck {
	private String cmdtype = "userinfoack";
	private String userid;
	private String username;
	private String serialnumber;
	private List<Map<String,String>> bslist;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public List<Map<String, String>> getBslist() {
		return bslist;
	}
	public void setBslist(List<Map<String, String>> bslist) {
		this.bslist = bslist;
	}
	@Override
	public String toString() {
		return "UserInfoAck [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", username=" + username + ", serialnumber=" + serialnumber
				+ ", bslist=" + bslist + "]";
	}

	
	
}
