package com.tcpBean;

/**
 * 获取所有手台的srcId和经纬度 app->server
 * 
 * cmdtype(getforgpsdst)
 * userid   --用户名
 * 
 * @author 12878
 *
 */

public class GetForGpsDst {
	private String cmdtype = "getforgpsdst";
	private String userid;
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
	@Override
	public String toString() {
		return "GetForGpsDst [cmdtype=" + cmdtype + ", userid=" + userid + "]";
	}
	
}
