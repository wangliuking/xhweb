package xh.mybatis.bean;

public class CheckMoneyBean {
	private String check_type;
	private String check_child;
	private String check_tag;
	private String bsId="0";
	private float money;
	private String detail;
	private String check_date;
	private String check_note;
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
	public String getCheck_tag() {
		return check_tag;
	}
	public void setCheck_tag(String check_tag) {
		this.check_tag = check_tag;
	}
	public String getBsId() {
		return bsId;
	}
	public void setBsId(String bsId) {
		this.bsId = bsId;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
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
	
	public String getCheck_note() {
		return check_note;
	}
	public void setCheck_note(String check_note) {
		this.check_note = check_note;
	}
	@Override
	public String toString() {
		return "CheckMoneyBean [check_type=" + check_type + ", check_child="
				+ check_child + ", check_tag=" + check_tag + ", bsId=" + bsId
				+ ", money=" + money + ", detail=" + detail + ", check_date="
				+ check_date + ", check_note=" + check_note + ", period="
				+ period + "]";
	}
	
	
}
