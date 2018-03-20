package com.tcpBean;
 
/**
 * 故障处理表响应
 * 
 * cmdtype(errprotableack)
 * serialnumber
 * userid
 * 
 * @author 12878
 *
 */

public class ErrProTableAck {
	private String cmdtype = "errprotableack";
	private String serialnumber;
	private String userid;
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
	@Override
	public String toString() {
		return "ErrProTableAck [cmdtype=" + cmdtype + ", serialnumber="
				+ serialnumber + ", userid=" + userid + "]";
	}
	
}
