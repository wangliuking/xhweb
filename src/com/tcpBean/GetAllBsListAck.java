package com.tcpBean;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
 
/**app返回所有基站信息
 * 
 * cmdtype (getallbsListack)	--命令类型
 * userid				--用户id
 * serialnumber			--流水号
 * status    1-传输结束 0-未结束
 * bslist	--基站列表，数组的数组，包含该用户所负责的所有基站的ID和基站名称,经纬度
 * @author 12878
 *
 */
public class GetAllBsListAck {
	
	private String cmdtype = "getallbslistack";
	private String userid;
	private String serialnumber;
	private String status;
	private List<Map<String,String>> bslist;
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
	public List<Map<String, String>> getBslist() {
		return bslist;
	}
	public void setBslist(List<Map<String, String>> bslist) {
		this.bslist = bslist;
	}
	@Override
	public String toString() {
		return "GetAllBsListAck [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", serialnumber=" + serialnumber + ", status=" + status
				+ ", bslist=" + bslist + "]";
	}
	
}
