package xh.mybatis.bean;

public class DispatchBean {
	private int dstId; 
	private String dstName; 
	private String dstAlias; 
	private String IP; 
	private String d_vpnId; 
	private int setupStatus=0; 
	private String flag; 
	private String alarmFlag; 
	private String updateTime;
	
	public int getDstId() {
		return dstId;
	}
	public void setDstId(int dstId) {
		this.dstId = dstId;
	}
	public String getDstName() {
		return dstName;
	}
	public void setDstName(String dstName) {
		this.dstName = dstName;
	}
	public String getDstAlias() {
		return dstAlias;
	}
	public void setDstAlias(String dstAlias) {
		this.dstAlias = dstAlias;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getD_vpnId() {
		return d_vpnId;
	}
	public void setD_vpnId(String d_vpnId) {
		this.d_vpnId = d_vpnId;
	}
	
	public int getSetupStatus() {
		return setupStatus;
	}
	public void setSetupStatus(int setupStatus) {
		this.setupStatus = setupStatus;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAlarmFlag() {
		return alarmFlag;
	}
	public void setAlarmFlag(String alarmFlag) {
		this.alarmFlag = alarmFlag;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "DispatchBean [dstId=" + dstId + ", dstName=" + dstName
				+ ", dstAlias=" + dstAlias + ", IP=" + IP + ", d_vpnId="
				+ d_vpnId + ", setupStatus=" + setupStatus + ", flag=" + flag
				+ ", alarmFlag=" + alarmFlag + ", updateTime=" + updateTime
				+ "]";
	}
	
	
	
}
