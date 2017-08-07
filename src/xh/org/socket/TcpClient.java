package xh.org.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javassist.expr.NewArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.Base64Util;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmhAlarmBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.SensorBean;
import xh.mybatis.service.EmhService;
import xh.org.listeners.TcpClientListenner;

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
	private static String endStr = "";

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
						recvStr += new String(c, "UTF-8");

						String[] recvStrs = recvStr.split("\n");
						for (int i = 0; i < recvStrs.length; i++) {
							if (i == recvStrs.length - 1) {
								if (isPackageFull(recvStrs[i]) == 0) {
									handler(recvStrs[i]);
									recvStr = "";
								} else {
									recvStr = recvStrs[i];
								}
							} else {
								handler(recvStrs[i]);
							}
						}

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
	public void handler(String string) {
		if(!string.contains("IMCP")){
			log.info("DS<--EMH:" + socket.getInetAddress() + ":" + string);
		}
		
		try {
			Map<String, Object> recvMap = new HashMap<String, Object>();
			if (string.contains("{") || string.contains("}")) {

				recvMap = GsonUtil.json2Object(string, Map.class);
				if (recvMap.get("type") != null) {
					int type = (int) funUtil.StringToFloat(recvMap.get("type")
							.toString());
					log.info("DS<--EMH:type:" + type);
					switch (type) {
					case 2:// 登录认证
						LoginResponse(recvMap);
						break;
					case 5:// 实时数据
						RealTimeData(recvMap);
						break;
					case 6:// 实时告警表
						RealTimeAlarm(recvMap);
						break;
					case 15:// 测点配置表
						RealTimeAgent(recvMap);
						break;
					case 31:// 状态
						RealTimeStatus(recvMap);
						break;
					case 58:// 实时统计
						RealTimeCount(recvMap);
						break;

					default:
						break;
					}
				}
			} else {
				if (string.length() == 8) {
					sendAuth(string + "\n");
				}
				if (string.contains("IMCP")) {
					/*log.info("DS<--EMH:Heart:" + socket.getInetAddress() + ":"
							+ string);*/
				}
			}

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

	// 登录认证
	public void LoginResponse(Map<String, Object> recvMap) {
		@SuppressWarnings("unchecked")
		Map<String, Object> mcdMap = (Map<String, Object>) recvMap.get("mcd");
		if (mcdMap.get("optResult").toString().equals("true")) {
			log.info("DS<--EMH:LoginResponse:登录认证通过");
			if (heartTimer == null) {
				heartTimer = new Timer();
				try {
					heartTimer.schedule(new HeartBeat(socket), 10000, 10000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			log.info("DS<--EMH:LoginResponse:登录认证失败!" + mcdMap.get("errMsg"));
			try {
				socket.close();
				connected = false;
				sleep(5000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	// 实时数据
	public void RealTimeData(Map<String, Object> recvMap) {
		@SuppressWarnings({ "unchecked", "unused" })
		
		List<Map<String, Object>> rtDataList = (List<Map<String, Object>>) recvMap
				.get("rtdata");
		
		for (Map<String, Object> map : rtDataList) {
			RtDataStruct rtdata = new RtDataStruct();
			rtdata.setUuid(map.get("uuid").toString());
			rtdata.setDnt(map.get("dnt").toString());
			@SuppressWarnings("unchecked")
			Map<String, Object> svmap = (Map<String, Object>) map.get("sv");
			SV sv = new SV();
			sv.setValueTitle(svmap.get("valueTitle").toString());
			sv.setValueData(svmap.get("valueData").toString());
			sv.setValueType((int) funUtil.StringToFloat(svmap.get("valueType")
					.toString()));
			rtdata.setSv(sv);
			log.info("DS<--EMH:RealTimeData:" + rtdata.toString());
			
			SensorBean bean=new SensorBean();
			bean.setDeviceId(rtdata.getUuid());
			bean.setSingleName(sv.getValueTitle());
			bean.setSingleValue(Base64Util.decode(sv.getValueData()));
			EmhService.updateDeviceValue(bean);
			

		}
	}

	// 实时告警
	public void RealTimeAlarm(Map<String, Object> recvMap) {
		@SuppressWarnings({ "unchecked", "unused" })
		List<Map<String, Object>> rtEventList = (List<Map<String, Object>>) recvMap
				.get("rtevent");
		for (Map<String, Object> map : rtEventList) {
			RtEventStruct rt = new RtEventStruct();
			rt.setUuid(map.get("uuid").toString());
			rt.setDnt(map.get("dnt").toString());
			rt.setDescription(map.get("description").toString());
			rt.setEt((int) funUtil.StringToFloat(map.get("et").toString()));
			rt.setLevel((int) funUtil
					.StringToFloat(map.get("level").toString()));
			rt.setState_alarm((int) funUtil.StringToFloat(map
					.get("state_alarm").toString()));
			rt.setCreateTime(funUtil.nowDate());
			log.info("DS<--EMH:RealTimeAlarm:" + rt.toString());
			
			/*新增告警记录*/
			EmhService.deviceAlarm(rt);
			SensorBean bean=new SensorBean();
			bean.setDeviceId(rt.getUuid());
			bean.setState_alarm(rt.getState_alarm());
			/*更新设备告警类型*/
			EmhService.updateDeviceStateAlarm(bean);
			
			

		}
	}

	// 状态消息
	public void RealTimeStatus(Map<String, Object> recvMap) {
		@SuppressWarnings({ "unchecked", "unused" })
		List<Map<String, Object>> rtStatusList = (List<Map<String, Object>>) recvMap
				.get("rtevent");
		for (Map<String, Object> map : rtStatusList) {
			RtStatusStruct rt=new RtStatusStruct();
			rt.setAgentip(map.get("agentip").toString());
			rt.setState((int)funUtil.StringToFloat(map.get("state").toString()));
			rt.setState_alarm((int)funUtil.StringToFloat(map.get("state_alarm").toString()));
			rt.setStattype((int)funUtil.StringToFloat(map.get("stattype").toString()));
			rt.setUuid(map.get("uuid").toString());
			log.info("DS<--EMH:RealTimeStatus:" + rt.toString());
			SensorBean bean=new SensorBean();
   		    bean.setDeviceId(rt.getUuid());
   		    bean.setStatus(rt.getState());
   		    bean.setState_alarm(rt.getState_alarm());
   		    EmhService.updateAgentDeviceState(bean);

		}
	}
	// 实时统计
	public void RealTimeCount(Map<String, Object> recvMap) {
		@SuppressWarnings({ "unchecked", "unused" })
		List<Map<String, Object>> rtCountList = (List<Map<String, Object>>) recvMap
				.get("rtstatistics");
		for (Map<String, Object> map : rtCountList) {
			log.info("DS<--EMH:RealTimeCount:" + map);

		}
	}
	// 测点配置表
	public void RealTimeAgent(Map<String, Object> recvMap) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> agentsList = (List<Map<String, Object>>) recvMap.get("agents");
		
		if(agentsList!=null){
		
         for (Map<String, Object> map : agentsList) {
        	 AgentDataStruct agent=new AgentDataStruct();
        	 agent.setUuid(map.get("uuid").toString());
        	 agent.setName(map.get("name").toString());
        	 agent.setStatus((Boolean) map.get("status"));
        	 agent.setState_alarm((int)funUtil.StringToFloat(map.get("state_alarm").toString()));
        	 EmhService.agent(agent);
        	 Devices device=new Devices();
        	 @SuppressWarnings("unchecked")
			List<Map<String, Object>> devicesList = (List<Map<String, Object>>) map.get("devices");
        	 for (Map<String, Object> map2 : devicesList) {
        		 device.setUuid(map2.get("uuid").toString());
            	 device.setName(map2.get("name").toString());
            	 device.setStatus((Boolean) map2.get("status"));
            	 device.setState_alarm((int)funUtil.StringToFloat(map2.get("state_alarm").toString()));
            	 @SuppressWarnings("unchecked")
				List<Map<String, Object>> spotsList = (List<Map<String, Object>>) map2.get("spots");
            	 log.info("DS<--EMH:RealTimeAgent:agent:" + agent.toString());
        		 log.info("DS<--EMH:RealTimeAgent:device:" + device.toString());
        		 
        		 SensorBean bean=new SensorBean();
        		 bean.setBsId(agent.getUuid());
        		 bean.setDeviceId(device.getUuid());
        		 bean.setDeviceName(device.getName());
        		 bean.setStatus(device.isStatus()?1:0);
        		 bean.setState_alarm(device.getState_alarm());
        		 EmhService.agentDevice(bean);
        		 
        		 
            	 if(spotsList!=null){
            		 Spots spots=new Spots();
                	 for (Map<String, Object> map3 : spotsList) {
                		 spots.setUuid(map3.get("uuid").toString());
                		 spots.setName(map3.get("name").toString());
                		 spots.setState_alarm((int)funUtil.StringToFloat(map3.get("state_alarm").toString()));
                		
                		 log.info("DS<--EMH:RealTimeAgent:spots:" + spots.toString());
    				}
            	 }
            	
			 }     

            }	
		}

	}

	// 完整包判断
	public static int isPackageFull(String str) {
		int lastPos = 0;
		int a = 0;
		StringBuffer astr = new StringBuffer();
		if (str.length() == 0) {
			return -1;
		}
		if (!str.contains("{") || !str.contains("{")) {
			return 0;
		}
		for (int i = 0; i < str.length(); i++) {
			if (String.valueOf(str.charAt(i)).equals("{")) {
				a++;
			} else if (String.valueOf(str.charAt(i)).equals("}")) {
				a--;
				if (a == 0) {
					/* astr.append(str.substring(lastPos, i + 1) + ";"); */
					lastPos = i + 1;
				}
			} else {

			}

		}
		if (lastPos < str.length()) {
			/* astr.append(str.substring(lastPos, str.length())); */
		}
		/* System.out.println("a===>"+a); */
		return a;
	}

	// 握手认证
	public void sendAuth(String str) {
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				log.info("DS-->EMH:sendAuth:" + socket.getInetAddress()
						+ ":ITHIRD/" + str);
				out.print("ITHIRD/" + str);
				out.flush();

				sendRegister();
				// TestData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		} else {
			log.info("====Timer:emh socket closed!!====");
		}
	}

	// 發送註冊請求
	public void sendRegister() {
		LoginStruct login = new LoginStruct();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operate", 1);
		login.setAccount(funUtil.readXml("emhLogin", "account"));
		login.setPassword(funUtil.readXml("emhLogin", "password"));
		/* login.setMcd(map); */
		Map<String, Object> loginMap = new HashMap<String, Object>();
		loginMap.put("account", login.getAccount());
		loginMap.put("mcd", login.getMcd());
		loginMap.put("name", login.getName());
		loginMap.put("password", login.getPassword());
		loginMap.put("type", login.getType());
		loginMap.put("usertype", login.getUsertype());
		// 封装json数据json.Encode(list)
		String jsonstr = json.Encode(loginMap);
		log.info("DS-->EMH:Register:" + socket.getInetAddress() + ":" + jsonstr
				+ "\n");
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				out.print(jsonstr + "\n");
				out.flush();
				deviceConfig();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		} else {
			log.info("====Timer:emh socket closed!!====");
		}
	}

	// 获取测点设备配置
	public void deviceConfig() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mcdMap = new HashMap<String, Object>();
		map.put("type", 15);
		mcdMap.put("errMsg", "");
		mcdMap.put("operate", 2);
		mcdMap.put("range", 0);
		map.put("mcd", mcdMap);
		String jsonstr = json.Encode(map);
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				log.info("DS-->EMH:deviceConfig:" + socket.getInetAddress() + ":"
						+ jsonstr + "\\n");
				out.print(jsonstr + "\n");
				out.flush();
				
				sleep(100000);
				searchDeviceValue();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			log.info(" socket error!");
		}

	}
	/*查询测点设备的值*/
	public void searchDeviceValue() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mcdMap = new HashMap<String, Object>();
		map.put("type", 5);
		mcdMap.put("errMsg", "");
		mcdMap.put("optResult", true);
		mcdMap.put("operate", 2);
		mcdMap.put("range", 0);
		map.put("mcd", mcdMap);
		String jsonstr = json.Encode(map);
		if (socket.isConnected()) {
			PrintWriter out;
			try {
				out = new PrintWriter(TcpClient.getSocket().getOutputStream());
				log.info("DS-->EMH:searchDeviceValue:" + socket.getInetAddress() + ":"
						+ jsonstr + "\\n");
				out.print(jsonstr + "\n");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		} else {
			log.info(" socket error!");
		}

	}

	// 事件类型
	public String ET(int a) {
		String ch = "";
		/*
		 * 1：系统（告警)事件 2：代理（告警)事件 3：设备（告警)事件 4：测点（告警)事件 5：设备状态变化 6：代理状态变化
		 * 7：操作日志事件
		 */
		switch (a) {
		case 1:
			ch = "系统（告警)";
			break;
		case 2:
			ch = "代理（告警)";
			break;
		case 3:
			ch = "设备（告警)";
			break;
		case 4:
			ch = "测点（告警)";
			break;
		case 5:
			ch = "设备状态变化";
			break;
		case 6:
			ch = "代理状态变化";
			break;
		case 7:
			ch = "操作日志";
			break;

		default:
			ch = "未知";
			break;
		}
		return ch;
	}

	// 事件状态
	public String StateAlarm(int a) {
		String ch = "";
		/*
		 * 1：超上限 2：超下限 3：临近上限 4：临近下限 5：数据异常,采集策略用 6：测点状态变化 7：告警回复 8：代理和设备联机
		 * 9：代理和设备脱机 10：文本报警，用于特定场合在代理端直接返回报警的文本
		 */
		switch (a) {
		case 1:
			ch = "超上限";
			break;
		case 2:
			ch = "超下限";
			break;
		case 3:
			ch = "临近上限";
			break;
		case 4:
			ch = "临近下限";
			break;
		case 5:
			ch = "数据异常";
			break;
		case 6:
			ch = "测点状态变化";
			break;
		case 7:
			ch = "代理和设备联机";
			break;
		case 8:
			ch = "代理和设备脱机";
			break;
		case 9:
			ch = "告警恢复";
			break;
		case 10:
			ch = "文本报警";
			break;

		default:
			ch = "未知";
			break;
		}
		return ch;
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
					/*log.info("DS->EMH:Heart:~IMCP iCore Connection Pulse~");*/
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		} else {
			log.info("====Timer:socket closed!!====");
		}
	}
}
