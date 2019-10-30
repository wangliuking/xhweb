package xh.mybatis.bean;

import java.util.List;
import java.util.Map;

public class WorkContactBean {
	private int id;
	private String taskId;
	private String reason;
	private String type;
	private String level;
	private String sendUnit;
	private String recvUnit;
	private String copyUnit;
	private String time;
	private String code;
	private String content;
	private String addUser;
	private int user_type;
	private String checkUser;
	private String checkTime;
	private String userName;
	private String checkUserName;
	private String signUserName;
	private String filePath;
	private String fileName;
	private int status;
	
	private String file_name;
	private String file_path;
	private String check_person;
	private String check_time;
	private String note;
	private String reply;
	
	
	private String handle_user;
	private String handle_time;
	private String handle_note;
	private String handleUserName;
	
	private String summary_user;
	private String summaryUserName;
	private String summary_time;
	private String summary_note;
	private String summary_fileName;
	private String summary_filePath;
	
	private String ensure_starttime=null;
	private String ensure_endtime=null;
	private String ensure_zone;
	
	private String ensure_satellite_time;
	private String ensure_bus_num;
	private String person_num;
	private List<Map<String,Object>> files;
	private List<Map<String,Object>> handle_files;
	private List<Map<String,Object>> summary_files;
	
	
	

	public List<Map<String, Object>> getSummary_files() {
		return summary_files;
	}
	public void setSummary_files(List<Map<String, Object>> summary_files) {
		this.summary_files = summary_files;
	}
	public List<Map<String, Object>> getHandle_files() {
		return handle_files;
	}
	public void setHandle_files(List<Map<String, Object>> handle_files) {
		this.handle_files = handle_files;
	}
	public String getEnsure_satellite_time() {
		return ensure_satellite_time;
	}
	public String getEnsure_bus_num() {
		return ensure_bus_num;
	}
	public String getPerson_num() {
		return person_num;
	}
	public void setEnsure_satellite_time(String ensure_satellite_time) {
		this.ensure_satellite_time = ensure_satellite_time;
	}
	public void setEnsure_bus_num(String ensure_bus_num) {
		this.ensure_bus_num = ensure_bus_num;
	}
	public void setPerson_num(String person_num) {
		this.person_num = person_num;
	}

	
	
	public List<Map<String, Object>> getFiles() {
		return files;
	}
	public void setFiles(List<Map<String, Object>> files) {
		this.files = files;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSendUnit() {
		return sendUnit;
	}
	public void setSendUnit(String sendUnit) {
		this.sendUnit = sendUnit;
	}
	public String getRecvUnit() {
		return recvUnit;
	}
	public void setRecvUnit(String recvUnit) {
		this.recvUnit = recvUnit;
	}
	public String getCopyUnit() {
		return copyUnit;
	}
	public void setCopyUnit(String copyUnit) {
		this.copyUnit = copyUnit;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public int getUser_type() {
		return user_type;
	}
	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getHandleUserName() {
		return handleUserName;
	}
	public String getSummaryUserName() {
		return summaryUserName;
	}
	public void setHandleUserName(String handleUserName) {
		this.handleUserName = handleUserName;
	}
	public void setSummaryUserName(String summaryUserName) {
		this.summaryUserName = summaryUserName;
	}
	public String getSummary_user() {
		return summary_user;
	}
	public String getSummary_time() {
		return summary_time;
	}
	public String getSummary_note() {
		return summary_note;
	}
	public String getSummary_fileName() {
		return summary_fileName;
	}
	public String getSummary_filePath() {
		return summary_filePath;
	}
	public void setSummary_user(String summary_user) {
		this.summary_user = summary_user;
	}
	public void setSummary_time(String summary_time) {
		this.summary_time = summary_time;
	}
	public void setSummary_note(String summary_note) {
		this.summary_note = summary_note;
	}
	public void setSummary_fileName(String summary_fileName) {
		this.summary_fileName = summary_fileName;
	}
	public void setSummary_filePath(String summary_filePath) {
		this.summary_filePath = summary_filePath;
	}
	public String getHandle_user() {
		return handle_user;
	}
	public String getHandle_time() {
		return handle_time;
	}
	public String getHandle_note() {
		return handle_note;
	}
	public void setHandle_user(String handle_user) {
		this.handle_user = handle_user;
	}
	public void setHandle_time(String handle_time) {
		this.handle_time = handle_time;
	}
	public void setHandle_note(String handle_note) {
		this.handle_note = handle_note;
	}
	public String getLevel() {
		return level;
	}
	public String getEnsure_starttime() {
		return ensure_starttime;
	}
	public String getEnsure_endtime() {
		return ensure_endtime;
	}
	public String getEnsure_zone() {
		return ensure_zone;
	}
	public void setEnsure_starttime(String ensure_starttime) {
		this.ensure_starttime = ensure_starttime;
	}
	public void setEnsure_endtime(String ensure_endtime) {
		this.ensure_endtime = ensure_endtime;
	}
	public void setEnsure_zone(String ensure_zone) {
		this.ensure_zone = ensure_zone;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getCheckUserName() {
		return checkUserName;
	}
	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getCheck_person() {
		return check_person;
	}
	public void setCheck_person(String check_person) {
		this.check_person = check_person;
	}
	public String getCheck_time() {
		return check_time;
	}
	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSignUserName() {
		return signUserName;
	}
	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}
	@Override
	public String toString() {
		return "WorkContactBean [id=" + id + ", taskId=" + taskId + ", reason="
				+ reason + ", type=" + type + ", level=" + level
				+ ", sendUnit=" + sendUnit + ", recvUnit=" + recvUnit
				+ ", copyUnit=" + copyUnit + ", time=" + time + ", code="
				+ code + ", content=" + content + ", addUser=" + addUser
				+ ", user_type=" + user_type + ", checkUser=" + checkUser
				+ ", checkTime=" + checkTime + ", userName=" + userName
				+ ", checkUserName=" + checkUserName + ", signUserName="
				+ signUserName + ", filePath=" + filePath + ", fileName="
				+ fileName + ", status=" + status + ", file_name=" + file_name
				+ ", file_path=" + file_path + ", check_person=" + check_person
				+ ", check_time=" + check_time + ", note=" + note + ", reply="
				+ reply + ", handle_user=" + handle_user + ", handle_time="
				+ handle_time + ", handle_note=" + handle_note
				+ ", handleUserName=" + handleUserName + ", summary_user="
				+ summary_user + ", summaryUserName=" + summaryUserName
				+ ", summary_time=" + summary_time + ", summary_note="
				+ summary_note + ", summary_fileName=" + summary_fileName
				+ ", summary_filePath=" + summary_filePath
				+ ", ensure_starttime=" + ensure_starttime
				+ ", ensure_endtime=" + ensure_endtime + ", ensure_zone="
				+ ensure_zone + ", ensure_satellite_time="
				+ ensure_satellite_time + ", ensure_bus_num=" + ensure_bus_num
				+ ", person_num=" + person_num + ", files=" + files + "]";
	}
	
	
	
	

}
