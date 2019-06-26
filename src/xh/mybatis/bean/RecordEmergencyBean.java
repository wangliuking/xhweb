package xh.mybatis.bean;

import java.util.List;
import java.util.Map;

public class RecordEmergencyBean {
	private int id;
	private String create_time;
	private String name;
	private String type;
	private String address;
	private String personnel;
	private String content;
	private String process;
	private String summary;
	
	private String file_name;
	private String file_path;
	private List<Map<String,Object>> files;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPersonnel() {
		return personnel;
	}
	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public List<Map<String, Object>> getFiles() {
		return files;
	}
	public void setFiles(List<Map<String, Object>> files) {
		this.files = files;
	}
	@Override
	public String toString() {
		return "RecordEmergencyBean [id=" + id + ", create_time=" + create_time
				+ ", name=" + name + ", type=" + type + ", address=" + address
				+ ", personnel=" + personnel + ", content=" + content
				+ ", process=" + process + ", summary=" + summary
				+ ", file_name=" + file_name + ", file_path=" + file_path + "]";
	}
	
	

}
