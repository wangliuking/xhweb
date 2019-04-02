package xh.protobuf;

//新增终端信息响应
/*message RadioAddRsp{  
	required string result	= 1;       
  required string detail	= 2; 
}*/
public class RadioUserAddRspBean {
	private String result;
	private String detail;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Override
	public String toString() {
		return "RadioAddRsp [result=" + result + ", detail=" + detail + "]";
	}
	
	

}
