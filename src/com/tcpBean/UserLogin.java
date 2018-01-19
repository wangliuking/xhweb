package com.tcpBean;

/**app登录处理类
 * 
 * cmdtype (userlogin)	--命令类型
 * userid				--用户id
 * passwd				--用户密码
 * serialnumber			--流水号
 * 
 * @author 12878
 *
 */
public class UserLogin {
	
	private String cmdtype = "userlogin";
	private String userid;
	private String passwd;
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
	@Override
	public String toString() {
		return "Login [cmdtype=" + cmdtype + ", userid=" + userid + ", passwd="
				+ passwd + ", serialnumber=" + serialnumber + "]";
	}
	
}
