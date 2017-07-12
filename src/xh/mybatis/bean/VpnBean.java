package xh.mybatis.bean;

import java.io.Serializable;

public class VpnBean implements Serializable{
	String vpnId;
	String name;
	
	public String getVpnId() {
		return vpnId;
	}
	public void setVpnId(String vpnId) {
		this.vpnId = vpnId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "VpnBean [vpnId=" + vpnId + ", name=" + name + "]";
	}
	
}
