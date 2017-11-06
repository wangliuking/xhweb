package xh.org.socket;

/************************************************************************
 * //RadioUser 终端用户数据结构体
 * *********************************************************************/
public class RadioUserStruct {
	private String message;
	private int operation;
	
	private int id;
	private String name; //16       //四期：长度=32
	private String alias;//8
	private int mscId;
	private long vpnId;
	private String sn; //32
	private String company;//32
	private String type;//16

	private int enabled; //1
	private int shortData;
	private int fullDuple;
	private int radioType;
	private int anycall;
	
	private int saId; //4
	private int iaId;
	private int vaId;
	private int rugId;
	
	private String packetData;
	private String ip;//20
	private int primaryTGId;
	private String ambienceMonitoring;
	private String ambienceInitiation;
	private String directDial;//16
	private String pstnAccess;
	private String pabxAccess;
	private String clir;
	private String clirOverride;
	private String killed;
	private String msType;
	public RadioUserStruct(){}
	
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
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public int getShortData() {
		return shortData;
	}
	public void setShortData(int shortData) {
		this.shortData = shortData;
	}
	public int getFullDuple() {
		return fullDuple;
	}
	public void setFullDuple(int fullDuple) {
		this.fullDuple = fullDuple;
	}
	public int getRadioType() {
		return radioType;
	}
	public void setRadioType(int radioType) {
		this.radioType = radioType;
	}
	public int getAnycall() {
		return anycall;
	}
	public void setAnycall(int anycall) {
		this.anycall = anycall;
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
	public int getRugId() {
		return rugId;
	}
	public void setRugId(int rugId) {
		this.rugId = rugId;
	}
	public String getPacketData() {
		return packetData;
	}
	public void setPacketData(String packetData) {
		this.packetData = packetData;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPrimaryTGId() {
		return primaryTGId;
	}
	public void setPrimaryTGId(int primaryTGId) {
		this.primaryTGId = primaryTGId;
	}
	public String getAmbienceMonitoring() {
		return ambienceMonitoring;
	}
	public void setAmbienceMonitoring(String ambienceMonitoring) {
		this.ambienceMonitoring = ambienceMonitoring;
	}
	public String getAmbienceInitiation() {
		return ambienceInitiation;
	}
	public void setAmbienceInitiation(String ambienceInitiation) {
		this.ambienceInitiation = ambienceInitiation;
	}
	public String getDirectDial() {
		return directDial;
	}
	public void setDirectDial(String directDial) {
		this.directDial = directDial;
	}
	public String getPstnAccess() {
		return pstnAccess;
	}
	public void setPstnAccess(String pstnAccess) {
		this.pstnAccess = pstnAccess;
	}
	public String getPabxAccess() {
		return pabxAccess;
	}
	public void setPabxAccess(String pabxAccess) {
		this.pabxAccess = pabxAccess;
	}
	public String getClir() {
		return clir;
	}
	public void setClir(String clir) {
		this.clir = clir;
	}
	public String getClirOverride() {
		return clirOverride;
	}
	public void setClirOverride(String clirOverride) {
		this.clirOverride = clirOverride;
	}
	public String getKilled() {
		return killed;
	}
	public void setKilled(String killed) {
		this.killed = killed;
	}
	public String getMsType() {
		return msType;
	}
	public void setMsType(String msType) {
		this.msType = msType;
	}

	@Override
	public String toString() {
		return "RadioUserStruct [message=" + message + ", operation="
				+ operation + ", id=" + id + ", name=" + name + ", alias="
				+ alias + ", mscId=" + mscId + ", vpnId=" + vpnId + ", sn="
				+ sn + ", company=" + company + ", type=" + type + ", enabled="
				+ enabled + ", shortData=" + shortData + ", fullDuple="
				+ fullDuple + ", radioType=" + radioType + ", anycall="
				+ anycall + ", saId=" + saId + ", iaId=" + iaId + ", vaId="
				+ vaId + ", rugId=" + rugId + ", packetData=" + packetData
				+ ", ip=" + ip + ", primaryTGId=" + primaryTGId
				+ ", ambienceMonitoring=" + ambienceMonitoring
				+ ", ambienceInitiation=" + ambienceInitiation
				+ ", directDial=" + directDial + ", pstnAccess=" + pstnAccess
				+ ", pabxAccess=" + pabxAccess + ", clir=" + clir
				+ ", clirOverride=" + clirOverride + ", killed=" + killed
				+ ", msType=" + msType + "]";
	}
	
	
}
