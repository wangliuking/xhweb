package xh.mybatis.bean;

public class bsLinkConfigBean {
	  private int id;
	  private int bsId;
	  private String tag;
	  private String is_open;
	  private String operator;
	  private String equipment_model;
	  private String bs_net;
	  private String bs_net_port;
	  private String msc_net;
	  private String msc_net_port;
	  private String regulation_number;
	  private String masterIp;
	  private String cameraIp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getIs_open() {
		return is_open;
	}
	public void setIs_open(String is_open) {
		this.is_open = is_open;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getEquipment_model() {
		return equipment_model;
	}
	public void setEquipment_model(String equipment_model) {
		this.equipment_model = equipment_model;
	}
	public String getBs_net() {
		return bs_net;
	}
	public void setBs_net(String bs_net) {
		this.bs_net = bs_net;
	}
	public String getBs_net_port() {
		return bs_net_port;
	}
	public void setBs_net_port(String bs_net_port) {
		this.bs_net_port = bs_net_port;
	}
	public String getMsc_net() {
		return msc_net;
	}
	public void setMsc_net(String msc_net) {
		this.msc_net = msc_net;
	}
	public String getMsc_net_port() {
		return msc_net_port;
	}
	public void setMsc_net_port(String msc_net_port) {
		this.msc_net_port = msc_net_port;
	}
	public String getRegulation_number() {
		return regulation_number;
	}
	public void setRegulation_number(String regulation_number) {
		this.regulation_number = regulation_number;
	}
	public String getMasterIp() {
		return masterIp;
	}
	public void setMasterIp(String masterIp) {
		this.masterIp = masterIp;
	}
	public String getCameraIp() {
		return cameraIp;
	}
	public void setCameraIp(String cameraIp) {
		this.cameraIp = cameraIp;
	}
	@Override
	public String toString() {
		return "bsLinkConfigBean [id=" + id + ", bsId=" + bsId + ", tag=" + tag
				+ ", is_open=" + is_open + ", operator=" + operator
				+ ", equipment_model=" + equipment_model + ", bs_net=" + bs_net
				+ ", bs_net_port=" + bs_net_port + ", msc_net=" + msc_net
				+ ", msc_net_port=" + msc_net_port + ", regulation_number="
				+ regulation_number + ", masterIp=" + masterIp + ", cameraIp="
				+ cameraIp + "]";
	}
	  
	  

	
}
