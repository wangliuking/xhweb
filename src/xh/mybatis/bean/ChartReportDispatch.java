package xh.mybatis.bean;

public class ChartReportDispatch {
	private String dstId;
	private String dstName;
	private String ip;
	private int flag;
	private int setupStatus;
	private String dxbox_ip;
	private String dxbox_runtime;
	public String getDstId() {
		return dstId;
	}
	public void setDstId(String dstId) {
		this.dstId = dstId;
	}
	public String getDstName() {
		return dstName;
	}
	public void setDstName(String dstName) {
		this.dstName = dstName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDxbox_ip() {
		return dxbox_ip;
	}
	public void setDxbox_ip(String dxbox_ip) {
		this.dxbox_ip = dxbox_ip;
	}
	public String getDxbox_runtime() {
		return dxbox_runtime;
	}
	public void setDxbox_runtime(String dxbox_runtime) {
		this.dxbox_runtime = dxbox_runtime;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getSetupStatus() {
		return setupStatus;
	}
	public void setSetupStatus(int setupStatus) {
		this.setupStatus = setupStatus;
	}
	@Override
	public String toString() {
		return "ChartReportDispatch [dstId=" + dstId + ", dstName=" + dstName
				+ ", ip=" + ip + ", flag=" + flag + ", setupStatus="
				+ setupStatus + ", dxbox_ip=" + dxbox_ip + ", dxbox_runtime="
				+ dxbox_runtime + "]";
	}
	
	
	

}
