package xh.org.socket;

public class savalidsiteStruct {
	
	
	private int operation;//4
	private int id;//4
	private int saId;//4
	private String saName;//16
	private int mscId;//4
	private String mscName;//16
	private int bsId;//4
	private String bsName;//16
	private int required;//1
	
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
	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
	}
	public String getBsName() {
		return bsName;
	}
	public void setBsName(String bsName) {
		this.bsName = bsName;
	}
	public int getRequired() {
		return required;
	}
	public void setRequired(int required) {
		this.required = required;
	}
	@Override
	public String toString() {
		return "savalidsiteStruct [operation=" + operation + ", id=" + id
				+ ", saId=" + saId + ", saName=" + saName + ", mscId=" + mscId
				+ ", mscName=" + mscName + ", bsId=" + bsId + ", bsName="
				+ bsName + ", required=" + required + "]";
	}
	
	

}
