package xh.org.socket;
/*消息格式：”mcd”:{"operate":2,"optResult":true,"errMsg":"","range":""}

其中：

operate：操作码

1：创建（不可用）

2：查询

3：控制

4：删除（不可用）

optResult：操作结果，用户发送某操作后，服务器以同样类型的消息返回，optResult 中填写

了本次操作的结果，若为 true，则表示本次操作成功，若为 false，则在 errMsg 项中描述出错原因

errMsg：附加错误消息。

range：数据操作范围，详细作用根据每种类型数据而定

-1：部分查询

0：全部查询*/

public class McdStruct {
	private int operate;//1：创建（不可用）2：查询 3：控制 4：删除（不可用）
	private boolean optResult;
	private String errMsg;
	private int range;
	public int getOperate() {
		return operate;
	}
	public void setOperate(int operate) {
		this.operate = operate;
	}
	public boolean isOptResult() {
		return optResult;
	}
	public void setOptResult(boolean optResult) {
		this.optResult = optResult;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	@Override
	public String toString() {
		return "McdStruct [operate=" + operate + ", optResult=" + optResult
				+ ", errMsg=" + errMsg + ", range=" + range + "]";
	}
	
	

}

