package xh.mybatis.bean;

public class smsBean {
	private int srcId;
	private int dstId;
	private int referenceNumber;
	private int consume;
	private int len;
	private int type;
	private String content;
	public int getSrcId() {
		return srcId;
	}
	public void setSrcId(int srcId) {
		this.srcId = srcId;
	}
	public int getDstId() {
		return dstId;
	}
	public void setDstId(int dstId) {
		this.dstId = dstId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(int referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public int getConsume() {
		return consume;
	}
	public void setConsume(int consume) {
		this.consume = consume;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	@Override
	public String toString() {
		return "smsBean [srcId=" + srcId + ", dstId=" + dstId
				+ ", referenceNumber=" + referenceNumber + ", consume="
				+ consume + ", len=" + len + ", type=" + type + ", content="
				+ content + "]";
	}
	
	

}
