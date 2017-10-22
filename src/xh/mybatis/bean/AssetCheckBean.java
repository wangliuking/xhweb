package xh.mybatis.bean;

public class AssetCheckBean {
	private int id;
	private String person;
	private String tel;
	private String account;
	private int status;
	private String note;
	private String applyTime;
	private String user1;
	private String time1;
	private String note1;
	private String time2;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	
	@Override
	public String toString() {
		return "AssetCheckBean [id=" + id + ", person=" + person + ", tel="
				+ tel + ", account=" + account + ", status=" + status
				+ ", note=" + note + ", applyTime=" + applyTime + ", user1="
				+ user1 + ", time1=" + time1 + ", note1=" + note1 + ", time2="
				+ time2 + "]";
	}
	
	
	

}
