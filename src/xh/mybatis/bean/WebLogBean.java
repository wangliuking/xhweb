package xh.mybatis.bean;

import xh.func.plugin.FunUtil;

public class WebLogBean {
	private int id;
	private String operator;
	private String operatorIp;
	private int style;
	private String content;
	private String createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorIp() {
		return operatorIp;
	}
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}
	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "WebLogBean [id=" + id + ", operator=" + operator
				+ ", operatorIp=" + operatorIp + ", style=" + style
				+ ", content=" + content + ", createTime=" + createTime + "]";
	}
	
	
	

}
