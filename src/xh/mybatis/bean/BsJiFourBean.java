package xh.mybatis.bean;

public class BsJiFourBean {
	
	private int bsId;
	private String name;
	private String fsuId;
	private String deviceId;
	private String updateTime;
	private String singleId;
	private int singleValue;
	private String description;
	private int neType;
	
	
	
	public int getNeType() {
		return neType;
	}
	public void setNeType(int neType) {
		this.neType = neType;
	}
	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFsuId() {
		return fsuId;
	}
	public void setFsuId(String fsuId) {
		this.fsuId = fsuId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getSingleValue() {
		return singleValue;
	}
	public void setSingleValue(int singleValue) {
		this.singleValue = singleValue;
	}
	public String getSingleId() {
		return singleId;
	}
	public void setSingleId(String singleId) {
		this.singleId = singleId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "BsJiFourBean [bsId=" + bsId + ", name=" + name + ", fsuId="
				+ fsuId + ", deviceId=" + deviceId + ", updateTime="
				+ updateTime + ", singleId=" + singleId + ", singleValue="
				+ singleValue + ", description=" + description + "]";
	}
	
	

}
