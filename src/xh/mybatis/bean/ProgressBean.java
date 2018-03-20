package xh.mybatis.bean;
 
public class ProgressBean {
	private long bytesRead=0L;   //到目前为止读取文件的比特数
    private long contentLength=0L;  //文件总大小
    private long items;//目前正在读取第几个文件
	public long getBytesRead() {
		return bytesRead;
	}
	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
	}
	public long getContentLength() {
		return contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	public long getItems() {
		return items;
	}
	public void setItems(long items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "ProgressBean [bytesRead=" + bytesRead + ", contentLength="
				+ contentLength + ", items=" + items + "]";
	}
    
	
    

}
