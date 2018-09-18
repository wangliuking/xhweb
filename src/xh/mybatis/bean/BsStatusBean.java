package xh.mybatis.bean;
 

public class BsStatusBean{

	/**
	 * 基站状态表 实体类
	 */
	private int bsId;
	private int status;
	private int period;
	private int groupNum;
	private int mscNum;
	private String updateTime;
	
	private int chnumber;
	
	private String name;
	private String clock_status;
	private String bscRuntime;
	private String bsrRunStatus;
	private String rx1;
	private String rx2;
	
	
	
	private String bsr_state1;
	private String bsr_state2;
	private String bsr_state3;
	private String bsr_state4;
	
	private String dpx_retLoss1;
	private String dpx_retLoss2;
	private String dpx_retLoss3;
	private String dpx_retLoss4;
	
	private int carrierLowNoiseRXRssi1;
	private int carrierLowNoiseRXRssi2;
	private int carrierLowNoiseRXRssi3;
	private int carrierLowNoiseRXRssi4;
	
	private String icp_status;
	
	
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

	
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getBsr_state1() {
		return bsr_state1;
	}
	public void setBsr_state1(String bsr_state1) {
		this.bsr_state1 = bsr_state1;
	}
	public String getBsr_state2() {
		return bsr_state2;
	}
	public void setBsr_state2(String bsr_state2) {
		this.bsr_state2 = bsr_state2;
	}
	public String getBsr_state3() {
		return bsr_state3;
	}
	public void setBsr_state3(String bsr_state3) {
		this.bsr_state3 = bsr_state3;
	}
	public String getBsr_state4() {
		return bsr_state4;
	}
	public void setBsr_state4(String bsr_state4) {
		this.bsr_state4 = bsr_state4;
	}
	public String getDpx_retLoss1() {
		return dpx_retLoss1;
	}
	public void setDpx_retLoss1(String dpx_retLoss1) {
		this.dpx_retLoss1 = dpx_retLoss1;
	}
	public String getDpx_retLoss2() {
		return dpx_retLoss2;
	}
	public void setDpx_retLoss2(String dpx_retLoss2) {
		this.dpx_retLoss2 = dpx_retLoss2;
	}
	public String getDpx_retLoss3() {
		return dpx_retLoss3;
	}
	public void setDpx_retLoss3(String dpx_retLoss3) {
		this.dpx_retLoss3 = dpx_retLoss3;
	}
	public String getDpx_retLoss4() {
		return dpx_retLoss4;
	}
	public void setDpx_retLoss4(String dpx_retLoss4) {
		this.dpx_retLoss4 = dpx_retLoss4;
	}
	public int getCarrierLowNoiseRXRssi1() {
		return carrierLowNoiseRXRssi1;
	}
	public void setCarrierLowNoiseRXRssi1(int carrierLowNoiseRXRssi1) {
		this.carrierLowNoiseRXRssi1 = carrierLowNoiseRXRssi1;
	}
	public int getCarrierLowNoiseRXRssi2() {
		return carrierLowNoiseRXRssi2;
	}
	public void setCarrierLowNoiseRXRssi2(int carrierLowNoiseRXRssi2) {
		this.carrierLowNoiseRXRssi2 = carrierLowNoiseRXRssi2;
	}
	public int getCarrierLowNoiseRXRssi3() {
		return carrierLowNoiseRXRssi3;
	}
	public void setCarrierLowNoiseRXRssi3(int carrierLowNoiseRXRssi3) {
		this.carrierLowNoiseRXRssi3 = carrierLowNoiseRXRssi3;
	}
	public int getCarrierLowNoiseRXRssi4() {
		return carrierLowNoiseRXRssi4;
	}
	public void setCarrierLowNoiseRXRssi4(int carrierLowNoiseRXRssi4) {
		this.carrierLowNoiseRXRssi4 = carrierLowNoiseRXRssi4;
	}
	public int getChnumber() {
		return chnumber;
	}
	public void setChnumber(int chnumber) {
		this.chnumber = chnumber;
	}
	public String getIcp_status() {
		return icp_status;
	}
	public void setIcp_status(String icp_status) {
		this.icp_status = icp_status;
	}
	


}
