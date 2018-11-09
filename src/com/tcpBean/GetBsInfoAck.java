package com.tcpBean;

import java.util.List;
import java.util.Map;

/**
 * 获取基站详细信息返回
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

public class GetBsInfoAck {
	private String cmdtype = "getbsinfoack";
	private String userid;
	private String bsid;
	private String serialnumber;
	private String period;
	private Map<String,Object> bsinfo;
	private Map<String,Object> emhinfo;
	private List<Map<String,Object>> inspectlist;

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

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public Map<String, Object> getBsinfo() {
		return bsinfo;
	}

	public void setBsinfo(Map<String, Object> bsinfo) {
		this.bsinfo = bsinfo;
	}

	public Map<String, Object> getEmhinfo() {
		return emhinfo;
	}

	public void setEmhinfo(Map<String, Object> emhinfo) {
		this.emhinfo = emhinfo;
	}

	public List<Map<String, Object>> getInspectlist() {
		return inspectlist;
	}

	public void setInspectlist(List<Map<String, Object>> inspectlist) {
		this.inspectlist = inspectlist;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	@Override
	public String toString() {
		return "GetBsInfoAck{" +
				"cmdtype='" + cmdtype + '\'' +
				", userid='" + userid + '\'' +
				", bsid='" + bsid + '\'' +
				", serialnumber='" + serialnumber + '\'' +
				", period='" + period + '\'' +
				", bsinfo=" + bsinfo +
				", emhinfo=" + emhinfo +
				", inspectlist=" + inspectlist +
				'}';
	}
}
