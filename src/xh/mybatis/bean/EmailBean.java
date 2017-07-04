package xh.mybatis.bean;

public class EmailBean {
	private int id;
	private String title;
	private String recvUser;
	private String content;
	private String sendUser;
	private String time;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRecvUser() {
		return recvUser;
	}
	public void setRecvUser(String recvUser) {
		this.recvUser = recvUser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "EmailBean [id=" + id + ", title=" + title + ", recvUser="
				+ recvUser + ", content=" + content + ", sendUser=" + sendUser
				+ ", time=" + time + ", status=" + status + "]";
	}
	
	

}
