package xh.mybatis.bean;

public class QuitNetBean {
	private int id;
	private String quitName;
	private String quitType;
	private String quitTime;
	private String quitReason;
	private String quitNumber;
	private String quitPerson;
	private String tel;
	//指定审核退网申请的主管部门领导
	private String user_MainManager;
	private int quit;
	private String userName;
	private String note1;
	private String note;
	
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuitNumber() {
		return quitNumber;
	}
	public void setQuitNumber(String quitNumber) {
		this.quitNumber = quitNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getQuitName() {
		return quitName;
	}
	public void setQuitName(String quitName) {
		this.quitName = quitName;
	}
	public String getQuitType() {
		return quitType;
	}
	public void setQuitType(String quitType) {
		this.quitType = quitType;
	}
	public String getQuitTime() {
		return quitTime;
	}
	public void setQuitTime(String quitTime) {
		this.quitTime = quitTime;
	}
	public String getQuitReason() {
		return quitReason;
	}
	public void setQuitReason(String quitReason) {
		this.quitReason = quitReason;
	}
	public String getQuitPerson() {
		return quitPerson;
	}
	public void setQuitPerson(String quitPerson) {
		this.quitPerson = quitPerson;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	} 
	public int getQuit() {
		return quit;
	}
	public void setQuit(int quit) {
		this.quit = quit;
	}
	public String getUser_MainManager() {
		return user_MainManager;
	}
	public void setUser_MainManager(String user_MainManager) {
		this.user_MainManager = user_MainManager;
	}	
	
	
	
}
