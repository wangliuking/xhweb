package xh.mybatis.bean;

import java.io.Serializable;

public class VpnBean implements Serializable{
	String vpnId;
	String pId;
	String name;
	String sn;
	String time;
	public String getVpnId() {
		return vpnId;
	}
	public void setVpnId(String vpnId) {
		this.vpnId = vpnId;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "VpnBean [vpnId=" + vpnId + ", pId=" + pId + ", name=" + name
				+ ", sn=" + sn + ", time=" + time + "]";
	}
	
}
