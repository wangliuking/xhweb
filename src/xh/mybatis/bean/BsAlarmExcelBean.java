package xh.mybatis.bean;
 
public class BsAlarmExcelBean {
	private int id;
	private String bsId;//基站编号
	private String name;//基站名称
	private String level;//基站分级
	private int period;
	private int tag;//使用状态
	private String weekly;//星期
	private String time;//故障发生时间
	private String from="网管";//报障来源
	private String severity="一般故障";//故障等级
	private String type;//故障类别
	private String reason;//故障原因
	private String nowDeal;//目前处理情况
	private String imbusiness="是";//是否影响业务
	private String dealResult;//故障处理结果
	private String faultRecoveryTime;//故障恢复时间
	private String faultTimeTotal;//故障历时
	private String content;//备注
	private String faultHandlePerson;//故障处理人员
	private String faultRecordPerson;//故障记录人员
	private String hometype;//基站归属
	private String faultType;//故障归属elc_time
	
	private String is_allow_generation;
	private String generation_date;
	private String elc_time;
	
	
	
	
	
	
	public String getFaultType() {
		return faultType;
	}
	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getWeekly() {
		return weekly;
	}
	public void setWeekly(String weekly) {
		this.weekly = weekly;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getImbusiness() {
		return imbusiness;
	}
	public void setImbusiness(String imbusiness) {
		this.imbusiness = imbusiness;
	}
	public String getDealResult() {
		return dealResult;
	}
	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}
	public String getFaultTimeTotal() {
		return faultTimeTotal;
	}
	public void setFaultTimeTotal(String faultTimeTotal) {
		this.faultTimeTotal = faultTimeTotal;
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
	public String getHometype() {
		return hometype;
	}
	public void setHometype(String hometype) {
		this.hometype = hometype;
	}
	public String getFaultRecoveryTime() {
		return faultRecoveryTime;
	}
	public void setFaultRecoveryTime(String faultRecoveryTime) {
		this.faultRecoveryTime = faultRecoveryTime;
	}
	
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public String getIs_allow_generation() {
		return is_allow_generation;
	}
	public void setIs_allow_generation(String is_allow_generation) {
		this.is_allow_generation = is_allow_generation;
	}
	public String getGeneration_date() {
		return generation_date;
	}
	public void setGeneration_date(String generation_date) {
		this.generation_date = generation_date;
	}
	
	public String getElc_time() {
		return elc_time;
	}
	public void setElc_time(String elc_time) {
		this.elc_time = elc_time;
	}
	@Override
	public String toString() {
		return "BsAlarmExcelBean [id=" + id + ", bsId=" + bsId + ", name="
				+ name + ", level=" + level + ", tag=" + tag + ", weekly="
				+ weekly + ", time=" + time + ", from=" + from + ", severity="
				+ severity + ", type=" + type + ", reason=" + reason
				+ ", nowDeal=" + nowDeal + ", imbusiness=" + imbusiness
				+ ", dealResult=" + dealResult + ", faultRecoveryTime="
				+ faultRecoveryTime + ", faultTimeTotal=" + faultTimeTotal
				+ ", content=" + content + ", faultHandlePerson="
				+ faultHandlePerson + ", faultRecordPerson="
				+ faultRecordPerson + ", hometype=" + hometype + "]";
	}
	
	
	
	
	
	
	

}
