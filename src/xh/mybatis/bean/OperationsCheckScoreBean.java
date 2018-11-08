package xh.mybatis.bean;

public class OperationsCheckScoreBean {
	private String check_type;
	private String check_tag;
	private float score;
	private String check_note;
	private String check_month;
	public String getCheck_type() {
		return check_type;
	}
	public void setCheck_type(String check_type) {
		this.check_type = check_type;
	}
	public String getCheck_tag() {
		return check_tag;
	}
	public void setCheck_tag(String check_tag) {
		this.check_tag = check_tag;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getCheck_note() {
		return check_note;
	}
	public void setCheck_note(String check_note) {
		this.check_note = check_note;
	}
	public String getCheck_month() {
		return check_month;
	}
	public void setCheck_month(String check_month) {
		this.check_month = check_month;
	}
	@Override
	public String toString() {
		return "OperationsCheckScoreBean [check_type=" + check_type
				+ ", check_tag=" + check_tag + ", score=" + score
				+ ", check_note=" + check_note + ", check_month=" + check_month
				+ "]";
	}
	
	

}
