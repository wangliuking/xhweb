package xh.mybatis.bean;

public class RecordNetBean {
private int id;
private String create_time;
private String person;
private String type;
private String address;
private String content;
private String total;
private String remark;
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
public String getPerson() {
	return person;
}
public void setPerson(String person) {
	this.person = person;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getTotal() {
	return total;
}
public void setTotal(String total) {
	this.total = total;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
@Override
public String toString() {
	return "RecordNetBean [id=" + id + ", create_time=" + create_time
			+ ", person=" + person + ", type=" + type + ", address=" + address
			+ ", content=" + content + ", total=" + total + ", remark="
			+ remark + "]";
}


}
