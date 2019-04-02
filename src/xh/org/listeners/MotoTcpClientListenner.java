package xh.org.listeners;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import xh.org.socket.MotoTcpClient;

public class MotoTcpClientListenner implements ServletContextListener{
	 private String ip;
	 private int port;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		MotoTcpClient mTcp=new  MotoTcpClient();
		mTcp.start();
		
		
	}

}
