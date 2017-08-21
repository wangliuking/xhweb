package xh.mybatis.bean;

public class CommunicationSupportBean {
	private int id;
	private String unit;
	private String time;
	private String reason;
	private String area;
	private String supportTime;
	private String need;
	private String person;
	private String tel;
	private int checked;
	private String user1;
	private String time1;
	private String user2;
	private String time2;
	private int star;
	private String userName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
		//指定审核入网申请的主管部门领导
		private String user_MainManager;
		public String getUser_MainManager() {
			return user_MainManager;
		}
		public void setUser_MainManager(String user_MainManager) {
			this.user_MainManager = user_MainManager;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSupportTime() {
		return supportTime;
	}
	public void setSupportTime(String supportTime) {
		this.supportTime = supportTime;
	}
	public String getNeed() {
		return need;
	}
	public void setNeed(String need) {
		this.need = need;
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
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getUser2() {
		return user2;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	
}
