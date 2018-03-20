package xh.mybatis.bean;
 

public class BsStatusBean{

	/**
	 * 基站状态表 实体类
	 */
	private int bsId;
	private int status;
	private int groupNum;
	private int mscNum;
	private String updateTime;
	
	private String name;
	private String clock_status;
	private String Returnloss1;
	private String Returnloss2;
	private String bscRuntime;
	private String bsrRunStatus;
	private String rx1;
	private String rx2;
	private String enbRunTime;
	private String psm1runtime;
	private String psm2runtime;
	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}
	public int getMscNum() {
		return mscNum;
	}
	public void setMscNum(int mscNum) {
		this.mscNum = mscNum;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClock_status() {
		return clock_status;
	}
	public void setClock_status(String clock_status) {
		this.clock_status = clock_status;
	}
	public String getReturnloss1() {
		return Returnloss1;
	}
	public void setReturnloss1(String returnloss1) {
		Returnloss1 = returnloss1;
	}
	public String getReturnloss2() {
		return Returnloss2;
	}
	public void setReturnloss2(String returnloss2) {
		Returnloss2 = returnloss2;
	}
	public String getBscRuntime() {
		return bscRuntime;
	}
	public void setBscRuntime(String bscRuntime) {
		this.bscRuntime = bscRuntime;
	}
	public String getBsrRunStatus() {
		return bsrRunStatus;
	}
	public void setBsrRunStatus(String bsrRunStatus) {
		this.bsrRunStatus = bsrRunStatus;
	}
	public String getRx1() {
		return rx1;
	}
	public void setRx1(String rx1) {
		this.rx1 = rx1;
	}
	public String getRx2() {
		return rx2;
	}
	public void setRx2(String rx2) {
		this.rx2 = rx2;
	}
	public String getEnbRunTime() {
		return enbRunTime;
	}
	public void setEnbRunTime(String enbRunTime) {
		this.enbRunTime = enbRunTime;
	}
	public String getPsm1runtime() {
		return psm1runtime;
	}
	public void setPsm1runtime(String psm1runtime) {
		this.psm1runtime = psm1runtime;
	}
	public String getPsm2runtime() {
		return psm2runtime;
	}
	public void setPsm2runtime(String psm2runtime) {
		this.psm2runtime = psm2runtime;
	}
	


}
