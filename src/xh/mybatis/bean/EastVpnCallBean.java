package xh.mybatis.bean;

public class EastVpnCallBean {
	
	private int TotalActiveCalls;
	private double   TotalActiveCallDuration;
	private double   AverageCallDuration;
	private int   dexTotalCalls;
	private int   TotalFailedCalls;
	private int   FailedPercentage;
	private int   NoEffectCalls;
	private long  vpnid;
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
	public double getTotalActiveCallDuration() {
		return TotalActiveCallDuration;
	}
	public void setTotalActiveCallDuration(double totalActiveCallDuration) {
		TotalActiveCallDuration = totalActiveCallDuration;
	}
	public double getAverageCallDuration() {
		return AverageCallDuration;
	}
	public void setAverageCallDuration(double averageCallDuration) {
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
	public int getFailedPercentage() {
		return FailedPercentage;
	}
	public void setFailedPercentage(int failedPercentage) {
		FailedPercentage = failedPercentage;
	}
	public int getNoEffectCalls() {
		return NoEffectCalls;
	}
	public void setNoEffectCalls(int noEffectCalls) {
		NoEffectCalls = noEffectCalls;
	}

	public long getVpnid() {
		return vpnid;
	}
	public void setVpnid(long vpnid) {
		this.vpnid = vpnid;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	
	

}
