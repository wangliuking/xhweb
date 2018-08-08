package xh.mybatis.bean;

public class AssetAddApplyBean {
	
	private int id;
	private String applyTag;
	private int userId;
	private String user;
	private String userName;
	private String tel;
	private String attachmentName;
	private String attachmentPath;
	private String comment;
	private String createTime;
	private int status;
	
	private String note1;
	private String time1;
	private String time2;
	private String time3;
	private String checkUser;
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	public String getTime3() {
		return time3;
	}
	public void setTime3(String time3) {
		this.time3 = time3;
	}
	public String getApplyTag() {
		return applyTag;
	}
	public void setApplyTag(String applyTag) {
		this.applyTag = applyTag;
	}
	@Override
	public String toString() {
		return "AssetAddApplyBean [id=" + id + ", applyTag=" + applyTag
				+ ", userId=" + userId + ", user=" + user + ", userName="
				+ userName + ", tel=" + tel + ", attachmentName="
				+ attachmentName + ", attachmentPath=" + attachmentPath
				+ ", comment=" + comment + ", createTime=" + createTime
				+ ", status=" + status + ", note1=" + note1 + ", time1="
				+ time1 + ", time2=" + time2 + ", time3=" + time3
				+ ", checkUser=" + checkUser + "]";
	}
	
	

}
