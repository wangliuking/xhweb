package xh.mybatis.bean;

public class SensorBean {
	private String bsId;
	private String deviceId;
	private String deviceName;
	private String singleName;
	private String singleValue;
	private int status;
	private int state_alarm;
	public String getBsId() {
		return bsId;
	}
	public void setBsId(String bsId) {
		this.bsId = bsId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getSingleName() {
		return singleName;
	}
	public void setSingleName(String singleName) {
		this.singleName = singleName;
	}
	public String getSingleValue() {
		return singleValue;
	}
	public void setSingleValue(String singleValue) {
		this.singleValue = singleValue;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getState_alarm() {
		return state_alarm;
	}
	public void setState_alarm(int state_alarm) {
		this.state_alarm = state_alarm;
	}
	@Override
	public String toString() {
		return "SensorBean [bsId=" + bsId + ", deviceId=" + deviceId
				+ ", deviceName=" + deviceName + ", singleName=" + singleName
				+ ", singleValue=" + singleValue + ", status=" + status
				+ ", state_alarm=" + state_alarm + "]";
	}
	

}
