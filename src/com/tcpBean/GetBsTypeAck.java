package com.tcpBean;
 
/**
 * 获取基站信息返回
 * 
 * cmdtype(getmovebsinfoack)
 * userid
 * bsid
 * bsname
 * bslevel
 * ack 
 * 
 * @author 12878
 *
 */

public class GetBsTypeAck {
	private String cmdtype = "getbstypeack";
	private String userid;
	private String period;
	private String bsid;
	private String bsname;
	private String bslevel;
	private String bstype;
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

	public String getBstype() {
		return bstype;
	}

	public void setBstype(String bstype) {
		this.bstype = bstype;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	@Override
	public String toString() {
		return "GetBsTypeAck{" +
				"cmdtype='" + cmdtype + '\'' +
				", userid='" + userid + '\'' +
				", period='" + period + '\'' +
				", bsid='" + bsid + '\'' +
				", bsname='" + bsname + '\'' +
				", bslevel='" + bslevel + '\'' +
				", bstype='" + bstype + '\'' +
				", ack='" + ack + '\'' +
				'}';
	}
}
