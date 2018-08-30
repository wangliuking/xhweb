package xh.mybatis.bean;

public class ThreeEmhAlarmBean {
	private int JFNode;
	private String DevNode;
	private String NodeID;
	private String DevName;
	private String State;
	private String AlarmText;
	private String AlarmDate;
	private String AlarmTime;
	private String RelieveDate;
	private String RelieveTime;
	private String name;
	private String description;
	
	private String time1;
	private String time2;
	

	public int getJFNode() {
		return JFNode;
	}


	public void setJFNode(int jFNode) {
		JFNode = jFNode;
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


	public String getState() {
		return State;
	}


	public void setState(String state) {
		State = state;
	}


	public String getAlarmText() {
		return AlarmText;
	}


	public void setAlarmText(String alarmText) {
		AlarmText = alarmText;
	}


	public String getAlarmDate() {
		return AlarmDate;
	}


	public void setAlarmDate(String alarmDate) {
		AlarmDate = alarmDate;
	}


	public String getAlarmTime() {
		return AlarmTime;
	}


	public void setAlarmTime(String alarmTime) {
		AlarmTime = alarmTime;
	}


	public String getRelieveDate() {
		return RelieveDate;
	}


	public void setRelieveDate(String relieveDate) {
		RelieveDate = relieveDate;
	}


	public String getRelieveTime() {
		return RelieveTime;
	}


	public void setRelieveTime(String relieveTime) {
		RelieveTime = relieveTime;
	}


	public String getTime1() {
		return time1;
	}


	public void setTime1(String time1) {
		this.time1 = time1;
	}


	public String getTime2() {
		return time2;
	}


	public void setTime2(String time2) {
		this.time2 = time2;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	
/*	this.AlarmDate=alarmDate;
	this.AlarmTime=alarmTime;
	String a=this.AlarmDate.split(" ")[0];
	String[] t=this.AlarmTime.split(":");
	int t1=Integer.parseInt(t[0]);
	int t2=Integer.parseInt(t[1]);
	String h="",m="";
	if(t1<10){
		h="0"+t1;
	}else{
		h=t[0];
	}
	if(t2<10){
		m="0"+t2;
	}else{
		m=t[1];
	}
	this.time1=a+" "+h+":"+m+":00";*/
	
	
	

}
