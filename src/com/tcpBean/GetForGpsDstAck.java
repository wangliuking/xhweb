package com.tcpBean;
 
import java.util.List;
import java.util.Map;

/**
 * 返回手台的srcId和经纬度 server->app
 * 
 * cmdtype (getforgpsdstack)
 * userid             --用户名
 * infolist 		   --所有srcId和经纬度
 * 
 * @author 12878
 *
 */

public class GetForGpsDstAck {
	private String cmdtype = "getforgpsdstack";
	private String userid;
	private List<Map<String,String>> infolist;
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
	public List<Map<String, String>> getInfolist() {
		return infolist;
	}
	public void setInfolist(List<Map<String, String>> infolist) {
		this.infolist = infolist;
	}
	@Override
	public String toString() {
		return "GetForGpsDstAck [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", infolist=" + infolist + "]";
	}
	
}
