package xh.mybatis.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class OrderDataBean {
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
	private String auditorName;
	
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	private String zbdldm;
	private String from;
	private String status;
	private String userName;
	private int period;
	private int level;
	public String getCmdtype() {
		return cmdtype;
	}
	public int getId() {
		return id;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public String getUserid() {
		return userid;
	}
	public String getOrderAccount() {
		return orderAccount;
	}
	public String getBsid() {
		return bsid;
	}
	public String getBsname() {
		return bsname;
	}
	public String getDispatchtime() {
		return dispatchtime;
	}
	public String getDispatchman() {
		return dispatchman;
	}
	public String getErrtype() {
		return errtype;
	}
	public String getErrlevel() {
		return errlevel;
	}
	public String getErrfoundtime() {
		return errfoundtime;
	}
	public String getErrslovetime() {
		return errslovetime;
	}
	public String getProgress() {
		return progress;
	}
	public String getProresult() {
		return proresult;
	}
	public String getHungup() {
		return hungup;
	}
	public String getWorkman() {
		return workman;
	}
	public String getAuditor() {
		return auditor;
	}
	public String getLongitude() {
		return longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public String getAddress() {
		return address;
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
	public String getHandlepower() {
		return handlepower;
	}
	public String getHandleUserid() {
		return handleUserid;
	}
	public String getZbdldm() {
		return zbdldm;
	}
	public String getFrom() {
		return from;
	}
	public String getStatus() {
		return status;
	}
	public String getUserName() {
		return userName;
	}
	public int getPeriod() {
		return period;
	}
	public int getLevel() {
		return level;
	}
	public void setCmdtype(String cmdtype) {
		this.cmdtype = cmdtype;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setOrderAccount(String orderAccount) {
		this.orderAccount = orderAccount;
	}
	public void setBsid(String bsid) {
		this.bsid = bsid;
	}
	public void setBsname(String bsname) {
		this.bsname = bsname;
	}
	public void setDispatchtime(String dispatchtime) {
		this.dispatchtime = dispatchtime;
	}
	public void setDispatchman(String dispatchman) {
		this.dispatchman = dispatchman;
	}
	public void setErrtype(String errtype) {
		this.errtype = errtype;
	}
	public void setErrlevel(String errlevel) {
		this.errlevel = errlevel;
	}
	public void setErrfoundtime(String errfoundtime) {
		this.errfoundtime = errfoundtime;
	}
	public void setErrslovetime(String errslovetime) {
		this.errslovetime = errslovetime;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public void setProresult(String proresult) {
		this.proresult = proresult;
	}
	public void setHungup(String hungup) {
		this.hungup = hungup;
	}
	public void setWorkman(String workman) {
		this.workman = workman;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public void setHandlepower(String handlepower) {
		this.handlepower = handlepower;
	}
	public void setHandleUserid(String handleUserid) {
		this.handleUserid = handleUserid;
	}
	public void setZbdldm(String zbdldm) {
		this.zbdldm = zbdldm;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	

}
