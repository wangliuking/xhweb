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
	private Map<String,List<Map<String,Object>>> bsstatus;
	private Map<String,Object> emhinfo;
	private List<BsInspectTable> inspectlist;

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

	public List<BsInspectTable> getInspectlist() {
		return inspectlist;
	}

	public void setInspectlist(List<BsInspectTable> inspectlist) {
		this.inspectlist = inspectlist;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Map<String, List<Map<String, Object>>> getBsstatus() {
		return bsstatus;
	}

	public void setBsstatus(Map<String, List<Map<String, Object>>> bsstatus) {
		this.bsstatus = bsstatus;
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
				", bsstatus=" + bsstatus +
				", emhinfo=" + emhinfo +
				", inspectlist=" + inspectlist +
				'}';
	}
}
