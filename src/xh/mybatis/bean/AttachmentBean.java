package xh.mybatis.bean;

public class AttachmentBean {
	private int id;
	private String attachment_name;
	private String attachment_desc;
	private String attachment_model;
	private String attachment_sn;
	private String attachment_company;
	private String attachment_conf_number;
	private String attachment_unit;
	private float avai;
	private String attachment_reality_number;
	private String attachment_location;
	private String attachment_note;
	private String time;
	
	
	
	@Override
	public String toString() {
		return "AttachmentBean [id=" + id + ", attachment_name="
				+ attachment_name + ", attachment_desc=" + attachment_desc
				+ ", attachment_model=" + attachment_model + ", attachment_sn="
				+ attachment_sn + ", attachment_company=" + attachment_company
				+ ", attachment_conf_number=" + attachment_conf_number
				+ ", attachment_unit=" + attachment_unit + ", avai=" + avai
				+ ", attachment_reality_number=" + attachment_reality_number
				+ ", attachment_location=" + attachment_location
				+ ", attachment_note=" + attachment_note + ", time=" + time
				+ "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAttachment_name() {
		return attachment_name;
	}
	public void setAttachment_name(String attachment_name) {
		this.attachment_name = attachment_name;
	}
	public String getAttachment_desc() {
		return attachment_desc;
	}
	public void setAttachment_desc(String attachment_desc) {
		this.attachment_desc = attachment_desc;
	}
	public String getAttachment_model() {
		return attachment_model;
	}
	public void setAttachment_model(String attachment_model) {
		this.attachment_model = attachment_model;
	}
	public String getAttachment_company() {
		return attachment_company;
	}
	public void setAttachment_company(String attachment_company) {
		this.attachment_company = attachment_company;
	}
	public String getAttachment_conf_number() {
		return attachment_conf_number;
	}
	public void setAttachment_conf_number(String attachment_conf_number) {
		this.attachment_conf_number = attachment_conf_number;
	}
	public String getAttachment_reality_number() {
		return attachment_reality_number;
	}
	public void setAttachment_reality_number(String attachment_reality_number) {
		this.attachment_reality_number = attachment_reality_number;
	}
	public String getAttachment_location() {
		return attachment_location;
	}
	public void setAttachment_location(String attachment_location) {
		this.attachment_location = attachment_location;
	}
	public String getAttachment_note() {
		return attachment_note;
	}
	public void setAttachment_note(String attachment_note) {
		this.attachment_note = attachment_note;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAttachment_sn() {
		return attachment_sn;
	}
	public void setAttachment_sn(String attachment_sn) {
		this.attachment_sn = attachment_sn;
	}
	public float getAvai() {
		return avai;
	}
	public void setAvai(float avai) {
		this.avai = avai;
	}
	public String getAttachment_unit() {
		return attachment_unit;
	}
	public void setAttachment_unit(String attachment_unit) {
		this.attachment_unit = attachment_unit;
	}
	
	

}
