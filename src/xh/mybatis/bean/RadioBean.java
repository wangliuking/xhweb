package xh.mybatis.bean;

import xh.func.plugin.FunUtil;

public class RadioBean {
	private Integer id;
	private String RadioID;
	private String RadioSerialNumber;
	private String RadioReferenceID;
	private String SecurityGroup;
	private String callId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRadioID() {
		return RadioID;
	}
	public void setRadioID(String radioID) {
		RadioID = radioID;
	}
	public String getRadioSerialNumber() {
		return RadioSerialNumber;
	}
	public void setRadioSerialNumber(String radioSerialNumber) {
		RadioSerialNumber = radioSerialNumber;
	}
	public String getRadioReferenceID() {
		return RadioReferenceID;
	}
	public void setRadioReferenceID(String radioReferenceID) {
		RadioReferenceID = radioReferenceID;
	}
	public String getSecurityGroup() {
		return SecurityGroup;
	}
	public void setSecurityGroup(String securityGroup) {
		SecurityGroup = securityGroup;
	}
	
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	@Override
	public String toString() {
		return "RadioBean [id=" + id + ", RadioID=" + RadioID
				+ ", RadioSerialNumber=" + RadioSerialNumber
				+ ", RadioReferenceID=" + RadioReferenceID + ", SecurityGroup="
				+ SecurityGroup + ", callId=" + callId + "]";
	}
	
	

}
