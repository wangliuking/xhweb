package xh.mybatis.bean;

public class QuitNetBean {
	private int id;
	private String quitName;
	private String quitType;
	private String quitTime;
	private String quitReason;
	private String quitPerson;
	private String tel;
	//指定审核退网申请的主管部门领导
	private String user_MainManager;
	private int quit;
	private String userName;
	
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
