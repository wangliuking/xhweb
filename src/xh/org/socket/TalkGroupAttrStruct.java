package xh.org.socket;
/************************************************************************
 *TalkGroupAttrStruct 通话播组/通播组业务属性数据结构体
 * *********************************************************************/

public class TalkGroupAttrStruct {
	private int operation;
	
	private int id;
	private String  name;//[16];
	private int  messageTransmission; //4
	private int  busyOverride;
	
	private int  emergencyCall; //1
	private int  emergencyAtNVS;
	private int  dispatchPriority; //4
	private int  priorityMonitor; //1
	private int  tgDataPreempt;
	
	//新增
	private int inactivityTime;// 1  默认为12  通话组非活跃时间
	private int callsWithoutMGEG;// 1   默认为false，true：是，false：否  无MGEG资源可呼叫
	private int audioInterrupt;//  1   语音中断模式: 从不/总是 , 默认为0，0：从不，1：总是
	private int userGroup;//  1  用户组  1~255
	
	
	
	public TalkGroupAttrStruct(){}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMessageTransmission() {
		return messageTransmission;
	}

	public void setMessageTransmission(int messageTransmission) {
		this.messageTransmission = messageTransmission;
	}

	public int getBusyOverride() {
		return busyOverride;
	}

	public void setBusyOverride(int busyOverride) {
		this.busyOverride = busyOverride;
	}

	public int getEmergencyCall() {
		return emergencyCall;
	}

	public void setEmergencyCall(int emergencyCall) {
		this.emergencyCall = emergencyCall;
	}

	public int getEmergencyAtNVS() {
		return emergencyAtNVS;
	}

	public void setEmergencyAtNVS(int emergencyAtNVS) {
		this.emergencyAtNVS = emergencyAtNVS;
	}

	public int getDispatchPriority() {
		return dispatchPriority;
	}

	public void setDispatchPriority(int dispatchPriority) {
		this.dispatchPriority = dispatchPriority;
	}

	public int getPriorityMonitor() {
		return priorityMonitor;
	}

	public void setPriorityMonitor(int priorityMonitor) {
		this.priorityMonitor = priorityMonitor;
	}

	public int getTgDataPreempt() {
		return tgDataPreempt;
	}

	public void setTgDataPreempt(int tgDataPreempt) {
		this.tgDataPreempt = tgDataPreempt;
	}

	public int getInactivityTime() {
		return inactivityTime;
	}

	public void setInactivityTime(int inactivityTime) {
		this.inactivityTime = inactivityTime;
	}

	public int getCallsWithoutMGEG() {
		return callsWithoutMGEG;
	}

	public void setCallsWithoutMGEG(int callsWithoutMGEG) {
		this.callsWithoutMGEG = callsWithoutMGEG;
	}

	public int getAudioInterrupt() {
		return audioInterrupt;
	}

	public void setAudioInterrupt(int audioInterrupt) {
		this.audioInterrupt = audioInterrupt;
	}

	public int getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(int userGroup) {
		this.userGroup = userGroup;
	}

	@Override
	public String toString() {
		return "TalkGroupAttrStruct [operation=" + operation + ", id=" + id
				+ ", name=" + name + ", messageTransmission="
				+ messageTransmission + ", busyOverride=" + busyOverride
				+ ", emergencyCall=" + emergencyCall + ", emergencyAtNVS="
				+ emergencyAtNVS + ", dispatchPriority=" + dispatchPriority
				+ ", priorityMonitor=" + priorityMonitor + ", tgDataPreempt="
				+ tgDataPreempt + ", inactivityTime=" + inactivityTime
				+ ", callsWithoutMGEG=" + callsWithoutMGEG
				+ ", audioInterrupt=" + audioInterrupt + ", userGroup="
				+ userGroup + "]";
	}

	

	
	

}
