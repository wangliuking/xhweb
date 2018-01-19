package com.tcpBean;

import java.util.List;
import java.util.Map;

/**
 * app调度台作业表提交
 * 
 * cmdtype(dispatchtable)
 * serialnumber
 * userid
 * dispatchname			--网管名称
 * dispatchplace		--地点
 * date					--日期
 * checkman				--巡检人
 * message				--调度台巡检数组，每组包含3个字段，分别为检查状况、问题描述、处理状况及遗留问题,每组具体内容与巡检表对应
 * 
 * @author 12878
 *
 */

public class DispatchTable {
	private String cmdtype = "dispatchtable";
	private String serialnumber;
	private String userid;
	private String dispatchname;
	private String dispatchplace;
	private String date;
	private String checkman;
	private List<Map<String,Object>> message;
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
	public String getDispatchname() {
		return dispatchname;
	}
	public void setDispatchname(String dispatchname) {
		this.dispatchname = dispatchname;
	}
	public String getDispatchplace() {
		return dispatchplace;
	}
	public void setDispatchplace(String dispatchplace) {
		this.dispatchplace = dispatchplace;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCheckman() {
		return checkman;
	}
	public void setCheckman(String checkman) {
		this.checkman = checkman;
	}
	public List<Map<String, Object>> getMessage() {
		return message;
	}
	public void setMessage(List<Map<String, Object>> message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "DispatchTable [cmdtype=" + cmdtype + ", serialnumber="
				+ serialnumber + ", userid=" + userid + ", dispatchname="
				+ dispatchname + ", dispatchplace=" + dispatchplace + ", date="
				+ date + ", checkman=" + checkman + ", message=" + message
				+ "]";
	}
	
}
