package xh.mybatis.bean;

public class ImpExcelBean {
	private String db;
	private String lng;
	private String lat;
	private String positionArea;
	private String nPositionArea;
	private String ndb;
	private String time;
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getPositionArea() {
		return positionArea;
	}
	public void setPositionArea(String positionArea) {
		this.positionArea = positionArea;
	}
	public String getnPositionArea() {
		return nPositionArea;
	}
	public void setnPositionArea(String nPositionArea) {
		this.nPositionArea = nPositionArea;
	}
	public String getNdb() {
		return ndb;
	}
	public void setNdb(String ndb) {
		this.ndb = ndb;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "ImpExcelBean [db=" + db + ", lng=" + lng + ", lat=" + lat
				+ ", positionArea=" + positionArea + ", nPositionArea="
				+ nPositionArea + ", ndb=" + ndb + ", time=" + time + "]";
	}
	
	
	
	
}
