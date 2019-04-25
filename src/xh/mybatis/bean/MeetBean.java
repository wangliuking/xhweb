package xh.mybatis.bean;

public class MeetBean {
	private int id;
	private String name;
	private int type;
	private String start_time;
	private String end_time;
	private String address;
	private String person;
	private String content;
	private String check_info;
	private String user_need;
	private String emergency_info;
	private String other_info;
	private String sign;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCheck_info() {
		return check_info;
	}
	public void setCheck_info(String check_info) {
		this.check_info = check_info;
	}
	public String getUser_need() {
		return user_need;
	}
	public void setUser_need(String user_need) {
		this.user_need = user_need;
	}
	public String getEmergency_info() {
		return emergency_info;
	}
	public void setEmergency_info(String emergency_info) {
		this.emergency_info = emergency_info;
	}
	public String getOther_info() {
		return other_info;
	}
	public void setOther_info(String other_info) {
		this.other_info = other_info;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "MeetBean [id=" + id + ", name=" + name + ", type=" + type
				+ ", start_time=" + start_time + ", end_time=" + end_time
				+ ", address=" + address + ", person=" + person + ", content="
				+ content + ", check_info=" + check_info + ", user_need="
				+ user_need + ", emergency_info=" + emergency_info
				+ ", other_info=" + other_info + ", sign=" + sign + "]";
	}
	
	

}
