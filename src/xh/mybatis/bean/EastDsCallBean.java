package xh.mybatis.bean;

public class EastDsCallBean {
	private String begintime;
	private String endtime;
	private String mscid; 
	private String dsid; 
	private String dstId; 
	private String dstName; 
	private int totalActiveCalls; 
	private int averageCallDuration; 
	private int totalActiveCallDuration; 
	private int totalPTTs;
	
	
	public String getDstId() {
		return dstId;
	}
	public String getDstName() {
		return dstName;
	}
	public void setDstId(String dstId) {
		this.dstId = dstId;
	}
	public void setDstName(String dstName) {
		this.dstName = dstName;
	}
	public String getBegintime() {
		return begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public String getMscid() {
		return mscid;
	}
	public String getDsid() {
		return dsid;
	}
	public int getTotalActiveCalls() {
		return totalActiveCalls;
	}
	public int getAverageCallDuration() {
		return averageCallDuration;
	}
	public int getTotalActiveCallDuration() {
		return totalActiveCallDuration;
	}
	public int getTotalPTTs() {
		return totalPTTs;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public void setMscid(String mscid) {
		this.mscid = mscid;
	}
	public void setDsid(String dsid) {
		this.dsid = dsid;
	}
	public void setTotalActiveCalls(int totalActiveCalls) {
		this.totalActiveCalls = totalActiveCalls;
	}
	public void setAverageCallDuration(int averageCallDuration) {
		this.averageCallDuration = averageCallDuration;
	}
	public void setTotalActiveCallDuration(int totalActiveCallDuration) {
		this.totalActiveCallDuration = totalActiveCallDuration;
	}
	public void setTotalPTTs(int totalPTTs) {
		this.totalPTTs = totalPTTs;
	}
	
	

}
