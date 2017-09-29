package xh.mybatis.bean;

public class AssetTransferBean {
	private int id;
	private int type;
	private String name;
	private String model;
	private String serialNumber;
	private String price;
	private String user;
	private String note;
	private String createTime;
	
	private int statusCount;
	
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
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
	@Override
	public String toString() {
		return "AssetTransferBean [id=" + id + ", type=" + type + ", name=" + name
				+ ", model=" + model + ", serialNumber=" + serialNumber
				+ ", price=" + price + ", user=" + user + ", note=" + note + ", createTime="
				+ createTime + ", statusCount=" + statusCount + "]";
	}
	
	
	
	

}
