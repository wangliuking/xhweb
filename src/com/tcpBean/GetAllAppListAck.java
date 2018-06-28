package com.tcpBean;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
 
/**app返回所有app信息
 * 
 * cmdtype (getallapplistack)	--命令类型
 * userid				--用户id
 * serialnumber			--流水号
 * status    1-传输结束 0-未结束
 * applist	--所有在线app的用户名，名称，经纬度
 * @author 12878
 *
 */
public class GetAllAppListAck {
	
	private String cmdtype = "getallapplistack";
	private String userid;
	private String serialnumber;
	private String status;
	private List<Map<String,String>> applist;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Map<String, String>> getApplist() {
		return applist;
	}
	public void setApplist(List<Map<String, String>> applist) {
		this.applist = applist;
	}
	@Override
	public String toString() {
		return "GetAllAppListAck [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", serialnumber=" + serialnumber + ", status=" + status
				+ ", applist=" + applist + "]";
	}
	
}
