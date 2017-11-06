package xh.org.socket;

public class KillStruct {
	private int operation;
	private String message;
	
	
	private int msId;
	private int userId;
	private int killCmd;
	
	public KillStruct(){}
	
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	public int getMsId() {
		return msId;
	}
	public void setMsId(int msId) {
		this.msId = msId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getKillCmd() {
		return killCmd;
	}
	public void setKillCmd(int killCmd) {
		this.killCmd = killCmd;
	}

	@Override
	public String toString() {
		return "KillStruct [operation=" + operation + ", message=" + message
				+ ", msId=" + msId + ", userId=" + userId + ", killCmd="
				+ killCmd + "]";
	}
	
	
	

}
