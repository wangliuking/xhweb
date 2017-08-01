package xh.org.socket;


/*Agent 格式:*/
public class AgentDataStruct {
	private String uuid;
	private String name;
	private boolean status;
	private int state_alarm;
	private Devices devices;
	public boolean setStatus;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getState_alarm() {
		return state_alarm;
	}
	public void setState_alarm(int state_alarm) {
		this.state_alarm = state_alarm;
	}
	public Devices getDevices() {
		return devices;
	}
	public void setDevices(Devices devices) {
		this.devices = devices;
	}
	

}
/*Device 格式：*/

class Devices{
	private Spots spots;
	private String uuid;
	private String name;
	private boolean status;
	private int state_alarm;
	public Spots getSpots() {
		return spots;
	}
	public void setSpots(Spots spots) {
		this.spots = spots;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getState_alarm() {
		return state_alarm;
	}
	public void setState_alarm(int state_alarm) {
		this.state_alarm = state_alarm;
	}
	
	
}
/*Spot 数据格式：*/

class Spots{
	private String uuid;
	private String name;
	private int state_alarm;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getState_alarm() {
		return state_alarm;
	}
	public void setState_alarm(int state_alarm) {
		this.state_alarm = state_alarm;
	}
	

}
