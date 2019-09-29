package com.tcpBean;

/**
 * app故障处理任务单(根据表单填写内容)
 * 
 * cmdtype(errprotable) id serialnumber --流水号 userid bsid bsname dispatchtime
 * --派单时间 dispatchman --派单人 errtype --故障类型 errlevel --故障等级 errfoundtime --故障发现时间
 * errslovetime --故障恢复时间 progress --处理进展 proresult --处理结果 workman --处理人 auditor
 * --审核人 longitude --GPS经度坐标 latitude --GPS纬度坐标 address --地址
 * 
 * @author 12878
 * 
 */

public class ErrProTable {
	private String cmdtype = "errprotable";
	private int id;
	private String serialnumber;
	private String userid;
	private String orderAccount;
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
	private String hungup;
	private String workman;
	private String auditor;
	private String longitude;
	private String latitude;
	private String address;
	private String recv_user;
	private String copy_user;
	private String recv_user_name;
	private String copy_user_name;
	private String handlepower;//0:可接单；1：可处理；2：只读
	private String handleUserid;
	
	private String zbdldm;
	private String from;
	private String status;

	public String getCmdtype() {
		return cmdtype;
	}

	public void setCmdtype(String cmdtype) {
		this.cmdtype = cmdtype;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getOrderAccount() {
		return orderAccount;
	}

	public void setOrderAccount(String orderAccount) {
		this.orderAccount = orderAccount;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getZbdldm() {
		return zbdldm;
	}

	public void setZbdldm(String zbdldm) {
		this.zbdldm = zbdldm;
	}

	

	public String getHandlepower() {
		return handlepower;
	}

	public void setHandlepower(String handlepower) {
		this.handlepower = handlepower;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHungup() {
		return hungup;
	}

	public void setHungup(String hungup) {
		this.hungup = hungup;
	}

	public String getRecv_user() {
		return recv_user;
	}

	public String getCopy_user() {
		return copy_user;
	}

	public String getRecv_user_name() {
		return recv_user_name;
	}

	public String getCopy_user_name() {
		return copy_user_name;
	}

	public void setRecv_user(String recv_user) {
		this.recv_user = recv_user;
	}

	public void setCopy_user(String copy_user) {
		this.copy_user = copy_user;
	}

	public void setRecv_user_name(String recv_user_name) {
		this.recv_user_name = recv_user_name;
	}

	public void setCopy_user_name(String copy_user_name) {
		this.copy_user_name = copy_user_name;
	}

	public String getHandleUserid() {
		return handleUserid;
	}

	public void setHandleUserid(String handleUserid) {
		this.handleUserid = handleUserid;
	}

	@Override
	public String toString() {
		return "ErrProTable{" +
				"cmdtype='" + cmdtype + '\'' +
				", id=" + id +
				", serialnumber='" + serialnumber + '\'' +
				", userid='" + userid + '\'' +
				", orderAccount='" + orderAccount + '\'' +
				", bsid='" + bsid + '\'' +
				", bsname='" + bsname + '\'' +
				", dispatchtime='" + dispatchtime + '\'' +
				", dispatchman='" + dispatchman + '\'' +
				", errtype='" + errtype + '\'' +
				", errlevel='" + errlevel + '\'' +
				", errfoundtime='" + errfoundtime + '\'' +
				", errslovetime='" + errslovetime + '\'' +
				", progress='" + progress + '\'' +
				", proresult='" + proresult + '\'' +
				", hungup='" + hungup + '\'' +
				", workman='" + workman + '\'' +
				", auditor='" + auditor + '\'' +
				", longitude='" + longitude + '\'' +
				", latitude='" + latitude + '\'' +
				", address='" + address + '\'' +
				", recv_user='" + recv_user + '\'' +
				", copy_user='" + copy_user + '\'' +
				", recv_user_name='" + recv_user_name + '\'' +
				", copy_user_name='" + copy_user_name + '\'' +
				", handlepower='" + handlepower + '\'' +
				", handleUserid='" + handleUserid + '\'' +
				", zbdldm='" + zbdldm + '\'' +
				", from='" + from + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
