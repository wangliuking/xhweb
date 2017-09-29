package xh.mybatis.bean;

public class DgnaBean {
	private int userId;
	private int status;
	private int groupId;
	
	@Override
	public String toString() {
		return "DgnaBean [userId=" + userId + ", status=" + status
				+ ", groupId=" + groupId + "]";
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	

}
