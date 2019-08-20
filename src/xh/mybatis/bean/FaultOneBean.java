package xh.mybatis.bean;

public class FaultOneBean {
	 private int  id;
	 private String  produce_time;
	 private String  recovery_time;
	 private String  total_time;
	 private String  reason;
	 private String  fault_type;
	 private String  type;
	 private String  area;
	 private String  note;
	 private String period;
	 private String  record_person;
	 
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduce_time() {
		return produce_time;
	}
	public void setProduce_time(String produce_time) {
		this.produce_time = produce_time;
	}
	public String getRecovery_time() {
		return recovery_time;
	}
	public void setRecovery_time(String recovery_time) {
		this.recovery_time = recovery_time;
	}
	public String getTotal_time() {
		return total_time;
	}
	public void setTotal_time(String total_time) {
		this.total_time = total_time;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getFault_type() {
		return fault_type;
	}
	public void setFault_type(String fault_type) {
		this.fault_type = fault_type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getRecord_person() {
		return record_person;
	}
	public void setRecord_person(String record_person) {
		this.record_person = record_person;
	}
	@Override
	public String toString() {
		return "FaultOneBean [id=" + id + ", produce_time=" + produce_time
				+ ", recovery_time=" + recovery_time + ", total_time="
				+ total_time + ", reason=" + reason + ", fault_type="
				+ fault_type + ", type=" + type + ", area=" + area + ", note="
				+ note + ", record_person=" + record_person + "]";
	}
	 
	 

}
