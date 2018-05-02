package xh.mybatis.bean;
 
public class BsstationBean {
	private String bsId;
	private String name;
	private int period;
	private String lat;
	private String lng;
	private String chnumber;
	private String number;
	/*private String gpsLineNum;*/
	private String power;
	/*private String carrier;
	private String carrierName;
	private String carrierLink;*/
	private String height;
	private String lineHeight;
	private String lineInstallType;
	private String address;
	private String contact;
	private String tel;
	private String ip;
	private String type;
	private String productor;
	private String deviceType;
	private String level;
	private String status="0";
	private String hometype;
	private String roomCharge;
	private String electricCharge;
	private String createTime;
	private String zone;
	private String envMonitor;
	private String conditionerCount;
	private String fireEquipment="0";
	private String generatorConfig="0";
	private String isGenerator="0";
	private String entryLimitType;
	private String transCount;
	private String comment;
	private String exist;
	
	private String weather;
	private String disasterPoint;
	private String stolenPoint;
	private String powerCut;
	private String residentialBuilding;
	

	/*
	 * private int type; private int level; private int status; private int
	 * openen; private String lat; private String lng; private String height;
	 * private String createTime;
	 * 
	 * //新增字段 private int statusCount; private int chnumber; private int
	 * gpsLineNum; private String power; private String carrier; private String
	 * carrierName; private String carrierLink; private String lineHeight;
	 * private String address; private String contact; private String tel;
	 * private String ip;
	 */

	public BsstationBean() {
		// TODO Auto-generated constructor stub
	}


	

	public String getBsId() {
		return bsId;
	}




	public void setBsId(String bsId) {
		this.bsId = bsId;
	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
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

	public String getChnumber() {
		return chnumber;
	}

	public void setChnumber(String chnumber) {
		this.chnumber = chnumber;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}


	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(String lineHeight) {
		this.lineHeight = lineHeight;
	}

	public String getLineInstallType() {
		return lineInstallType;
	}

	public void setLineInstallType(String lineInstallType) {
		this.lineInstallType = lineInstallType;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductor() {
		return productor;
	}

	public void setProductor(String productor) {
		this.productor = productor;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHometype() {
		return hometype;
	}

	public void setHometype(String hometype) {
		this.hometype = hometype;
	}

	public String getRoomCharge() {
		return roomCharge;
	}

	public void setRoomCharge(String roomCharge) {
		this.roomCharge = roomCharge;
	}

	public String getElectricCharge() {
		return electricCharge;
	}

	public void setElectricCharge(String electricCharge) {
		this.electricCharge = electricCharge;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getEnvMonitor() {
		return envMonitor;
	}

	public void setEnvMonitor(String envMonitor) {
		this.envMonitor = envMonitor;
	}

	public String getConditionerCount() {
		return conditionerCount;
	}

	public void setConditionerCount(String conditionerCount) {
		this.conditionerCount = conditionerCount;
	}

	public String getFireEquipment() {
		return fireEquipment;
	}

	public void setFireEquipment(String fireEquipment) {
		this.fireEquipment = fireEquipment;
	}

	public String getGeneratorConfig() {
		return generatorConfig;
	}

	public void setGeneratorConfig(String generatorConfig) {
		this.generatorConfig = generatorConfig;
	}

	public String getIsGenerator() {
		return isGenerator;
	}

	public void setIsGenerator(String isGenerator) {
		this.isGenerator = isGenerator;
	}

	public String getEntryLimitType() {
		return entryLimitType;
	}

	public void setEntryLimitType(String entryLimitType) {
		this.entryLimitType = entryLimitType;
	}

	public String getTransCount() {
		return transCount;
	}

	public void setTransCount(String transCount) {
		this.transCount = transCount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getExist() {
		return exist;
	}

	public void setExist(String exist) {
		this.exist = exist;
	}




	public String getWeather() {
		return weather;
	}




	public void setWeather(String weather) {
		this.weather = weather;
	}




	public String getDisasterPoint() {
		return disasterPoint;
	}




	public void setDisasterPoint(String disasterPoint) {
		this.disasterPoint = disasterPoint;
	}




	public String getStolenPoint() {
		return stolenPoint;
	}




	public void setStolenPoint(String stolenPoint) {
		this.stolenPoint = stolenPoint;
	}




	public String getPowerCut() {
		return powerCut;
	}




	public void setPowerCut(String powerCut) {
		this.powerCut = powerCut;
	}




	public String getResidentialBuilding() {
		return residentialBuilding;
	}




	public void setResidentialBuilding(String residentialBuilding) {
		this.residentialBuilding = residentialBuilding;
	}




	@Override
	public String toString() {
		return "BsstationBean [bsId=" + bsId + ", name=" + name + ", period="
				+ period + ", lat=" + lat + ", lng=" + lng + ", chnumber="
				+ chnumber + ", number=" + number + ", power=" + power
				+ ", height=" + height + ", lineHeight=" + lineHeight
				+ ", lineInstallType=" + lineInstallType + ", address="
				+ address + ", contact=" + contact + ", tel=" + tel + ", ip="
				+ ip + ", type=" + type + ", productor=" + productor
				+ ", deviceType=" + deviceType + ", level=" + level
				+ ", status=" + status + ", hometype=" + hometype
				+ ", roomCharge=" + roomCharge + ", electricCharge="
				+ electricCharge + ", createTime=" + createTime + ", zone="
				+ zone + ", envMonitor=" + envMonitor + ", conditionerCount="
				+ conditionerCount + ", fireEquipment=" + fireEquipment
				+ ", generatorConfig=" + generatorConfig + ", isGenerator="
				+ isGenerator + ", entryLimitType=" + entryLimitType
				+ ", transCount=" + transCount + ", comment=" + comment
				+ ", exist=" + exist + ", weather=" + weather
				+ ", disasterPoint=" + disasterPoint + ", stolenPoint="
				+ stolenPoint + ", powerCut=" + powerCut
				+ ", residentialBuilding=" + residentialBuilding + "]";
	}




	

}
