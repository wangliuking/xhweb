package xh.mybatis.bean;

public class ChartReportImpBsBean {
	
	private int bsid;
	private int regUsers=0;
	private int TotalActiveCalls;
	private int TotalActiveCallDuration;
	public int getBsid() {
		return bsid;
	}
	public void setBsid(int bsid) {
		this.bsid = bsid;
	}
	public int getTotalActiveCalls() {
		return TotalActiveCalls;
	}
	public void setTotalActiveCalls(int totalActiveCalls) {
		TotalActiveCalls = totalActiveCalls;
	}
	public int getTotalActiveCallDuration() {
		return TotalActiveCallDuration;
	}
	public void setTotalActiveCallDuration(int totalActiveCallDuration) {
		TotalActiveCallDuration = totalActiveCallDuration;
	}
	public int getRegUsers() {
		return regUsers;
	}
	public void setRegUsers(int regUsers) {
		this.regUsers = regUsers;
	}
	
	

}
