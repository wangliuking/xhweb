package xh.mybatis.bean;

public class AlarmList {
	private String siteId;
	private String name;
	private String level;
	private String zone;
	private String fsuId;
	private String serialNo;
	private String deviceId;
	private String deviceName;
	private String alarmId;
	private String alarmlevel;
	private String alarmFlag;
	private String alarmDesc;	
	private String startTime;
	private String alarmTime;
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getFsuId() {
		return fsuId;
	}
	public void setFsuId(String fsuId) {
		this.fsuId = fsuId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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
	public String getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}
	public String getAlarmlevel() {
		return alarmlevel;
	}
	public void setAlarmlevel(String alarmlevel) {
		this.alarmlevel = alarmlevel;
	}
	public String getAlarmFlag() {
		return alarmFlag;
	}
	public void setAlarmFlag(String alarmFlag) {
		this.alarmFlag = alarmFlag;
	}
	public String getAlarmDesc() {
		return alarmDesc;
	}
	public void setAlarmDesc(String alarmDesc) {
		this.alarmDesc = alarmDesc;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Override
	public String toString() {
		return "AlarmList [siteId=" + siteId + ", name=" + name + ", level="
				+ level + ", zone=" + zone + ", fsuId=" + fsuId + ", serialNo="
				+ serialNo + ", deviceId=" + deviceId + ", deviceName="
				+ deviceName + ", alarmId=" + alarmId + ", alarmlevel="
				+ alarmlevel + ", alarmFlag=" + alarmFlag + ", alarmDesc="
				+ alarmDesc + ", startTime=" + startTime + ", alarmTime="
				+ alarmTime + "]";
	}
	
	
}
