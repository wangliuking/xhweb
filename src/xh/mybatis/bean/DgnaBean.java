package xh.mybatis.bean;

public class DgnaBean {
	private int userId;
	private int groupId;
	
	
	private int opra;
	
	private int mscId;  //2
	private int issi;   //4
	private int gssi;    //4
	private String groupname;  //32
	private int attached;   //1
	private int cou;   ///1
	private int operation;   ///1
	private int status;    //1
	

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
	public int getOpra() {
		return opra;
	}
	public void setOpra(int opra) {
		this.opra = opra;
	}
	public int getMscId() {
		return mscId;
	}
	public void setMscId(int mscId) {
		this.mscId = mscId;
	}
	public int getIssi() {
		return issi;
	}
	public void setIssi(int issi) {
		this.issi = issi;
	}
	public int getGssi() {
		return gssi;
	}
	public void setGssi(int gssi) {
		this.gssi = gssi;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public int getAttached() {
		return attached;
	}
	public void setAttached(int attached) {
		this.attached = attached;
	}
	public int getCou() {
		return cou;
	}
	public void setCou(int cou) {
		this.cou = cou;
	}
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	
	

}
