package xh.mybatis.bean;

public class CheckRoomEquBean {
	private String check_type;
	private String check_child;
	private String check_tag;
	private String bsId="0";
	private float score;
	private int fault_time=0;
	private String detail;
	private String check_date;
	private String period;
	public String getCheck_type() {
		return check_type;
	}
	public void setCheck_type(String check_type) {
		this.check_type = check_type;
	}
	public String getCheck_child() {
		return check_child;
	}
	public void setCheck_child(String check_child) {
		this.check_child = check_child;
	}
	public String getBsId() {
		return bsId;
	}
	public void setBsId(String bsId) {
		this.bsId = bsId;
	}
	
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getCheck_date() {
		return check_date;
	}
	public void setCheck_date(String check_date) {
		this.check_date = check_date;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getCheck_tag() {
		return check_tag;
	}
	public void setCheck_tag(String check_tag) {
		this.check_tag = check_tag;
	}
	public int getFault_time() {
		return fault_time;
	}
	public void setFault_time(int fault_time) {
		this.fault_time = fault_time;
	}
	@Override
	public String toString() {
		return "CheckRoomEquBean [check_type=" + check_type + ", check_child="
				+ check_child + ", check_tag=" + check_tag + ", bsId=" + bsId
				+ ", score=" + score + ", fault_time=" + fault_time
				+ ", detail=" + detail + ", check_date=" + check_date
				+ ", period=" + period + "]";
	}
	
	

}
