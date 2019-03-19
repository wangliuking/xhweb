package xh.mybatis.bean;

public class UserNeedBean {
	private int id;
	 private String time;
	 private String contact_person;
	 private String address;
	 private String description;
	 private String response;
	 private String handle;
	 private String result;
	 private String note;
	 private String state;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContact_person() {
		return contact_person;
	}
	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "UserNeedBean [id=" + id + ", time=" + time
				+ ", contact_person=" + contact_person + ", address=" + address
				+ ", description=" + description + ", response=" + response
				+ ", handle=" + handle + ", result=" + result + ", note="
				+ note + ", state=" + state + "]";
	}
	 
	 


}
