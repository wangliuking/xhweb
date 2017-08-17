package xh.mybatis.bean;

public class UserPowerBean {
	/*用户ID*/
	private int userId;
	
	/*平台管理权限*/
	private String p_add="off";
	private String p_update="off";
	private String p_delete="off";
	private String p_lock="off";
	
	/*业务管理权限*/
	private String b_add="off";
	private String b_update="off";
	private String b_delete="off";
	private String b_check="off";
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getP_add() {
		return p_add;
	}
	public void setP_add(String p_add) {
		this.p_add = p_add;
	}
	public String getP_update() {
		return p_update;
	}
	public void setP_update(String p_update) {
		this.p_update = p_update;
	}
	public String getP_delete() {
		return p_delete;
	}
	public void setP_delete(String p_delete) {
		this.p_delete = p_delete;
	}
	public String getP_lock() {
		return p_lock;
	}
	public void setP_lock(String p_lock) {
		this.p_lock = p_lock;
	}
	public String getB_add() {
		return b_add;
	}
	public void setB_add(String b_add) {
		this.b_add = b_add;
	}
	public String getB_update() {
		return b_update;
	}
	public void setB_update(String b_update) {
		this.b_update = b_update;
	}
	public String getB_delete() {
		return b_delete;
	}
	public void setB_delete(String b_delete) {
		this.b_delete = b_delete;
	}
	public String getB_check() {
		return b_check;
	}
	public void setB_check(String b_check) {
		this.b_check = b_check;
	}
	@Override
	public String toString() {
		return "UserPowerBean [userId=" + userId + ", p_add=" + p_add
				+ ", p_update=" + p_update + ", p_delete=" + p_delete
				+ ", p_lock=" + p_lock + ", b_add=" + b_add + ", b_update="
				+ b_update + ", b_delete=" + b_delete + ", b_check=" + b_check
				+ "]";
	}
	
	
	

}
