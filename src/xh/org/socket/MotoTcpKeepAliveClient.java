package xh.org.socket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FunUtil;
import xh.protobuf.MotoCorba;
import xh.protobuf.RadioAddReqBean;


public class MotoTcpKeepAliveClient extends Thread {
	protected final Log log = LogFactory.getLog(MotoTcpKeepAliveClient.class);
	private String ip;
	private int port;
	private static Socket socket;
	private static int timeout = 10 * 1000;
	private static byte[] result;
	private static byte[] bufferFlag = {};
	private static int slot;
	private static long timeStart;
	private static String date;
	private static String fileName;
	private static String filePath;
	private static int comID = -1;
	private static int bufLen = -1;
	private static int usetime = -1;
	private static String callId;
	private static byte[] readBuf;
	private static int dataLen = 0;
	private static String callType;
	private static int m_calling = 0;
	private static int m_countin = 0;
	private static byte[] writeBuf = {};
	private static long time = 0;
	private static int m_rssi = 0;
	private static int muticastsrc_bsid;
	public static int cbsId=0;
	private static ArrayList colorList;
	private static HashMap<String, ArrayList> colorMap = new HashMap<String, ArrayList>();
	private static ArrayList<HashMap<String, Object>> callList = new ArrayList<HashMap<String, Object>>();
	private static ArrayList<HashMap<String, Object>> bsInfoList = new ArrayList<HashMap<String, Object>>();
	private static HashMap<String, Object> startTimeMap = new HashMap<String, Object>();
	private static HashMap<String, Map> callMap = new HashMap<String, Map>();
	private static HashMap<String, String> rssi_map = new HashMap<String, String>();

	private boolean connected = false;
	private NetDataTypeTransform dd = new NetDataTypeTransform();

	public enum BsControlType {
		STATUS, // 获取状态
		REBOOT, // 重启系统
		INTERFERE, // 干扰
		RSSI, // 获取RSSI
		SLEEP, // 基站联网/脱网
		GPSDATA, // 获取GPS
		PWSET, // 功率设置(content为功率值,范围1-50)
		PWRELOAD
		// 功率标定
	}

	public void run() {

		receive();
	}

	public void connect() {
		if (socket == null || socket.isClosed() || !socket.isConnected()) {
			socket = new Socket();
			ip = FunUtil.readXml("moto", "ip");
			port = Integer.parseInt(FunUtil.readXml("moto", "port"));
			InetSocketAddress addr = new InetSocketAddress(ip, port);
			try {
				socket.connect(addr, timeout);
				socket.setTcpNoDelay(true);
			} catch (IOException e) {
			}
			try {
				socket.setKeepAlive(true);
			} catch (SocketException e) {
				/* log.info("KeepAlive SocketException"); */
			}
			try {
				socket.setSoTimeout(timeout);
			} catch (SocketException e) {
				/* log.info("timeout SocketException"); */
				//告警类型：1：断站；2：中心；3：交换；4：温度;5:gps失锁；6：反向功率过大；7：交流；8：功率
				log.debug("moto tcp recvData timeout 50s,socket is closed and reconnecting!");
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				connected = false;
			}

		}
	}

	public void receive() {
		InputStream input = null;
		NetDataTypeTransform dd = new NetDataTypeTransform();

		while (!connected) {

			try {
				System.setProperty("java.net.preferIPv4Stack", "true");
				connect();
				input = socket.getInputStream();
				if (socket.isConnected()) {
					connected = true;
					log.debug("moto TCP Connected success!!");
					
					RadioAddReqBean bean=new RadioAddReqBean();
					bean.setRadioID("12345");
					bean.setRadioReferenceID("12345");
					bean.setRadioSerialNumber("ggtt444");
					bean.setSecurityGroup("亚光");
					SendData.RadioAddReq(bean);

					/*try {
						Timer timer = new Timer();
						timer.schedule(new HeartBeat(socket), 2000, 3 * 1000);
					} catch (IOException e) {
						log.info("Timer");
					}*/
				}
				// read body
				byte[] buf = new byte[4096];// 收到的包字节数组
				byte[] bufH = new byte[2];// 收到的包字节数组
				byte[] realBuf = new byte[10240];

				while (connected) {
					int len = input.read(buf);
					int recvLen = len;
					if (len > 0 && len + writeBuf.length >= 4) {
						readBuf = new byte[len + writeBuf.length];
						System.arraycopy(writeBuf, 0, readBuf, 0,
								writeBuf.length);
						System.arraycopy(buf, 0, readBuf, writeBuf.length, len);
						len = len + writeBuf.length;

						System.arraycopy(readBuf, 0, bufH, 0, 2);
						String packageHeader = HexString(bufH);
						
						/*log.info("TCP数据：->writebuf="+func.BytesToHexS(writeBuf));
						log.info("TCP数据：->readBuf="+func.BytesToHexS(readBuf));*/
						if (!packageHeader.equals("c4d7")) {
							log.error("SocketError1111:>>!c4d7");
							log.info(packageHeader);
							this.writeBuf=new byte[0];
							
							/* writeBuf=null; */
						} else {
							int length = dd.BigByteArrayToShort(readBuf, 2);
							if (length + 4 > len) {
								byte[] temp = new byte[writeBuf.length];
								System.arraycopy(writeBuf, 0, temp, 0,
										writeBuf.length);
								writeBuf = new byte[recvLen + temp.length];
								System.arraycopy(temp, 0, writeBuf, 0,
										temp.length);
								System.arraycopy(buf, 0, writeBuf, temp.length,
										recvLen);
								// break;
							} else if (length + 4 == len) {
								int comId = dd.BigByteArrayToShort(readBuf, 4);
								String callid = dd.ByteArraytoString(readBuf,
										6, 8);
								comID = comId;

								callId = dd.ByteArraytoString(readBuf, 6, 8);
								handler(comId, length + 4, callid, readBuf, len);
							} else if (len > length + 4) {

								for (int i = 0; i <= len;) {

									System.arraycopy(readBuf, i, realBuf, 0,
											len - i);
									length = dd.BigByteArrayToShort(realBuf, 2);
									dataLen = length + 4;
									System.arraycopy(realBuf, 0, bufH, 0, 2);
									packageHeader = HexString(bufH);
									if (!packageHeader.equals("c4d7")) {
										log.error("SocketError2:>>!c4d7");
										this.writeBuf=new byte[0];
									}
									if (dataLen > len - i) {
										writeBuf = new byte[len - i];
										System.arraycopy(realBuf, 0, writeBuf,
												0, len - i);
										break;
									} else {
										int comId = dd.BigByteArrayToShort(
												realBuf, 4);
										String callid = dd.ByteArraytoString(
												realBuf, 6, 8);
										length = dd.BigByteArrayToShort(
												realBuf, 2);
										comID = comId;

										callId = dd.ByteArraytoString(realBuf,
												6, 8);
										handler(comId, dataLen, callid,
												realBuf, len);
										i += length + 4;
										writeBuf = null;
									}

								}
							}
						}

					} else {
						socket.close();
						log.debug("====TCP connection is closed!!====");
						log.debug("====reconnection now!!====");
						connected = false;
					}
				}

			} catch (SocketException e) {
				log.debug("TCP connection trying");
				if (socket.isConnected() || socket != null) {
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					connected = false;
				}
				try {
					sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (UnknownHostException e) {
			} catch (IOException e) {
				log.debug("recvData timeout 10s,socket is closed and reconnecting!");
				try {
					socket.close();

				} catch (IOException e1) {
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

	public void handler(int comId, int len, String callid, byte[] buf,
			int length) throws Exception {

		try {
			switch (comId) {
			case 5:
				RadioAddRsp(len,buf);// 呼叫信息
				break;
			default:
				break;
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
		}catch(NoSuchMethodError e){
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
	}

	
	
	public void RadioAddRsp(int len, byte[] buf) {
		result = new byte[len - 26];
		System.arraycopy(buf, 24, result, 0, len - 26);
		MotoCorba.RadioAddRsp resp=null;
		try {
			resp=MotoCorba.RadioAddRsp.parseFrom(result);
			xh.protobuf.RadioAddRspBean bean=new xh.protobuf.RadioAddRspBean();
			bean.setResult(resp.getResult());
			bean.setDetail(resp.getResult());
			log.info("MOTO->DS:"+bean.toString());
			
		}catch (Error e) {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public static byte[] getBufferFlag() {
		return bufferFlag;
	}

	public static void setBufferFlag(byte[] bufferFlag) {
		MotoTcpKeepAliveClient.bufferFlag = bufferFlag;
	}

	public static String getFilePath() {
		return filePath;
	}

	public static void setFilePath(String filePath) {
		MotoTcpKeepAliveClient.filePath = filePath;
	}

	public static long getTimeStart() {
		return timeStart;
	}

	public static void setTimeStart(long timeStart) {
		MotoTcpKeepAliveClient.timeStart = timeStart;
	}

	public static String getDate() {
		return date;
	}

	public static void setDate(String date) {
		MotoTcpKeepAliveClient.date = date;
	}

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		MotoTcpKeepAliveClient.fileName = fileName;
	}

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		MotoTcpKeepAliveClient.socket = socket;
	}

	public static byte[] getResult() {
		return result;
	}

	public static void setResult(byte[] result) {
		MotoTcpKeepAliveClient.result = result;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public MotoTcpKeepAliveClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public static int getComID() {
		return comID;
	}

	public static void setComID(int comID) {
		MotoTcpKeepAliveClient.comID = comID;
	}

	public static int getBufLen() {
		return bufLen;
	}

	public static void setBufLen(int bufLen) {
		MotoTcpKeepAliveClient.bufLen = bufLen;
	}

	public static int getUsetime() {
		return usetime;
	}

	public static void setUsetime(int usetime) {
		MotoTcpKeepAliveClient.usetime = usetime;
	}

	public static String getCallId() {
		return callId;
	}

	public static void setCallId(String callId) {
		MotoTcpKeepAliveClient.callId = callId;
	}

	public static String getCallType() {
		return callType;
	}

	public static void setCallType(String callType) {
		MotoTcpKeepAliveClient.callType = callType;
	}

	public static byte[] getWriteBuf() {
		return writeBuf;
	}

	public static void setWriteBuf(byte[] writeBuf) {
		MotoTcpKeepAliveClient.writeBuf = writeBuf;
	}

	public static long getTime() {
		return time;
	}

	public static void setTime(long time) {
		MotoTcpKeepAliveClient.time = time;
	}

	public static ArrayList<HashMap<String, Object>> getCallList() {
		return callList;
	}

	public static void setCallList(ArrayList<HashMap<String, Object>> callList) {
		MotoTcpKeepAliveClient.callList = callList;
	}

	public static HashMap<String, Object> getStartTimeMap() {
		return startTimeMap;
	}

	public static void setStartTimeMap(HashMap<String, Object> startTimeMap) {
		MotoTcpKeepAliveClient.startTimeMap = startTimeMap;
	}

	public static ArrayList<HashMap<String, Object>> getBsInfoList() {
		return bsInfoList;
	}

	public static void setBsInfoList(
			ArrayList<HashMap<String, Object>> bsInfoList) {
		MotoTcpKeepAliveClient.bsInfoList = bsInfoList;
	}

	public static ArrayList getColorList() {
		return colorList;
	}

	public static void setColorList(ArrayList colorList) {
		MotoTcpKeepAliveClient.colorList = colorList;
	}

	public static HashMap<String, Map> getCallMap() {
		return callMap;
	}

	public static void setCallMap(HashMap<String, Map> callMap) {
		MotoTcpKeepAliveClient.callMap = callMap;
	}

	public static int getM_rssi() {
		return m_rssi;
	}

	public static void setM_rssi(int m_rssi) {
		MotoTcpKeepAliveClient.m_rssi = m_rssi;
	}

	public static HashMap<String, String> getRssi_map() {
		return rssi_map;
	}

	public static void setRssi_map(HashMap<String, String> rssi_map) {
		MotoTcpKeepAliveClient.rssi_map = rssi_map;
	}

	public static int getM_calling() {
		return m_calling;
	}

	public static void setM_calling(int m_calling) {
		MotoTcpKeepAliveClient.m_calling = m_calling;
	}

	public static HashMap<String, ArrayList> getColorMap() {
		return colorMap;
	}

	public static void setColorMap(HashMap<String, ArrayList> colorMap) {
		MotoTcpKeepAliveClient.colorMap = colorMap;
	}

	public static int getM_countin() {
		return m_countin;
	}

	public static void setM_countin(int m_countin) {
		MotoTcpKeepAliveClient.m_countin = m_countin;
	}

}

