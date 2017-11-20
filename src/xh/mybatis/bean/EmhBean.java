package xh.mybatis.bean;

public class EmhBean {
	/*private int id;
	private int dev_id;
	private int max_range_unitid;
	private int status;
	private int warning_status;
	private int is_on_off;
	private int on_unitid;
	private int off_unitid;
	private int on_off_waring;
	private int is_write_old_monitor;
	private int up_index;
	private int deleted;
	private String dev_name;
	private String sig_value;
	private String min_warning;
	private String warning;
	private String min_range;
	private String max_range;
	private String 	x_num;
	private String add_num;
	private String last_conn_time;
	private String createtime;
	private String modifydate;*/
	
	private int id;
	private int bsId;
	private String fsuId;
	private String deviceId;
	private String deviceName;
	private String singleId;
	private String singleType;
	private String singleValue;
	private String level;
	private String updateTime;
	
	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
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
	public String getSingleId() {
		return singleId;
	}
	public void setSingleId(String singleId) {
		this.singleId = singleId;
	}
	public String getSingleType() {
		return singleType;
	}
	public void setSingleType(String singleType) {
		this.singleType = singleType;
	}
	public String getSingleValue() {
		return singleValue;
	}
	public void setSingleValue(String singleValue) {
		this.singleValue = singleValue;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	
	
	
	
	

}
