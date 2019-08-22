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
	
	
	
	
	private List<Map<String,Object>> files;
	
	
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
	
	public String getLevel() {
		return level;
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
				+ reason + ", type=" + type + ", sendUnit=" + sendUnit
				+ ", recvUnit=" + recvUnit + ", copyUnit=" + copyUnit
				+ ", time=" + time + ", code=" + code + ", content=" + content
				+ ", addUser=" + addUser + ", user_type=" + user_type
				+ ", checkUser=" + checkUser + ", checkTime=" + checkTime
				+ ", userName=" + userName + ", checkUserName=" + checkUserName
				+ ", filePath=" + filePath + ", fileName=" + fileName
				+ ", status=" + status + ", file_name=" + file_name
				+ ", file_path=" + file_path + ", check_person=" + check_person
				+ ", check_time=" + check_time + ", note=" + note + ", files="
				+ files + "]";
	}
	
	
	
	

}
