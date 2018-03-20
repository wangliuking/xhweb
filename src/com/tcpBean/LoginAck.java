package com.tcpBean;
 
/**app登录返回类
 * 
 * cmdtype (userlogin)	--命令类型
 * userid				--用户id
 * passwd				--用户密码
 * serialnumber			--流水号
 * ack (返回0 ：登陆成功；1：账号密码错误；2：其它原因)
 * 
 * @author 12878
 *
 */

public class LoginAck {
	private String cmdtype = "loginack";
	private String userid;
	private String passwd;
	private String serialnumber;
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
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getAck() {
		return ack;
	}
	public void setAck(String ack) {
		this.ack = ack;
	}
	@Override
	public String toString() {
		return "LoginAck [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", passwd=" + passwd + ", serialnumber=" + serialnumber
				+ ", ack=" + ack + "]";
	}
	
}
