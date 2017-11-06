package xh.org.listeners;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import xh.mybatis.bean.ProgressBean;

public class FileUploadProgressListener implements ProgressListener {
	private HttpSession session;

	public void setSession(HttpSession session) {
		this.session = session;
		ProgressBean status = new ProgressBean();// 保存上传状态
		session.setAttribute("status", status);
	}

	@Override
	public void update(long bytesRead, long contentLength, int items) {
		// TODO Auto-generated method stub
		ProgressBean status = (ProgressBean) session.getAttribute("status");
		status.setBytesRead(bytesRead);
		status.setContentLength(contentLength);
		status.setItems(items);

	}


	

}
