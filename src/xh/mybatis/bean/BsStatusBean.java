package xh.mybatis.bean;

import java.io.Serializable;

public class BsStatusBean implements Serializable{

	/**
	 * 基站状态表 实体类
	 */
	private static final long serialVersionUID = 1L;
	private int bsId;
	private int status;
	private int groupNum;
	private int mscNum;
	private String updateTime;
	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}
	public int getMscNum() {
		return mscNum;
	}
	public void setMscNum(int mscNum) {
		this.mscNum = mscNum;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "BsStatusBean [bsId=" + bsId + ", status=" + status
				+ ", groupNum=" + groupNum + ", mscNum=" + mscNum
				+ ", updateTime=" + updateTime + "]";
	}
	

}
