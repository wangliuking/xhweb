package xh.org.socket;
/************************************************************************
 *RadioUserAttr 通话播组数据结构体
 * *********************************************************************/

public class RadioUserAttrStruct {
	
	private int operation;
	
	private int id; //4
	private String  name;//[16];
	private int ssId;    //2
	private int dispatchPriority;  //1
	
	
	private int pccEnabled; //1
	private int tgEnabled;
	private int mgEnabled;  //1
	private int mgEmergencyEnabled;
	private int dispatchPreempt;
	private int allSitesAllowed;
	private int calledPreempt;   //  29
	private int userGroup;  //1
	private int emergIndCallEnabled;  //1
	private int emergGroupCallEnabled;  //1
	
	
	//新增
	private String ssName;   //16
	
	public RadioUserAttrStruct(){}
	
	
	
	public int getUserGroup() {
		return userGroup;
	}



	public void setUserGroup(int userGroup) {
		this.userGroup = userGroup;
	}



	public int getEmergIndCallEnabled() {
		return emergIndCallEnabled;
	}



	public void setEmergIndCallEnabled(int emergIndCallEnabled) {
		this.emergIndCallEnabled = emergIndCallEnabled;
	}



	public int getEmergGroupCallEnabled() {
		return emergGroupCallEnabled;
	}



	public void setEmergGroupCallEnabled(int emergGroupCallEnabled) {
		this.emergGroupCallEnabled = emergGroupCallEnabled;
	}



	public int getMgEnabled() {
		return mgEnabled;
	}


	public void setMgEnabled(int mgEnabled) {
		this.mgEnabled = mgEnabled;
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
	public int getSsId() {
		return ssId;
	}
	public void setSsId(int ssId) {
		this.ssId = ssId;
	}
	public int getDispatchPriority() {
		return dispatchPriority;
	}
	public void setDispatchPriority(int dispatchPriority) {
		this.dispatchPriority = dispatchPriority;
	}
	public int getPccEnabled() {
		return pccEnabled;
	}
	public void setPccEnabled(int pccEnabled) {
		this.pccEnabled = pccEnabled;
	}
	public int getTgEnabled() {
		return tgEnabled;
	}
	public void setTgEnabled(int tgEnabled) {
		this.tgEnabled = tgEnabled;
	}
	public int getMgEmergencyEnabled() {
		return mgEmergencyEnabled;
	}
	public void setMgEmergencyEnabled(int mgEmergencyEnabled) {
		this.mgEmergencyEnabled = mgEmergencyEnabled;
	}
	public int getDispatchPreempt() {
		return dispatchPreempt;
	}
	public void setDispatchPreempt(int dispatchPreempt) {
		this.dispatchPreempt = dispatchPreempt;
	}
	public int getAllSitesAllowed() {
		return allSitesAllowed;
	}
	public void setAllSitesAllowed(int allSitesAllowed) {
		this.allSitesAllowed = allSitesAllowed;
	}
	public int getCalledPreempt() {
		return calledPreempt;
	}
	public void setCalledPreempt(int calledPreempt) {
		this.calledPreempt = calledPreempt;
	}
	public String getSsName() {
		return ssName;
	}
	public void setSsName(String ssName) {
		this.ssName = ssName;
	}



	@Override
	public String toString() {
		return "RadioUserAttrStruct [operation=" + operation + ", id=" + id
				+ ", name=" + name + ", ssId=" + ssId + ", dispatchPriority="
				+ dispatchPriority + ", pccEnabled=" + pccEnabled
				+ ", tgEnabled=" + tgEnabled + ", mgEnabled=" + mgEnabled
				+ ", mgEmergencyEnabled=" + mgEmergencyEnabled
				+ ", dispatchPreempt=" + dispatchPreempt + ", allSitesAllowed="
				+ allSitesAllowed + ", calledPreempt=" + calledPreempt
				+ ", userGroup=" + userGroup + ", emergIndCallEnabled="
				+ emergIndCallEnabled + ", emergGroupCallEnabled="
				+ emergGroupCallEnabled + ", ssName=" + ssName + "]";
	}
	
	
	


}
