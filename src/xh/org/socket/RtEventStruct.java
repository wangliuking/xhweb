package xh.org.socket;

public class RtEventStruct {
/*	uuid： 测点的 36 位 UUID，具备惟一性。
	et: 事件类型，值如下：
	1：系统（告警)事件
	2：代理（告警)事件
	3：设备（告警)事件
	4：测点（告警)事件
	5：设备状态变化
	6：代理状态变化
	7：操作日志事件
	level：事件级别，1-10，数值越小，级别越高。
	description：事件详细描述消息
	state_alarm：事件状态，值如下：
	1：超上限
	2：超下限
	3：临近上限
	4：临近下限
	5：数据异常,采集策略用
	6：测点状态变化
	7：告警回复
	8：代理和设备联机
	9：代理和设备脱机
	10：文本报警，用于特定场合在代理端直接返回报警的文本
	dnt:事件发生的时间，格式为 UNIX 时间。*/

	private String description;
	private String dnt;
	private int et;
	private int level;
	private int state_alarm;
	private String uuid;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDnt() {
		return dnt;
	}
	public void setDnt(String dnt) {
		this.dnt = dnt;
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
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String toString() {
		return "RtEventStruct [description=" + description + ", dnt=" + dnt
				+ ", et=" + et + ", level=" + level + ", state_alarm="
				+ state_alarm + ", uuid=" + uuid + "]";
	}
	
	

}
