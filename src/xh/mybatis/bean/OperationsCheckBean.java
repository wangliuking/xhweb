package xh.mybatis.bean;

public class OperationsCheckBean {
	private int id;
	private String applyId;
	private int userId;
	private String user;
	private String userName;
	private String tel;
	private String checkMonth;
	
	private String fileName;
	private String filePath;
	private String comment;
	private String createTime;
	private String checkUser;
	private String checkTime;
	private String note1;
	private int status;
	
	
	
	
	
	public String getCheckMonth() {
		return checkMonth;
	}
	public void setCheckMonth(String checkMonth) {
		this.checkMonth = checkMonth;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	@Override
	public String toString() {
		return "OperationsCheckBean [id=" + id + ", applyId=" + applyId
				+ ", userId=" + userId + ", user=" + user + ", userName="
				+ userName + ", tel=" + tel + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", comment=" + comment
				+ ", createTime=" + createTime + ", checkUser=" + checkUser
				+ ", checkTime=" + checkTime + ", note1=" + note1 + ", status="
				+ status + "]";
	}
	
	

}
