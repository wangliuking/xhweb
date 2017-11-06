package xh.org.socket;

/************************************************************************
 * // TalkGroup 通话组数据结构体
 * *********************************************************************/
public class TalkGroupStruct {
	private String message;
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
	private int radioType;
	private int regroupAble;
	private int enabled;
	private String directDial="";//16
	
	public TalkGroupStruct(){}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

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

	public int getRegroupAble() {
		return regroupAble;
	}

	public void setRegroupAble(int regroupAble) {
		this.regroupAble = regroupAble;
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

	@Override
	public String toString() {
		return "TalkGroupStruct [message=" + message + ", operation="
				+ operation + ", id=" + id + ", name=" + name + ", alias="
				+ alias + ", mscId=" + mscId + ", vpnId=" + vpnId + ", saId="
				+ saId + ", iaId=" + iaId + ", vaId=" + vaId + ", preempt="
				+ preempt + ", radioType=" + radioType + ", regroupAble="
				+ regroupAble + ", enabled=" + enabled + ", directDial="
				+ directDial + "]";
	}
	
}
