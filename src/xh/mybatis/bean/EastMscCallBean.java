package xh.mybatis.bean;

public class EastMscCallBean {
	private int TotalActiveCall;
	private double TotalActiveCallDuration;
	private double AverageCallDuration;
	private int TotalCalls;
	private int TotalFailedCalls;
	private int FailedPercentage;
	private int NoEffectCalls;
	private String starttime;
	private String endtime;
	private int mscid;
	private int totalPTTs;
	private int totalQueueCount;
	private int totalQueueDuration;
	private int totalMaxReg;
	private int maxRegGroup;
	public int getTotalActiveCall() {
		return TotalActiveCall;
	}
	public void setTotalActiveCall(int totalActiveCall) {
		TotalActiveCall = totalActiveCall;
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
	public int getTotalCalls() {
		return TotalCalls;
	}
	public void setTotalCalls(int totalCalls) {
		TotalCalls = totalCalls;
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
	public int getMscid() {
		return mscid;
	}
	public void setMscid(int mscid) {
		this.mscid = mscid;
	}
	public int getTotalPTTs() {
		return totalPTTs;
	}
	public void setTotalPTTs(int totalPTTs) {
		this.totalPTTs = totalPTTs;
	}
	public int getTotalQueueCount() {
		return totalQueueCount;
	}
	public void setTotalQueueCount(int totalQueueCount) {
		this.totalQueueCount = totalQueueCount;
	}
	public int getTotalQueueDuration() {
		return totalQueueDuration;
	}
	public void setTotalQueueDuration(int totalQueueDuration) {
		this.totalQueueDuration = totalQueueDuration;
	}
	public int getTotalMaxReg() {
		return totalMaxReg;
	}
	public void setTotalMaxReg(int totalMaxReg) {
		this.totalMaxReg = totalMaxReg;
	}
	public int getMaxRegGroup() {
		return maxRegGroup;
	}
	public void setMaxRegGroup(int maxRegGroup) {
		this.maxRegGroup = maxRegGroup;
	}
	@Override
	public String toString() {
		return "EastMscCallBean [TotalActiveCall=" + TotalActiveCall
				+ ", TotalActiveCallDuration=" + TotalActiveCallDuration
				+ ", AverageCallDuration=" + AverageCallDuration
				+ ", TotalCalls=" + TotalCalls + ", TotalFailedCalls="
				+ TotalFailedCalls + ", FailedPercentage=" + FailedPercentage
				+ ", NoEffectCalls=" + NoEffectCalls + ", starttime="
				+ starttime + ", endtime=" + endtime + ", mscid=" + mscid
				+ ", totalPTTs=" + totalPTTs + ", totalQueueCount="
				+ totalQueueCount + ", totalQueueDuration="
				+ totalQueueDuration + ", totalMaxReg=" + totalMaxReg
				+ ", maxRegGroup=" + maxRegGroup + "]";
	}
	

}
