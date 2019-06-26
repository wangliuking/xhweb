package xh.mybatis.bean;

import java.util.List;
import java.util.Map;

public class RecordTrainBean {
private int id;
private String create_time;
private String address;
private String talk_person;
private String personnel;
private String name;
private String content;
private String note;
private List<Map<String, Object>> files;
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
public String getCreate_time() {
	return create_time;
}
public void setCreate_time(String create_time) {
	this.create_time = create_time;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getTalk_person() {
	return talk_person;
}
public void setTalk_person(String talk_person) {
	this.talk_person = talk_person;
}
public String getPersonnel() {
	return personnel;
}
public void setPersonnel(String personnel) {
	this.personnel = personnel;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getNote() {
	return note;
}
public void setNote(String note) {
	this.note = note;
}
@Override
public String toString() {
	return "RecordTrainBean [id=" + id + ", create_time=" + create_time
			+ ", address=" + address + ", talk_person=" + talk_person
			+ ", personnel=" + personnel + ", name=" + name + ", content="
			+ content + ", note=" + note + "]";
}

}
