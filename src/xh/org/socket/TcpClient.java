package xh.org.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;

public class TcpClient extends Thread {
	protected final Log log = LogFactory.getLog(TcpClient.class);
	private String ip;
	private int port;
	private static Socket socket;
	private static int timeout = 10000;
	private static String recvStr = "";
	private boolean connected = false;
	private static OutputStream outStr = null;

	private static InputStream inStr = null;
	private static Timer heartTimer = null;

	private FunUtil funUtil = new FunUtil();
	private FlexJSON json = new FlexJSON();
	private GsonUtil gson = new GsonUtil();
	private Timer timer = null;

	public void run() {

		receive();
	}

	// 接收数据
	public void receive() {
		while (!connected) {
			try {
				connect();
				InputStream is = socket.getInputStream();
				byte b[] = new byte[1024];
				while (connected) {
					int len = is.read(b);

					if (len > 0) {
						byte c[] = new byte[len];
						System.arraycopy(b, 0, c, 0, len);
						recvStr = new String(c, "UTF-8");
						handler(recvStr);

					} else {
						socket.close();
						log.info("====emh socket is closed!! and reconnection now ====");
						connected = false;
						sleep(3000);
					}
				}

			} catch (SocketException e) {
				log.info("emh socket connection trying");
				if (socket.isConnected() || socket != null) {
					try {
						socket.close();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					connected = false;
				}

			} catch (UnknownHostException e) {
				System.out.println("UnknownHostException");
			} catch (IOException e) {
				log.info("recvData timeout 60s,emh socket is closed and reconnecting!");
				try {
					socket.close();
					connected = false;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				connected = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void connect() {
		if (socket == null || socket.isClosed() || !socket.isConnected()) {
			socket = new Socket();
			ip = funUtil.readXml("emh", "ip");
			port = funUtil.StringToInt(funUtil.readXml("emh", "port"));
			InetSocketAddress addr = new InetSocketAddress(ip, port);
			try {
				socket.connect(addr, timeout);
				socket.setTcpNoDelay(true);
				socket.setKeepAlive(true);
				connected = true;
				log.info("emh socket Connected success!!");
				if(heartTimer==null){
					heartTimer=new Timer();
					heartTimer.schedule(new HeartBeat(socket), 2000, 10000);
				}
								
			} catch (IOException e) {

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				socket.setSoTimeout(timeout);
			} catch (SocketException e) {

				try {
					socket.close();
					Thread.sleep(1000);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				connected = false;
			}

		}
	}

	public void handler(String str) {
		String recvch = "";
		if (str.indexOf("\n") > 0) {
			recvch = str.substring(0, str.indexOf("\n"));
		}
		log.info("<--" + socket.getInetAddress() + ":" + str);
		if (funUtil.isInteger(recvch)) {
			sendAuth(str);
		}
	}

	// 握手认证
	public void sendAuth(String str) {
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				out.println("ITHIRD/" + str);
				out.flush();
				log.info("sendAuth-->" + socket.getInetAddress() + ":ITHIRD/"
						+ str);
				sleep(2000);
				sendRegister();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			log.info("====Timer:emh socket closed!!====");
		}
	}

	public void sendRegister() {
		/*
		 * "account":"dev3", "mcd": { “operate”：1 }, "name":null,
		 * "password":"abcd1234", "type":2, "usertype":2
		 */
		LoginStruct login = new LoginStruct();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operate", 1);
		login.setAccount(funUtil.readXml("emhLogin", "account"));
		login.setPassword(funUtil.readXml("emhLogin", "password"));
		login.setMcd(map);
		Map<String, Object> loginMap = new HashMap<String, Object>();
		loginMap.put("account", login.getAccount());
		loginMap.put("mcd", login.getMcd());
		loginMap.put("password", login.getPassword());
		loginMap.put("type", login.getType());
		loginMap.put("usertype", login.getUsertype());
		// 封装json数据json.Encode(list)
		String jsonstr = json.Encode(loginMap);
		log.info("Login-->" + socket.getInetAddress() + ":" + jsonstr);
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				out.println(jsonstr + "\n");
				out.flush();
				TestData();
				timer = new Timer();
				try {
					timer.schedule(new HeartBeat(socket), 2000, 30 * 1000);
				} catch (IOException e) {
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		} else {
			log.info("====Timer:emh socket closed!!====");
		}
	}

	// 测点表数据
	public void TestData() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mcdMap = new HashMap<String, Object>();
		map.put("type", 5);
		mcdMap.put("range", 0);
		mcdMap.put("operate", 2);
		map.put("mcd", mcdMap);
		String jsonstr = json.Encode(map);
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				out.println(jsonstr + "\n");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		} else {
			log.info(" socket error!");
		}

	}
	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		TcpClient.socket = socket;
	}

	public static int getTimeout() {
		return timeout;
	}

	public static void setTimeout(int timeout) {
		TcpClient.timeout = timeout;
	}
}

// 心跳包
class HeartBeat extends TimerTask {
	private Socket socket = null;
	private TcpClient tcp;

	protected final Log log = LogFactory.getLog(HeartBeat.class);

	public HeartBeat(Socket socket) throws Exception {
		this.socket = socket;

	}

	public void run() {
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				if (TcpClient.getSocket() != null) {
					out = new PrintWriter(TcpClient.getSocket()
							.getOutputStream());
					out.println("~IMCP iCore Connection Pulse~\n");
					out.flush();
					log.info("KeepThread->~IMCP iCore Connection Pulse~");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		} else {
			log.info("====Timer:socket closed!!====");
		}
	}
}
