package com.tcpBean;

/**
 * app故障处理审核结果
 * 
 * cmdtype(errcheckack)
 * serialnumber
 * userid
 * result			--审核结果：0：通过审核；1：未通过;
 * 
 * @author 12878
 *
 */

public class ErrCheckAck {
	private String cmdtype = "errcheckack";
	private String serialnumber;
	private String auditor;
	private String userid;
	private String result;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	@Override
	public String toString() {
		return "ErrCheckAck{" +
				"cmdtype='" + cmdtype + '\'' +
				", serialnumber='" + serialnumber + '\'' +
				", auditor='" + auditor + '\'' +
				", userid='" + userid + '\'' +
				", result='" + result + '\'' +
				'}';
	}
}
