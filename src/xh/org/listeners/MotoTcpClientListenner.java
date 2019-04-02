package xh.org.listeners;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import xh.func.plugin.FunUtil;
import xh.org.socket.MotoTcpKeepAliveClient;
import xh.org.socket.TcpKeepAliveClient;

public class MotoTcpClientListenner implements ServletContextListener{
	private Timer timer=null;
	 private String ip;
	 private int port;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("safskjsajskjncsjnckjnck");
		
	}

}
