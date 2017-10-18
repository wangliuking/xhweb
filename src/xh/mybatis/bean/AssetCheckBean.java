package xh.mybatis.bean;

public class AssetCheckBean {
	private int id;
	private String person;
	private String tel;
	private String account;
	private int status;
	private String note;
	private String applyTime;
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
	@Override
	public String toString() {
		return "AssetCheckBean [id=" + id + ", person=" + person + ", tel="
				+ tel + ", account=" + account + ", status=" + status
				+ ", note=" + note + ", applyTime=" + applyTime + "]";
	}
	
	
	

}
