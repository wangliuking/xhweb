package xh.mybatis.bean;

public class EastMscDayBean {
	private int TotalActiveCall;
	private double TotalActiveCallDuration;
	private double AverageCallDuration;
	private int TotalCalls;
	private int TotalFailedCalls;
	private int FailedPercentage;
	private int NoEffectCalls;
	private String starttime;
	private int mscid;

	private int GroupCalls;
	private double GroupCallDuration;
	private int PrivateCalls;
	private double PrivateCallDuration;
	private int PhoneCalls;
	private double PhoneCallDuration;
	private int EmergencyCalls;
	private double EmergencyCallDuration;
	private int PrivateDuplexCalls;
	private int PrivateSimplexCalls;
	
	
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

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public int getMscid() {
		return mscid;
	}

	public void setMscid(int mscid) {
		this.mscid = mscid;
	}

	public int getGroupCalls() {
		return GroupCalls;
	}

	public void setGroupCalls(int groupCalls) {
		GroupCalls = groupCalls;
	}

	public double getGroupCallDuration() {
		return GroupCallDuration;
	}

	public void setGroupCallDuration(double groupCallDuration) {
		GroupCallDuration = groupCallDuration;
	}

	public int getPrivateCalls() {
		return PrivateCalls;
	}

	public void setPrivateCalls(int privateCalls) {
		PrivateCalls = privateCalls;
	}

	public double getPrivateCallDuration() {
		return PrivateCallDuration;
	}

	public void setPrivateCallDuration(double privateCallDuration) {
		PrivateCallDuration = privateCallDuration;
	}

	public int getPhoneCalls() {
		return PhoneCalls;
	}

	public void setPhoneCalls(int phoneCalls) {
		PhoneCalls = phoneCalls;
	}

	public double getPhoneCallDuration() {
		return PhoneCallDuration;
	}

	public void setPhoneCallDuration(double phoneCallDuration) {
		PhoneCallDuration = phoneCallDuration;
	}

	public int getEmergencyCalls() {
		return EmergencyCalls;
	}

	public void setEmergencyCalls(int emergencyCalls) {
		EmergencyCalls = emergencyCalls;
	}

	public double getEmergencyCallDuration() {
		return EmergencyCallDuration;
	}

	public void setEmergencyCallDuration(double emergencyCallDuration) {
		EmergencyCallDuration = emergencyCallDuration;
	}

	public int getPrivateDuplexCalls() {
		return PrivateDuplexCalls;
	}

	public void setPrivateDuplexCalls(int privateDuplexCalls) {
		PrivateDuplexCalls = privateDuplexCalls;
	}

	public int getPrivateSimplexCalls() {
		return PrivateSimplexCalls;
	}

	public void setPrivateSimplexCalls(int privateSimplexCalls) {
		PrivateSimplexCalls = privateSimplexCalls;
	}

	public int getNoEffectCalls() {
		return NoEffectCalls;
	}

	public void setNoEffectCalls(int noEffectCalls) {
		NoEffectCalls = noEffectCalls;
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
	

}
