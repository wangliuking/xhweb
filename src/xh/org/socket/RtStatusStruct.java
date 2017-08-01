package xh.org.socket;

public class RtStatusStruct {
	/*{“uuid”:””, “stattype”:2,”state”:3, “state_alarm”:6, “agentip”:”” }
	其中：
	uuid： 测点的 36 位 UUID，具备惟一性。
	stattype:状态类型，值如下：
	1：代理状态
	2：设备状态
	3：测点
	state：设备或代理状态，值如下： （当 stattype=3 时，此项无效）
	0：离线
	1：在线
	2：未知
	state_alarm：对象的告警状态（主动查询的时候才有效，其它时候通过 3.3.5.2 事件消息包来
	获取对象的告警状态）。主动查询状态消息包格式：
	{"type":31,"mcd":…………,"rtstatus":[StatusObject,StatusObject,...]}
	agentip:代理所在设备的 ip 地址,返回结果只有代理的状态才会有值。*/
	private String uuid;
	private String agentip;
	private int state;
	private int stattype;
	private int state_alarm;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getAgentip() {
		return agentip;
	}
	public void setAgentip(String agentip) {
		this.agentip = agentip;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getStattype() {
		return stattype;
	}
	public void setStattype(int stattype) {
		this.stattype = stattype;
	}
	public int getState_alarm() {
		return state_alarm;
	}
	public void setState_alarm(int state_alarm) {
		this.state_alarm = state_alarm;
	}
	@Override
	public String toString() {
		return "RtStatusStruct [uuid=" + uuid + ", agentip=" + agentip
				+ ", state=" + state + ", stattype=" + stattype
				+ ", state_alarm=" + state_alarm + "]";
	}
	
	


}
