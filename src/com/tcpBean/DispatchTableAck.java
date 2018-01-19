package com.tcpBean;

/**
 * app调度台巡检作业表应答
 * 
 * cmdtype(dispatchtableack)
 * serialnumber
 * userid
 * ack
 * 
 * @author 12878
 *
 */

public class DispatchTableAck {
	private String cmdtype = "dispatchtableack";
	private String serialnumber;
	private String userid;
	private String ack;
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
	public String getAck() {
		return ack;
	}
	public void setAck(String ack) {
		this.ack = ack;
	}
	@Override
	public String toString() {
		return "DispatchTableAck [cmdtype=" + cmdtype + ", serialnumber="
				+ serialnumber + ", userid=" + userid + ", ack=" + ack + "]";
	}
	
}
