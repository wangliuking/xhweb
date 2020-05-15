package xh.mybatis.bean;

public class EastGroupCallBean {
	private int TotalActiveCalls;
	private double TotalActiveCallDuration;
	private double AverageCallDuration;
	private int TotalCalls;
	private int TotalFailedCalls;
	private int FailedPercentage;
	private int NoEffectCalls;
	private String starttime;
	private String endtime;
	private int groupid;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalActiveCalls() {
		return TotalActiveCalls;
	}
	public double getTotalActiveCallDuration() {
		return TotalActiveCallDuration;
	}
	public double getAverageCallDuration() {
		return AverageCallDuration;
	}
	public int getTotalCalls() {
		return TotalCalls;
	}
	public int getTotalFailedCalls() {
		return TotalFailedCalls;
	}
	public int getFailedPercentage() {
		return FailedPercentage;
	}
	public int getNoEffectCalls() {
		return NoEffectCalls;
	}
	public String getStarttime() {
		return starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setTotalActiveCalls(int totalActiveCalls) {
		TotalActiveCalls = totalActiveCalls;
	}
	public void setTotalActiveCallDuration(double totalActiveCallDuration) {
		TotalActiveCallDuration = totalActiveCallDuration;
	}
	public void setAverageCallDuration(double averageCallDuration) {
		AverageCallDuration = averageCallDuration;
	}
	public void setTotalCalls(int totalCalls) {
		TotalCalls = totalCalls;
	}
	public void setTotalFailedCalls(int totalFailedCalls) {
		TotalFailedCalls = totalFailedCalls;
	}
	public void setFailedPercentage(int failedPercentage) {
		FailedPercentage = failedPercentage;
	}
	public void setNoEffectCalls(int noEffectCalls) {
		NoEffectCalls = noEffectCalls;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	
	

}

