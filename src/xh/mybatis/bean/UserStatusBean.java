package xh.mybatis.bean;

public class UserStatusBean {
	private int id;
	private String userId;
	private int bsId;
	private String name;
	private int regStatus;
	private int callStatus;
	private String TGid;
	private String time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRegStatus() {
		return regStatus;
	}
	public void setRegStatus(int regStatus) {
		this.regStatus = regStatus;
	}
	public int getCallStatus() {
		return callStatus;
	}
	public void setCallStatus(int callStatus) {
		this.callStatus = callStatus;
	}
	public String getTGid() {
		return TGid;
	}
	public void setTGid(String tGid) {
		TGid = tGid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "UserStatusBean [id=" + id + ", userId=" + userId + ", bsId="
				+ bsId + ", name=" + name + ", regStatus=" + regStatus
				+ ", callStatus=" + callStatus + ", TGid=" + TGid + ", time="
				+ time + "]";
	}
	
	

}
