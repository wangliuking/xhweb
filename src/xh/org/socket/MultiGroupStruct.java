package xh.org.socket;

/************************************************************************
 * MultiGroup 通播组数据结构体
 * *********************************************************************/

public class MultiGroupStruct {
	private int operation;
	
	private int id;
	private String name; //16
	private String alias="";//8
	private int mscId;
	private long vpnId;
	private int saId;
	private int iaId;
	private int vaId;
	
	private int preempt;
	private int radioType;  //0~3之间的整数 TETRA = 0,PDT = 1,DMR = 2, MPT1327 = 3
	private int enabled;
	private String directDial="";//16
	
	//新增
	private int interruptWait; // 1  中断或等待模式
	private int pdtType; // 2...  0或581  PDT类型
	private int npType;//  2   0-1000 区号
	public MultiGroupStruct(){}
	
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
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getMscId() {
		return mscId;
	}
	public void setMscId(int mscId) {
		this.mscId = mscId;
	}
	public long getVpnId() {
		return vpnId;
	}
	public void setVpnId(long vpnId) {
		this.vpnId = vpnId;
	}
	public int getSaId() {
		return saId;
	}
	public void setSaId(int saId) {
		this.saId = saId;
	}
	public int getIaId() {
		return iaId;
	}
	public void setIaId(int iaId) {
		this.iaId = iaId;
	}
	public int getVaId() {
		return vaId;
	}
	public void setVaId(int vaId) {
		this.vaId = vaId;
	}
	public int getPreempt() {
		return preempt;
	}
	public void setPreempt(int preempt) {
		this.preempt = preempt;
	}
	public int getRadioType() {
		return radioType;
	}
	public void setRadioType(int radioType) {
		this.radioType = radioType;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getDirectDial() {
		return directDial;
	}
	public void setDirectDial(String directDial) {
		this.directDial = directDial;
	}

	public int getInterruptWait() {
		return interruptWait;
	}

	public void setInterruptWait(int interruptWait) {
		this.interruptWait = interruptWait;
	}

	public int getPdtType() {
		return pdtType;
	}

	public void setPdtType(int pdtType) {
		this.pdtType = pdtType;
	}

	public int getNpType() {
		return npType;
	}

	public void setNpType(int npType) {
		this.npType = npType;
	}

	@Override
	public String toString() {
		return "MultiGroupStruct [operation=" + operation + ", id=" + id
				+ ", name=" + name + ", alias=" + alias + ", mscId=" + mscId
				+ ", vpnId=" + vpnId + ", saId=" + saId + ", iaId=" + iaId
				+ ", vaId=" + vaId + ", preempt=" + preempt + ", radioType="
				+ radioType + ", enabled=" + enabled + ", directDial="
				+ directDial + ", interruptWait=" + interruptWait
				+ ", pdtType=" + pdtType + ", npType=" + npType + "]";
	}
	
	
}


