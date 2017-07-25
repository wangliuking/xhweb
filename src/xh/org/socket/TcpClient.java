package xh.org.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.JoinNetBean;

public class TcpClient extends Thread {
	protected final Log log = LogFactory.getLog(TcpClient.class);
	private String ip;
	private int port;
	private static Socket socket;
	private static int timeout = 90000;
	private static String recvStr = "";
	private boolean connected = false;
	private static OutputStream outStr = null;

	private static InputStream inStr = null;
	private static Timer heartTimer = null;
	private static String endStr="";

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
				byte b[] = new byte[10240];
				while (connected) {
					int len = is.read(b);

					if (len > 0) {
						byte c[] = new byte[len];
						System.arraycopy(b, 0, c, 0, len);
						recvStr +=new String(c, "UTF-8");
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
				log.info("recvData timeout 90s,emh socket is closed and reconnecting!");
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

	@SuppressWarnings("unchecked")
	public void handler(String str) {
		log.info("DS<--EMH:" + socket.getInetAddress() + ":" + str);
		String[] recvStrs=str.split("\n");
		Map<String, Object> recvMap = new HashMap<String, Object>();
		Map<String, Object> mcdMap = new HashMap<String, Object>();
		for (String string : recvStrs) {
			if(string.contains("{") || string.contains("}")){
				if(isPackageFull(string)==0){
					recvMap=GsonUtil.json2Object(string,Map.class);
					if(recvMap.get("type")!=null){
						int type=funUtil.StringToInt(recvMap.get("type").toString());
						switch (type) {
						case 2://登录认证
							LoginResponse(recvMap);
							break;
						case 5://实时数据表
							LoginResponse(recvMap);
							break;
						case 6://实时告警表
							LoginResponse(recvMap);
							break;
						case 15://测点配置表
							LoginResponse(recvMap);
							break;
						case 31://状态
							LoginResponse(recvMap);
							break;
						case 58://实时统计
							LoginResponse(recvMap);
							break;

						default:
							break;
						}
					}
					
				}else{
					recvStr=string;
				}
			}else{
				if(string.length()==8){
					sendAuth(string);
				}else if(string.contains("IMCP")){
					log.info("DS<--EMH:Heart:" + socket.getInetAddress() + ":" + string);
				}else{
					
				}
			}
		}
		
		try {

			
		} catch (NullPointerException e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		} catch (StringIndexOutOfBoundsException e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		} catch (ConcurrentModificationException e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
	}
	//登录认证
	public void LoginResponse(Map<String, Object> recvMap){
		@SuppressWarnings("unchecked")
		Map<String, Object> mcdMap = (Map<String, Object>) recvMap.get("mcd");	
		if(mcdMap.get("optResult").toString().equals("true")){
			log.info("DS<--EMH:LoginResponse:登录认证通过");
			if (heartTimer == null) {
				heartTimer = new Timer();
				try {
					heartTimer.schedule(new HeartBeat(socket), 10000,
							10000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			log.info("DS<--EMH:LoginResponse:登录认证失败!"+mcdMap.get("errMsg"));
		}
		
		
		
	}
	//测点配置表
	public void icmpConfigResponse(Map<String, Object> recvMap){
		@SuppressWarnings("unchecked")
		Map<String, Object> mcdMap = (Map<String, Object>) recvMap.get("mcd");	
		Map<String, Object> agents = (Map<String, Object>) recvMap.get("agents");
		/*"name": "空调-佳力图 M816-9#1.1",
		"uuid": "5cbb7006-61cb-425c-bf7b-52a3e392ca38",
		"status":true,
		"state_alarm":6*/
		if(mcdMap.get("optResult").toString().equals("true")){
			log.info("DS<--EMH:LoginResponse:登录认证通过");
			
		}else{
			log.info("DS<--EMH:LoginResponse:登录认证失败!"+mcdMap.get("errMsg"));
		}
		
	}
	
	//完整包判断
	public static int isPackageFull(String str) {
		int lastPos = 0;
		int a = 0;
		StringBuffer astr = new StringBuffer();
		if (str.length() == 0) {
			return -1;
		}
		if (!str.startsWith("{")) {
			return -1;
		}
		for (int i = 0; i < str.length(); i++) {
			if (String.valueOf(str.charAt(i)).equals("{")) {
				a++;
			} else if (String.valueOf(str.charAt(i)).equals("}")) {
				a--;
				if (a == 0) {
					/*astr.append(str.substring(lastPos, i + 1) + ";");*/
					lastPos = i + 1;
				}
			} else {

			}

		}
		if (lastPos < str.length()) {
			/*astr.append(str.substring(lastPos, str.length()));*/
		}
		/*System.out.println("a===>"+a);*/
		return a;
	}

	// 握手认证
	public void sendAuth(String str) {
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				log.info("DS-->EMH:sendAuth:" + socket.getInetAddress() + ":ITHIRD/"
						+ str);
				out.print("ITHIRD/"+str);
				out.flush();
				
				sendRegister();
				//TestData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		} else {
			log.info("====Timer:emh socket closed!!====");
		}
	}

	// 發送註冊請求
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
		/*login.setMcd(map);*/
		Map<String, Object> loginMap = new HashMap<String, Object>();
		loginMap.put("account", login.getAccount());
		loginMap.put("mcd", login.getMcd());
		loginMap.put("name", login.getName());
		loginMap.put("password", login.getPassword());
		loginMap.put("type", login.getType());
		loginMap.put("usertype", login.getUsertype());
		// 封装json数据json.Encode(list)
		String jsonstr = json.Encode(loginMap);
		log.info("DS-->EMH:Register:" + socket.getInetAddress() + ":" + jsonstr+"\n");
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				out.print(jsonstr+"\n");
				out.flush();
				TestData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		} else {
			log.info("====Timer:emh socket closed!!====");
		}
	}

	// 测点表数据
	public void TestData() {
	/*	{"type":15,"mcd":{"account":"","errMsg":"",
			"msgsequence":0,"operate":0,"optResult":false,
			"projectId":"","range":"0"},"devices":[]}\n
*/
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mcdMap = new HashMap<String, Object>();
		map.put("type", 15);
		/*map.put("devices","[]");*/
		/*mcdMap.put("account","");*/
		mcdMap.put("errMsg","");
		/*mcdMap.put("msgsequence", 0);*/
		mcdMap.put("operate", 2);
		/*mcdMap.put("optResult", false);
		mcdMap.put("projectId","");*/
		mcdMap.put("range", 0);
		map.put("mcd", mcdMap);
		String jsonstr = json.Encode(map);
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				log.info("DS-->EMH:TestData:" + socket.getInetAddress() + ":" + jsonstr+"\\n");
				out.print(jsonstr+"\n");
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

	public static String getEndStr() {
		return endStr;
	}

	public static void setEndStr(String endStr) {
		TcpClient.endStr = endStr;
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
					out.print("~IMCP iCore Connection Pulse~\n");
					out.flush();
					log.info("DS->EMH:Heart:~IMCP iCore Connection Pulse~");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		} else {
			log.info("====Timer:socket closed!!====");
		}
	}
}
