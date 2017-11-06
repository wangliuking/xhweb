package xh.org.socket;

/************************************************************************
 * DispatchUserIA 调度用户业务属性数据结构体
 * *********************************************************************/

public class DispatchUserIAStruct{
	
	private int operation;
	private int id;//4
	private String name;//16
	private int monitorOn;//1
	private int PCPreempt;//1
	private int callPriority;//1
	private int allMute;//1
	private int allMuteTimeout;//1
	private int pttPriority;//1     26+4+2
	
// 新增
	private int prohibitTone; // 1 允许通话禁止音
	private int sideTone; //  1  允许侧音
	private int patchGroupNum; //  4  派接组数目
	private int MSGroupNum;//  4   多选组数目
	private int APBNum; //  4  APB传输数目
	private int calledPreempt; //  4    1~14 被叫用户预占
	private int inboundCall;//  1  电话呼入
	private int inboundPTT; //  1  PTT呼入
	private int instantTransmit;//  1  瞬时传输
	private int patchPC;//  1  派接私密呼叫
	
	public DispatchUserIAStruct(){}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMonitorOn() {
		return monitorOn;
	}

	public void setMonitorOn(int monitorOn) {
		this.monitorOn = monitorOn;
	}

	public int getPCPreempt() {
		return PCPreempt;
	}

	public void setPCPreempt(int pCPreempt) {
		PCPreempt = pCPreempt;
	}

	public int getCallPriority() {
		return callPriority;
	}

	public void setCallPriority(int callPriority) {
		this.callPriority = callPriority;
	}

	public int getAllMute() {
		return allMute;
	}

	public void setAllMute(int allMute) {
		this.allMute = allMute;
	}

	public int getAllMuteTimeout() {
		return allMuteTimeout;
	}

	public void setAllMuteTimeout(int allMuteTimeout) {
		this.allMuteTimeout = allMuteTimeout;
	}

	public int getPttPriority() {
		return pttPriority;
	}

	public void setPttPriority(int pttPriority) {
		this.pttPriority = pttPriority;
	}

	public int getProhibitTone() {
		return prohibitTone;
	}

	public void setProhibitTone(int prohibitTone) {
		this.prohibitTone = prohibitTone;
	}

	public int getSideTone() {
		return sideTone;
	}

	public void setSideTone(int sideTone) {
		this.sideTone = sideTone;
	}

	public int getPatchGroupNum() {
		return patchGroupNum;
	}

	public void setPatchGroupNum(int patchGroupNum) {
		this.patchGroupNum = patchGroupNum;
	}

	public int getMSGroupNum() {
		return MSGroupNum;
	}

	public void setMSGroupNum(int mSGroupNum) {
		MSGroupNum = mSGroupNum;
	}

	public int getAPBNum() {
		return APBNum;
	}

	public void setAPBNum(int aPBNum) {
		APBNum = aPBNum;
	}

	public int getCalledPreempt() {
		return calledPreempt;
	}

	public void setCalledPreempt(int calledPreempt) {
		this.calledPreempt = calledPreempt;
	}

	public int getInboundCall() {
		return inboundCall;
	}

	public void setInboundCall(int inboundCall) {
		this.inboundCall = inboundCall;
	}

	public int getInboundPTT() {
		return inboundPTT;
	}

	public void setInboundPTT(int inboundPTT) {
		this.inboundPTT = inboundPTT;
	}

	public int getInstantTransmit() {
		return instantTransmit;
	}

	public void setInstantTransmit(int instantTransmit) {
		this.instantTransmit = instantTransmit;
	}

	public int getPatchPC() {
		return patchPC;
	}

	public void setPatchPC(int patchPC) {
		this.patchPC = patchPC;
	}

	@Override
	public String toString() {
		return "DispatchUserIAStruct [operation=" + operation + ", id=" + id
				+ ", name=" + name + ", monitorOn=" + monitorOn
				+ ", PCPreempt=" + PCPreempt + ", callPriority=" + callPriority
				+ ", allMute=" + allMute + ", allMuteTimeout=" + allMuteTimeout
				+ ", pttPriority=" + pttPriority + ", prohibitTone="
				+ prohibitTone + ", sideTone=" + sideTone + ", patchGroupNum="
				+ patchGroupNum + ", MSGroupNum=" + MSGroupNum + ", APBNum="
				+ APBNum + ", calledPreempt=" + calledPreempt
				+ ", inboundCall=" + inboundCall + ", inboundPTT=" + inboundPTT
				+ ", instantTransmit=" + instantTransmit + ", patchPC="
				+ patchPC + "]";
	}
	
	//
	
	
	
	
}
