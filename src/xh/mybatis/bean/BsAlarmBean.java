package xh.mybatis.bean;
 
import java.io.Serializable;


public class BsAlarmBean implements Serializable{
	/**
	 * 基站告警表 实体类
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int bsId;
	private int alarmLevel;
	private int alarmType;
	private String content;
	private int status;
	private String createTime;
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
	public int getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public int getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "BsAlarmBean [id=" + id + ", bsId=" + bsId + ", alarmLevel="
				+ alarmLevel + ", alarmType=" + alarmType + ", content="
				+ content + ", status=" + status + ", createTime=" + createTime
				+ "]";
	}


}
