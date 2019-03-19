package xh.mybatis.bean;

public class EmhThreeBean {
	private int bsId;
	private String DevNode;
	private String NodeID;
	private String DevName;
	private String NodeAttribute;
	private String State;
	private String value;
	private String AlarmValue;
	private String AlarmAdduction;
	private String AlarmState;
	private String AlarmGrade;
	private String AlarmEX;
	
	private String updateTime;
	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
	}
	public String getDevNode() {
		return DevNode;
	}
	public void setDevNode(String devNode) {
		DevNode = devNode;
	}
	public String getNodeID() {
		return NodeID;
	}
	public void setNodeID(String nodeID) {
		NodeID = nodeID;
	}
	public String getDevName() {
		return DevName;
	}
	public void setDevName(String devName) {
		DevName = devName;
	}
	public String getNodeAttribute() {
		return NodeAttribute;
	}
	public void setNodeAttribute(String nodeAttribute) {
		NodeAttribute = nodeAttribute;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAlarmValue() {
		return AlarmValue;
	}
	public void setAlarmValue(String alarmValue) {
		AlarmValue = alarmValue;
	}
	public String getAlarmAdduction() {
		return AlarmAdduction;
	}
	public void setAlarmAdduction(String alarmAdduction) {
		AlarmAdduction = alarmAdduction;
	}
	
	public String getAlarmState() {
		return AlarmState;
	}
	public void setAlarmState(String alarmState) {
		AlarmState = alarmState;
	}
	public String getAlarmGrade() {
		return AlarmGrade;
	}
	public void setAlarmGrade(String alarmGrade) {
		AlarmGrade = alarmGrade;
	}
	public String getAlarmEX() {
		return AlarmEX;
	}
	public void setAlarmEX(String alarmEX) {
		AlarmEX = alarmEX;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "EmhThreeBean [bsId=" + bsId + ", DevNode=" + DevNode
				+ ", NodeID=" + NodeID + ", DevName=" + DevName
				+ ", NodeAttribute=" + NodeAttribute + ", State=" + State
				+ ", value=" + value + ", AlarmValue=" + AlarmValue
				+ ", AlarmAdduction=" + AlarmAdduction + ", AlarmState="
				+ AlarmState + ", AlarmGrade=" + AlarmGrade + ", AlarmEX="
				+ AlarmEX + "]";
	}
	
	
	

}
