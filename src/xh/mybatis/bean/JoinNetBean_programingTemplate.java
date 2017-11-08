package xh.mybatis.bean;

public class JoinNetBean_programingTemplate {
	private int id;
	private int id_JoinNet;
	private String fileName;
	private String filePath;
	private String insertTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_JoinNet() {
		return id_JoinNet;
	}
	public void setId_JoinNet(int id_JoinNet) {
		this.id_JoinNet = id_JoinNet;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	@Override
	public String toString() {
		return "JoinNetBean_programingTemplate [id=" + id + ", id_JoinNet=" + id_JoinNet + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", insertTime=" + insertTime + "]";
	}
	

}
