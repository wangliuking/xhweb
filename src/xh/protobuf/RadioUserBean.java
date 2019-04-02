package xh.protobuf;

//新增终端用户信息请求
/*message RadioUserAddReq{  
	private String RadioID	= 1;       
	private String RadioUserAlias	= 2;  
	private String SecurityGroup	= 3; 
	private String RadioUserCapabilityProfileAlias	= 4;
	private String UserEnabled 	= 5; 
	private String InterconnectEnabled	= 6; 
	private String PacketDataEnabled	= 7; 
	private String ShortDataEnabled	= 8; 
	private String FullDuplexEnabled	= 9;  
}*/ 
public class RadioUserBean {
	private String RadioID	;       
	private String RadioUserAlias	;  
	private String SecurityGroup	; 
	private String RadioUserCapabilityProfileAlias	;
	private String UserEnabled ; 
	private String InterconnectEnabled	; 
	private String PacketDataEnabled	; 
	private String ShortDataEnabled	; 
	private String FullDuplexEnabled	;
	public String getRadioID() {
		return RadioID;
	}
	public void setRadioID(String radioID) {
		RadioID = radioID;
	}
	public String getRadioUserAlias() {
		return RadioUserAlias;
	}
	public void setRadioUserAlias(String radioUserAlias) {
		RadioUserAlias = radioUserAlias;
	}
	public String getSecurityGroup() {
		return SecurityGroup;
	}
	public void setSecurityGroup(String securityGroup) {
		SecurityGroup = securityGroup;
	}
	public String getRadioUserCapabilityProfileAlias() {
		return RadioUserCapabilityProfileAlias;
	}
	public void setRadioUserCapabilityProfileAlias(
			String radioUserCapabilityProfileAlias) {
		RadioUserCapabilityProfileAlias = radioUserCapabilityProfileAlias;
	}
	public String getUserEnabled() {
		return UserEnabled;
	}
	public void setUserEnabled(String userEnabled) {
		UserEnabled = userEnabled;
	}
	public String getInterconnectEnabled() {
		return InterconnectEnabled;
	}
	public void setInterconnectEnabled(String interconnectEnabled) {
		InterconnectEnabled = interconnectEnabled;
	}
	public String getPacketDataEnabled() {
		return PacketDataEnabled;
	}
	public void setPacketDataEnabled(String packetDataEnabled) {
		PacketDataEnabled = packetDataEnabled;
	}
	public String getShortDataEnabled() {
		return ShortDataEnabled;
	}
	public void setShortDataEnabled(String shortDataEnabled) {
		ShortDataEnabled = shortDataEnabled;
	}
	public String getFullDuplexEnabled() {
		return FullDuplexEnabled;
	}
	public void setFullDuplexEnabled(String fullDuplexEnabled) {
		FullDuplexEnabled = fullDuplexEnabled;
	}
	@Override
	public String toString() {
		return "RadioUserAddReqBean [RadioID=" + RadioID + ", RadioUserAlias="
				+ RadioUserAlias + ", SecurityGroup=" + SecurityGroup
				+ ", RadioUserCapabilityProfileAlias="
				+ RadioUserCapabilityProfileAlias + ", UserEnabled="
				+ UserEnabled + ", InterconnectEnabled=" + InterconnectEnabled
				+ ", PacketDataEnabled=" + PacketDataEnabled
				+ ", ShortDataEnabled=" + ShortDataEnabled
				+ ", FullDuplexEnabled=" + FullDuplexEnabled + "]";
	}  
	
	
	
	

}
