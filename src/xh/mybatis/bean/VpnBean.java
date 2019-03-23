package xh.mybatis.bean;

import java.io.Serializable;

public class VpnBean implements Serializable{
	String vpnId;
	String pId;
	String name;
	String c_name;
	String sn;
	String time;
	String type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	@Override
	public String toString() {
		return "VpnBean{" +
				"vpnId='" + vpnId + '\'' +
				", pId='" + pId + '\'' +
				", name='" + name + '\'' +
				", c_name='" + c_name + '\'' +
				", sn='" + sn + '\'' +
				", time='" + time + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
