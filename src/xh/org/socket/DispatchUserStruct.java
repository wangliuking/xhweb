package xh.org.socket;
/************************************************************************
 * DispatchUser 调度用户数据结构体
 * *********************************************************************/
public class DispatchUserStruct {


	private int operation;
	private String message;
	
	private int id;//4
	private String name; //16
	private String alias; //8
	private int mscId;//2
	private long vpnId;//8
	private int userType;//1
	private String password;//16
	private int vaid;//2
	private int said;//2
	private int masterId;//4
	private int fullDuplex; //1
	private int ambienceInitiation;//1
	private int clir;//1
	private int clirOverride;//1     //67+4
	
	//新增
	private String saName; //16
	private int supervisorStatus; //4  监视状态：主/次/无
	private int dispatcherType; //4   OTAP(0),PDT标准(1),PDT浙江(2)
	private int code; //4
	private int dispatchNum; //4
	private String dialString; //16
	
	public DispatchUserStruct(){}
	
	public int getOperation() {
		return operation;
	}


	public void setOperation(int operation) {
		this.operation = operation;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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


	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getVaid() {
		return vaid;
	}
	public void setVaid(int vaid) {
		this.vaid = vaid;
	}
	public int getSaid() {
		return said;
	}
	public void setSaid(int said) {
		this.said = said;
	}
	public int getMasterId() {
		return masterId;
	}
	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}
	public int getFullDuplex() {
		return fullDuplex;
	}
	public void setFullDuplex(int fullDuplex) {
		this.fullDuplex = fullDuplex;
	}
	public int getAmbienceInitiation() {
		return ambienceInitiation;
	}
	public void setAmbienceInitiation(int ambienceInitiation) {
		this.ambienceInitiation = ambienceInitiation;
	}
	public int getClir() {
		return clir;
	}
	public void setClir(int clir) {
		this.clir = clir;
	}
	public int getClirOverride() {
		return clirOverride;
	}
	public void setClirOverride(int clirOverride) {
		this.clirOverride = clirOverride;
	}

	public String getSaName() {
		return saName;
	}

	public void setSaName(String saName) {
		this.saName = saName;
	}

	public int getSupervisorStatus() {
		return supervisorStatus;
	}

	public void setSupervisorStatus(int supervisorStatus) {
		this.supervisorStatus = supervisorStatus;
	}

	public int getDispatcherType() {
		return dispatcherType;
	}

	public void setDispatcherType(int dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getDispatchNum() {
		return dispatchNum;
	}

	public void setDispatchNum(int dispatchNum) {
		this.dispatchNum = dispatchNum;
	}

	public String getDialString() {
		return dialString;
	}

	public void setDialString(String dialString) {
		this.dialString = dialString;
	}
	
	public String toString() {
		return "DispatchUserStruct [operation=" + operation + ", message="
				+ message + ", id=" + id + ", name=" + name + ", alias="
				+ alias + ", mscId=" + mscId + ", vpnId=" + vpnId
				+ ", userType=" + userType + ", password=" + password
				+ ", vaid=" + vaid + ", said=" + said + ", masterId="
				+ masterId + ", fullDuplex=" + fullDuplex
				+ ", ambienceInitiation=" + ambienceInitiation + ", clir="
				+ clir + ", clirOverride=" + clirOverride + ", saName="
				+ saName + ", supervisorStatus=" + supervisorStatus
				+ ", dispatcherType=" + dispatcherType + ", code=" + code
				+ ", dispatchNum=" + dispatchNum + ", dialString=" + dialString
				+ "]";
	}
}

