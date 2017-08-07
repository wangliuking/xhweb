package xh.mybatis.bean;

public class EmhAlarmBean {
	private String deviceId;
	private int 	et;
	private int level;
	private int state_alarm;
	private String description;
	private String createTime;
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public int getEt() {
		return et;
	}
	public void setEt(int et) {
		this.et = et;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getState_alarm() {
		return state_alarm;
	}
	public void setState_alarm(int state_alarm) {
		this.state_alarm = state_alarm;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "EmhAlarmBean [deviceId=" + deviceId + ", et=" + et + ", level="
				+ level + ", state_alarm=" + state_alarm + ", description="
				+ description + ", createTime=" + createTime + "]";
	}
	
	

}
