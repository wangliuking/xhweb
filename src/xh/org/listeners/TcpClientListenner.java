package xh.org.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import xh.org.socket.TcpClient;

public class TcpClientListenner implements ServletContextListener{
	private TcpClient tcpClient;

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
			tcpClient=new TcpClient();
			tcpClient.start();
		}
	}

}
