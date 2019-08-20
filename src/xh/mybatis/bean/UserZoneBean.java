package xh.mybatis.bean;

public class UserZoneBean {
	private String user;
	private String zone;
	public String getUser() {
		return user;
	}
	public String getZone() {
		return zone;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	@Override
	public String toString() {
		return "UserZoneBean [user=" + user + ", zone=" + zone + "]";
	}
	

}
