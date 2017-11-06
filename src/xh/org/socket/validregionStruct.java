package xh.org.socket;

public class validregionStruct {
	
	private int operation;
	private int id;//4
	private int saId;//	0x02	4	1~2000
	private String saName;//	0x03	16	1~16之间的字符串，不允许出现|@_\”?&’%字符
	private int mscId;//	0x04	4	1~255
	private String mscName;//	0x05	16	1~16之间的字符串，不允许出现|@_\”?&’%字符
	private int type;//	0x06	4	0 - 允许, 1 - 屏蔽，默认允许
	private int neType;//	0x01	4	2代表交换，6代表isgw
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSaId() {
		return saId;
	}
	public void setSaId(int saId) {
		this.saId = saId;
	}
	public String getSaName() {
		return saName;
	}
	public void setSaName(String saName) {
		this.saName = saName;
	}
	public int getMscId() {
		return mscId;
	}
	public void setMscId(int mscId) {
		this.mscId = mscId;
	}
	public String getMscName() {
		return mscName;
	}
	public void setMscName(String mscName) {
		this.mscName = mscName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNeType() {
		return neType;
	}
	public void setNeType(int neType) {
		this.neType = neType;
	}
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	
	

}
