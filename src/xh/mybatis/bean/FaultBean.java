package xh.mybatis.bean;

public class FaultBean {
	private int id;
	private String unit;
	private String person;
	private String tel;
	private String information;
	private String time;
	private String time1;
	private String time2;
	private String user1;
	private String user2;
	private int checked;
	private String userName;
	//指定审核故障申请的主管部门领导
	private String user_MainManager;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUser_MainManager() {
		return user_MainManager;
	}
	public void setUser_MainManager(String user_MainManager) {
		this.user_MainManager = user_MainManager;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	public String getUser2() {
		return user2;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	
}
