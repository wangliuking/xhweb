package xh.mybatis.bean;

public class BsstationBean {
	private int bsId;
	private String name;
	private int type;
	private int level;
	private int status;
	private int openen;
	private String lat;
	private String lng;
	private String height;
	private String createTime;
	
	//新增字段
	private int statusCount;
	private int chnumber;
	private int gpsLineNum;
	private String power;
	private String carrier;
	private String carrierName;
	private String carrierLink;
	private String lineHeight;
	private String address;
	private String contact;
	private String tel;
	private String ip;
	
	public BsstationBean() {
		// TODO Auto-generated constructor stub
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getStatusCount() {
		return statusCount;
	}

	public void setStatusCount(int statusCount) {
		this.statusCount = statusCount;
	}

	public int getChnumber() {
		return chnumber;
	}

	public void setChnumber(int chnumber) {
		this.chnumber = chnumber;
	}

	public int getGpsLineNum() {
		return gpsLineNum;
	}

	public void setGpsLineNum(int gpsLineNum) {
		this.gpsLineNum = gpsLineNum;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getCarrierLink() {
		return carrierLink;
	}

	public void setCarrierLink(String carrierLink) {
		this.carrierLink = carrierLink;
	}

	public String getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(String lineHeight) {
		this.lineHeight = lineHeight;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	



	public int getOpenen() {
		return openen;
	}

	public void setOpenen(int openen) {
		this.openen = openen;
	}

	@Override
	public String toString() {
		return "BsstationBean [bsId=" + bsId + ", name=" + name + ", type="
				+ type + ", level=" + level + ", status=" + status
				+ ", openen=" + openen + ", lat=" + lat + ", lng=" + lng
				+ ", height=" + height + ", createTime=" + createTime
				+ ", statusCount=" + statusCount + ", chnumber=" + chnumber
				+ ", gpsLineNum=" + gpsLineNum + ", power=" + power
				+ ", carrier=" + carrier + ", carrierName=" + carrierName
				+ ", carrierLink=" + carrierLink + ", lineHeight=" + lineHeight
				+ ", address=" + address + ", contact=" + contact + ", tel="
				+ tel + ", ip=" + ip + "]";
	}
	

}
