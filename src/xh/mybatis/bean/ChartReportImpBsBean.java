package xh.mybatis.bean;

public class ChartReportImpBsBean {
	
	private int bsid;
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
	
	

}
