package com.tcpBean;
 
/**app请求所有基站信息
 * 
 * cmdtype (getallbslist)	--命令类型
 * userid				--用户id
 * serialnumber			--流水号
 * 
 * @author 12878
 *
 */
public class GetAllBsList {
	
	private String cmdtype = "getallbslist";
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
		return "GetAllBsList [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", serialnumber=" + serialnumber + "]";
	}
	
}
