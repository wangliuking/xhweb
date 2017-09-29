package xh.org.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import xh.func.plugin.FunUtil;
import xh.org.socket.TcpKeepAliveClient;

public class TcpClientListenner implements ServletContextListener{
	private TcpKeepAliveClient tcpClient;
	 private String ip;
	 private int port;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		if (tcpClient.isInterrupted()||tcpClient!=null) {
			tcpClient.interrupt();
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		if (tcpClient==null) {
			ip=FunUtil.readXml("ucm", "ip");
            port=Integer.parseInt(FunUtil.readXml("ucm", "port"));
			tcpClient=new TcpKeepAliveClient(ip,port);
			tcpClient.start();
		}
	}

}
