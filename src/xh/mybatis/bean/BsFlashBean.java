package xh.mybatis.bean;
 
public class BsFlashBean {
	private String bsId;
	private String name;
	private String time_break;
	private String time_restore;
	private String emsTime_break;
	private String sumtime;
	private String note="基站闪断";
	private String reason;
	private int period;
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
	public String getTime_break() {
		return time_break;
	}
	public void setTime_break(String time_break) {
		this.time_break = time_break;
	}
	public String getTime_restore() {
		return time_restore;
	}
	public void setTime_restore(String time_restore) {
		this.time_restore = time_restore;
	}
	public String getSumtime() {
		return sumtime;
	}
	public void setSumtime(String sumtime) {
		this.sumtime = sumtime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getEmsTime_break() {
		return emsTime_break;
	}
	public void setEmsTime_break(String emsTime_break) {
		this.emsTime_break = emsTime_break;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "BsFlashBean [bsId=" + bsId + ", name=" + name + ", time_break="
				+ time_break + ", time_restore=" + time_restore
				+ ", emsTime_break=" + emsTime_break + ", sumtime=" + sumtime
				+ ", note=" + note + ", reason=" + reason + ", period="
				+ period + "]";
	}
	
	
	

}
