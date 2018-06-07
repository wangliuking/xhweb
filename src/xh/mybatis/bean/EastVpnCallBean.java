package xh.mybatis.bean;

public class EastVpnCallBean {
	
	private int TotalActiveCalls;
	private int   TotalActiveCallDuration;
	private int   AverageCallDuration;
	private int   dexTotalCalls;
	private int   TotalFailedCalls;
	private float   FailedPercentage;
	private int   NoEffectCalls;
	private int  vpnid;
	private String name;
	private String   starttime;
	private String   endtime;
	private float percent;
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
	public int getAverageCallDuration() {
		return AverageCallDuration;
	}
	public void setAverageCallDuration(int averageCallDuration) {
		AverageCallDuration = averageCallDuration;
	}
	public int getDexTotalCalls() {
		return dexTotalCalls;
	}
	public void setDexTotalCalls(int dexTotalCalls) {
		this.dexTotalCalls = dexTotalCalls;
	}
	public int getTotalFailedCalls() {
		return TotalFailedCalls;
	}
	public void setTotalFailedCalls(int totalFailedCalls) {
		TotalFailedCalls = totalFailedCalls;
	}
	public float getFailedPercentage() {
		return FailedPercentage;
	}
	public void setFailedPercentage(float failedPercentage) {
		FailedPercentage = failedPercentage;
	}
	public int getNoEffectCalls() {
		return NoEffectCalls;
	}
	public void setNoEffectCalls(int noEffectCalls) {
		NoEffectCalls = noEffectCalls;
	}
	public int getVpnid() {
		return vpnid;
	}
	public void setVpnid(int vpnid) {
		this.vpnid = vpnid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}

	

}
