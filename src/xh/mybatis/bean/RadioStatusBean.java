package xh.mybatis.bean;

import java.io.Serializable;

public class RadioStatusBean implements Serializable{
	/**
	 * 终端状态表 实体类
	 */
	private static final long serialVersionUID = 1L;
	private int mscId;
	private int bsId;
	private int onlineStatus;
	private int talkStatus;
	private int talkGroupId;
	private int sysTag;
	private String updateTime;
	public int getMscId() {
		return mscId;
	}
	public void setMscId(int mscId) {
		this.mscId = mscId;
	}
	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
	}
	public int getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	public int getTalkStatus() {
		return talkStatus;
	}
	public void setTalkStatus(int talkStatus) {
		this.talkStatus = talkStatus;
	}
	public int getTalkGroupId() {
		return talkGroupId;
	}
	public void setTalkGroupId(int talkGroupId) {
		this.talkGroupId = talkGroupId;
	}
	public int getSysTag() {
		return sysTag;
	}
	public void setSysTag(int sysTag) {
		this.sysTag = sysTag;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "RadioStatusBean [mscId=" + mscId + ", bsId=" + bsId
				+ ", onlineStatus=" + onlineStatus + ", talkStatus="
				+ talkStatus + ", talkGroupId=" + talkGroupId + ", sysTag="
				+ sysTag + ", updateTime=" + updateTime + "]";
	}
	

}
