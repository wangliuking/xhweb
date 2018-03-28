package xh.org.socket;

public class GpsSetStruct {
	private int srcId;//4
	private int dstId;//4
	
	
	
	
	private int locationDstId;//4
	private int referenceNumber;//1
	

	private int  enableFlag;//1
	
	
	private int triggerType;//1
	private int triggerPara;//4
	public int getSrcId() {
		return srcId;
	}
	public void setSrcId(int srcId) {
		this.srcId = srcId;
	}
	public int getDstId() {
		return dstId;
	}
	public void setDstId(int dstId) {
		this.dstId = dstId;
	}
	public int getLocationDstId() {
		return locationDstId;
	}
	public void setLocationDstId(int locationDstId) {
		this.locationDstId = locationDstId;
	}
	public int getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(int referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public int getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(int enableFlag) {
		this.enableFlag = enableFlag;
	}
	public int getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(int triggerType) {
		this.triggerType = triggerType;
	}
	public int getTriggerPara() {
		return triggerPara;
	}
	public void setTriggerPara(int triggerPara) {
		this.triggerPara = triggerPara;
	}
	@Override
	public String toString() {
		return "GpsSetStruct [srcId=" + srcId + ", dstId=" + dstId
				+ ", locationDstId=" + locationDstId + ", referenceNumber="
				+ referenceNumber + ", enableFlag=" + enableFlag
				+ ", triggerType=" + triggerType + ", triggerPara="
				+ triggerPara + "]";
	}
	
	

}
