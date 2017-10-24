package xh.mybatis.bean;

import java.io.Serializable;
import java.util.Date;

public class InspectionBean implements Serializable {
    private int id;
    private String fileName;
    private String contact;
    private String tel;
    private String note;
    private int status;
    private String filePath;
	private String recvUser;
    private String uploadUser;
    
    private String createtime;
    private String time1;
    private String note1;
    
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getRecvUser() {
		return recvUser;
	}
	public void setRecvUser(String recvUser) {
		this.recvUser = recvUser;
	}
	public String getUploadUser() {
		return uploadUser;
	}
	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}
	
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	@Override
	public String toString() {
		return "WorkBean [id=" + id + ", fileName=" + fileName + ", contact="
				+ contact + ", tel=" + tel + ", note=" + note + ", status="
				+ status + ", filePath=" + filePath + ", recvUser=" + recvUser
				+ ", uploadUser=" + uploadUser + "]";
	}
	
	
    
    

}