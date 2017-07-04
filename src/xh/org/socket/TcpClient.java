package xh.org.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FunUtil;

public class TcpClient extends Thread {
	protected final Log log = LogFactory.getLog(TcpClient.class);
	private String ip;
	private int port;
	private static Socket socket;
	private static int timeout = 0;
	private static String recvStr = "";
	private boolean connected = false;

	private FunUtil funUtil = new FunUtil();

	public void run() {

		receive();
	}

	// 接收数据
	public void receive() {
		while (!connected) {
			try {
				connect();
				if (socket.isConnected()) {
					connected = true;
					log.info("emh socket Connected success!!");
				}
				// read body
				InputStream is = socket.getInputStream();
				byte b[] = new byte[1024];
				while (connected) {
					int len = is.read(b);
					if (len > 0) {
						byte c[] = new byte[len];
						System.arraycopy(b, 0, c, 0, len);
						recvStr = new String(c);
						handler(recvStr);

					} else {
						socket.close();
						log.info("====emh socket is closed!!  and reconnection now ====");
						connected = false;
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
				log.info("recvData timeout 10s,emh socket is closed and reconnecting!");
				try {
					socket.close();
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
			} catch (IOException e) {

			}
			try {
				socket.setSoTimeout(timeout);
			} catch (SocketException e) {

				try {
					socket.close();

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				connected = false;
			}

		}
	}

	// 转16进制
	public String HexString(byte[] src) {
		String str = "";
		int v1 = src[0] & 0xFF;
		int v2 = src[1] & 0xFF;
		str = Integer.toHexString(v1) + Integer.toHexString(v2);
		return str;
	}
	//字符串处理
	public String tt(String str) {
		int lastPos = 0;
		int a = 0;
		StringBuffer astr = new StringBuffer();
		if (str.length() == 0) {
			return "";
		}
		if (!str.startsWith("{")) {
			return "";
		}
		for (int i = 0; i < str.length(); i++) {
			if (String.valueOf(str.charAt(i)).equals("{")) {
				a++;
				/* System.out.println("point-> a++"+a); */
			} else if (String.valueOf(str.charAt(i)).equals("}")) {
				a--;
				/* System.out.println("point-> a--"+a); */
				if (a == 0) {
					astr.append(str.substring(lastPos, i + 1) + ";");
					lastPos = i + 1;
				}
			} else {

			}

		}
		if (lastPos < str.length()) {
			astr.append(str.substring(lastPos, str.length()));
		}
		return astr.toString();
	}


	public void handler(String str) {
		log.info(str);
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
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				out.println("workstatus:1");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}

		} else {
			log.info("====Timer:socket closed!!====");
		}
	}
}
