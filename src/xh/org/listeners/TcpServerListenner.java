package xh.org.listeners;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinamobile.fsuservice.Test;
import com.tcpServer.ServerDemo;

import xh.func.plugin.FunUtil;
import xh.org.socket.TcpKeepAliveClient;
import xh.springmvc.handlers.GosuncnController;

public class TcpServerListenner implements ServletContextListener{
	private Timer timer = null;
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		timer = new Timer(true);
		timer.schedule(new TcpServerThread(), 20 * 1000);
	}

}

/**
 * tcp服务器开启线程
 */
class TcpServerThread extends TimerTask {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerDemo server = new ServerDemo();
		try {
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
