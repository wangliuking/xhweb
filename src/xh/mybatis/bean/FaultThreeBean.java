package xh.mybatis.bean;

public class FaultThreeBean {
	private int id;
	private int fault_id;
	private String period;
	private String level;
	private String name;
	private String bsId;
	private String time;
	private String fault_from;
	private String reason;
	private String nowDeal;
	private String dealResult;
	private String faultRecoveryTime;
	private String faultTimeTotal;
	private String content;
	private String faultHandlePerson;
	private String faultRecordPerson;
	private String fault_type;

	private String checkTag;
	private String self_recovery;
	private String handle_detail;
	private String reponse_part;
	private String order_wh;
	private String order_dx;
	private String order_yd;
	private String send_order_time;
	private String recv_order_time;
	private long recv_order_use_time;
	private long recv_order_cs;
	private long handle_order_user_time;
	private long handle_order_cs;
	
	private String receipt_order_time;

	public String getReceipt_order_time() {
		return receipt_order_time;
	}

	public void setReceipt_order_time(String receipt_order_time) {
		this.receipt_order_time = receipt_order_time;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBsId() {
		return bsId;
	}

	public void setBsId(String bsId) {
		this.bsId = bsId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFault_from() {
		return fault_from;
	}

	public void setFault_from(String fault_from) {
		this.fault_from = fault_from;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getNowDeal() {
		return nowDeal;
	}

	public void setNowDeal(String nowDeal) {
		this.nowDeal = nowDeal;
	}

	public String getDealResult() {
		return dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

	public String getFaultRecoveryTime() {
		return faultRecoveryTime;
	}

	public void setFaultRecoveryTime(String faultRecoveryTime) {
		this.faultRecoveryTime = faultRecoveryTime;
	}

	public String getFaultTimeTotal() {
		return faultTimeTotal;
	}

	public String getCheckTag() {
		return checkTag;
	}

	public String getSelf_recovery() {
		return self_recovery;
	}

	public String getHandle_detail() {
		return handle_detail;
	}

	public String getReponse_part() {
		return reponse_part;
	}

	public String getOrder_wh() {
		return order_wh;
	}

	public String getOrder_dx() {
		return order_dx;
	}

	public String getOrder_yd() {
		return order_yd;
	}

	public String getSend_order_time() {
		return send_order_time;
	}

	public String getRecv_order_time() {
		return recv_order_time;
	}

	
	public void setFaultTimeTotal(String faultTimeTotal) {
		this.faultTimeTotal = faultTimeTotal;
	}

	public void setCheckTag(String checkTag) {
		this.checkTag = checkTag;
	}

	public void setSelf_recovery(String self_recovery) {
		this.self_recovery = self_recovery;
	}

	public void setHandle_detail(String handle_detail) {
		this.handle_detail = handle_detail;
	}

	public void setReponse_part(String reponse_part) {
		this.reponse_part = reponse_part;
	}

	public void setOrder_wh(String order_wh) {
		this.order_wh = order_wh;
	}

	public void setOrder_dx(String order_dx) {
		this.order_dx = order_dx;
	}

	public void setOrder_yd(String order_yd) {
		this.order_yd = order_yd;
	}

	public void setSend_order_time(String send_order_time) {
		this.send_order_time = send_order_time;
	}

	public void setRecv_order_time(String recv_order_time) {
		this.recv_order_time = recv_order_time;
	}

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFaultHandlePerson() {
		return faultHandlePerson;
	}

	public void setFaultHandlePerson(String faultHandlePerson) {
		this.faultHandlePerson = faultHandlePerson;
	}

	public String getFaultRecordPerson() {
		return faultRecordPerson;
	}

	public void setFaultRecordPerson(String faultRecordPerson) {
		this.faultRecordPerson = faultRecordPerson;
	}

	public String getFault_type() {
		return fault_type;
	}

	public int getFault_id() {
		return fault_id;
	}

	public void setFault_id(int fault_id) {
		this.fault_id = fault_id;
	}

	public void setFault_type(String fault_type) {
		this.fault_type = fault_type;
	}

	
	public long getRecv_order_use_time() {
		return recv_order_use_time;
	}

	public long getRecv_order_cs() {
		return recv_order_cs;
	}

	public long getHandle_order_user_time() {
		return handle_order_user_time;
	}

	public long getHandle_order_cs() {
		return handle_order_cs;
	}

	public void setRecv_order_use_time(long recv_order_use_time) {
		this.recv_order_use_time = recv_order_use_time;
	}

	public void setRecv_order_cs(long recv_order_cs) {
		this.recv_order_cs = recv_order_cs;
	}

	public void setHandle_order_user_time(long handle_order_user_time) {
		this.handle_order_user_time = handle_order_user_time;
	}

	public void setHandle_order_cs(long handle_order_cs) {
		this.handle_order_cs = handle_order_cs;
	}

	@Override
	public String toString() {
		return "FaultThreeBean [id=" + id + ", fault_id=" + fault_id
				+ ", period=" + period + ", level=" + level + ", name=" + name
				+ ", bsId=" + bsId + ", time=" + time + ", fault_from="
				+ fault_from + ", reason=" + reason + ", nowDeal=" + nowDeal
				+ ", dealResult=" + dealResult + ", faultRecoveryTime="
				+ faultRecoveryTime + ", faultTimeTotal=" + faultTimeTotal
				+ ", content=" + content + ", faultHandlePerson="
				+ faultHandlePerson + ", faultRecordPerson="
				+ faultRecordPerson + ", fault_type=" + fault_type
				+ ", checkTag=" + checkTag + ", self_recovery=" + self_recovery
				+ ", handle_detail=" + handle_detail + ", reponse_part="
				+ reponse_part + ", order_wh=" + order_wh + ", order_dx="
				+ order_dx + ", order_yd=" + order_yd + ", send_order_time="
				+ send_order_time + ", recv_order_time=" + recv_order_time
				+ ", recv_order_use_time=" + recv_order_use_time
				+ ", recv_order_cs=" + recv_order_cs
				+ ", handle_order_user_time=" + handle_order_user_time
				+ ", handle_order_cs=" + handle_order_cs
				+ ", receipt_order_time=" + receipt_order_time + "]";
	}

	

}
