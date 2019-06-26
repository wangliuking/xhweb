package xh.mybatis.bean;

public class RecordCommunicationBean {
	private int id;
	private String name;
	private String start_time;
	private String end_time;
	private String zone;
	private String level;
	private String file_name;
	private String file_path;
	private String satellite_time;
	private String bus_num;
	private String person_num;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
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
	public String getSatellite_time() {
		return satellite_time;
	}
	public void setSatellite_time(String satellite_time) {
		this.satellite_time = satellite_time;
	}
	public String getBus_num() {
		return bus_num;
	}
	public void setBus_num(String bus_num) {
		this.bus_num = bus_num;
	}
	public String getPerson_num() {
		return person_num;
	}
	public void setPerson_num(String person_num) {
		this.person_num = person_num;
	}
	@Override
	public String toString() {
		return "RecordCommunicationBean [id=" + id + ", name=" + name
				+ ", start_time=" + start_time + ", end_time=" + end_time
				+ ", zone=" + zone + ", level=" + level + ", file_name="
				+ file_name + ", file_path=" + file_path + ", satellite_time="
				+ satellite_time + ", bus_num=" + bus_num + ", person_num="
				+ person_num + "]";
	}
	
	
}
