package xh.mybatis.bean;

public class AssetScrapInfoBean {
	private int id;
	private int type;
	private String name;
	private String model;
	private String serialNumber;
	private String price;
	
	private Float ratValue;
	private int number=1;
	private int from;
	private int status;
	private String note;
	private String createTime;
	private int isLock=0;
	private String addUser;
	private String checkTime;
	private String checkResult;
	private String checkUser;
	
	private  String applyTag;
	
	
	
	private int statusCount;
	
	private String proName;
	private String priceType;
	private String buyTime;
	
	
	
	
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public String getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	public String getApplyTag() {
		return applyTag;
	}
	public void setApplyTag(String applyTag) {
		this.applyTag = applyTag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	
	public Float getRatValue() {
		return ratValue;
	}
	public void setRatValue(Float ratValue) {
		this.ratValue = ratValue;
	}
	
	public int getIsLock() {
		return isLock;
	}
	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	
	@Override
	public String toString() {
		return "AssetScrapInfoBean [id=" + id + ", type=" + type + ", name="
				+ name + ", model=" + model + ", serialNumber=" + serialNumber
				+ ", price=" + price + ", ratValue=" + ratValue + ", number="
				+ number + ", from=" + from + ", status=" + status + ", note="
				+ note + ", createTime=" + createTime + ", isLock=" + isLock
				+ ", addUser=" + addUser + ", checkTime=" + checkTime
				+ ", checkResult=" + checkResult + ", checkUser=" + checkUser
				+ ", applyTag=" + applyTag + ", statusCount=" + statusCount
				+ ", proName=" + proName + ", priceType=" + priceType
				+ ", buyTime=" + buyTime + "]";
	}
	
	
	
	

}
