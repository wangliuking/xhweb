package xh.mybatis.bean;

public class MplanBean {
	private int id;
	private String mplan_type;
	private String mplan_content;
	private String plan_time;
	private String complete_time;
	private String result;
	private String reason;
	private String note;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMplan_type() {
		return mplan_type;
	}
	public void setMplan_type(String mplan_type) {
		this.mplan_type = mplan_type;
	}
	public String getMplan_content() {
		return mplan_content;
	}
	public void setMplan_content(String mplan_content) {
		this.mplan_content = mplan_content;
	}
	public String getPlan_time() {
		return plan_time;
	}
	public void setPlan_time(String plan_time) {
		this.plan_time = plan_time;
	}
	public String getComplete_time() {
		return complete_time;
	}
	public void setComplete_time(String complete_time) {
		this.complete_time = complete_time;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "MplanBean [id=" + id + ", mplan_type=" + mplan_type
				+ ", mplan_content=" + mplan_content + ", plan_time="
				+ plan_time + ", complete_time=" + complete_time + ", result="
				+ result + ", reason=" + reason + ", note=" + note + "]";
	}
	
	

}
