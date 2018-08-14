package xh.mybatis.bean;

public class AssetScrapApplayInfoBean {
	
	private int applyId;
	private String createTime;
	private String workNote;
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getWorkNote() {
		return workNote;
	}
	public void setWorkNote(String workNote) {
		this.workNote = workNote;
	}
	@Override
	public String toString() {
		return "AssetAddApplayInfoBean [applyId=" + applyId + ", createTime="
				+ createTime + ", workNote=" + workNote + "]";
	}
	
	

}
