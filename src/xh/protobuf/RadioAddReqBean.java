package xh.protobuf;

//新增终端信息请求
/*message RadioAddReq{   
	required String RadioID	= 1;       
	required String RadioReferenceID	= 2;  
	required String RadioSerialNumber	= 3; 
	required String SecurityGroup	= 4; 
}*/
public class RadioAddReqBean {
	private String RadioID;       
	private String RadioReferenceID	; 
	private String RadioSerialNumber; 
	private String SecurityGroup;
	
	
	public String getRadioID() {
		return RadioID;
	}


	public void setRadioID(String radioID) {
		RadioID = radioID;
	}


	public String getRadioReferenceID() {
		return RadioReferenceID;
	}


	public void setRadioReferenceID(String radioReferenceID) {
		RadioReferenceID = radioReferenceID;
	}


	public String getRadioSerialNumber() {
		return RadioSerialNumber;
	}


	public void setRadioSerialNumber(String radioSerialNumber) {
		RadioSerialNumber = radioSerialNumber;
	}


	public String getSecurityGroup() {
		return SecurityGroup;
	}


	public void setSecurityGroup(String securityGroup) {
		SecurityGroup = securityGroup;
	}


	@Override
	public String toString() {
		return "RadioAddReqBean [RadioID=" + RadioID + ", RadioReferenceID="
				+ RadioReferenceID + ", RadioSerialNumber=" + RadioSerialNumber
				+ ", SecurityGroup=" + SecurityGroup + "]";
	}
	
	

}
