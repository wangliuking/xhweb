package com.tcpBean;

/**
 * app故障处理任务单(根据表单填写内容)
 * 
 * cmdtype(errprotable)
 * serialnumber		--流水号
 * userid
 * bsid
 * bsname
 * dispatchtime		--派单时间
 * dispatchman		--派单人
 * errtype			--故障类型
 * errlevel			--故障等级
 * errfoundtime		--故障发现时间
 * errslovetime		--故障恢复时间
 * progress			--处理进展
 * proresult		--处理结果
 * workman			--处理人
 * auditor			--审核人
 * 
 * @author 12878
 *
 */

public class ErrProTable {
	private int id;
	private String cmdtype = "errprotable";
	private String serialnumber;
	private String userid;
	private String bsid;
	private String bsname;
	private String dispatchtime;
	private String dispatchman;
	private String errtype;
	private String errlevel;
	private String errfoundtime;
	private String errslovetime;
	private String progress;
	private String proresult;
	private String workman;
	private String auditor;
	private String longitude;
	private String latitude;
	private String  address;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getBsid() {
		return bsid;
	}
	public void setBsid(String bsid) {
		this.bsid = bsid;
	}
	public String getBsname() {
		return bsname;
	}
	public void setBsname(String bsname) {
		this.bsname = bsname;
	}
	public String getDispatchtime() {
		return dispatchtime;
	}
	public void setDispatchtime(String dispatchtime) {
		this.dispatchtime = dispatchtime;
	}
	public String getDispatchman() {
		return dispatchman;
	}
	public void setDispatchman(String dispatchman) {
		this.dispatchman = dispatchman;
	}
	public String getErrtype() {
		return errtype;
	}
	public void setErrtype(String errtype) {
		this.errtype = errtype;
	}
	public String getErrlevel() {
		return errlevel;
	}
	public void setErrlevel(String errlevel) {
		this.errlevel = errlevel;
	}
	public String getErrfoundtime() {
		return errfoundtime;
	}
	public void setErrfoundtime(String errfoundtime) {
		this.errfoundtime = errfoundtime;
	}
	public String getErrslovetime() {
		return errslovetime;
	}
	public void setErrslovetime(String errslovetime) {
		this.errslovetime = errslovetime;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getProresult() {
		return proresult;
	}
	public void setProresult(String proresult) {
		this.proresult = proresult;
	}
	public String getWorkman() {
		return workman;
	}
	public void setWorkman(String workman) {
		this.workman = workman;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "ErrProTable [id=" + id + ", cmdtype=" + cmdtype
				+ ", serialnumber=" + serialnumber + ", userid=" + userid
				+ ", bsid=" + bsid + ", bsname=" + bsname + ", dispatchtime="
				+ dispatchtime + ", dispatchman=" + dispatchman + ", errtype="
				+ errtype + ", errlevel=" + errlevel + ", errfoundtime="
				+ errfoundtime + ", errslovetime=" + errslovetime
				+ ", progress=" + progress + ", proresult=" + proresult
				+ ", workman=" + workman + ", auditor=" + auditor
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", address=" + address + "]";
	}
	
}
