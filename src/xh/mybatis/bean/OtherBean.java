package xh.mybatis.bean;

public class OtherBean {
	private String bsId;
	private String name;
	private String adjacentCellId;
	public String getBsId() {
		return bsId;
	}
	public void setBsId(String bsId) {
		this.bsId = bsId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdjacentCellId() {
		return adjacentCellId;
	}
	public void setAdjacentCellId(String adjacentCellId) {
		this.adjacentCellId = adjacentCellId;
	}
	@Override
	public String toString() {
		return "OtherBean [bsId=" + bsId + ", name=" + name
				+ ", adjacentCellId=" + adjacentCellId + "]";
	}
	
	
	
}
