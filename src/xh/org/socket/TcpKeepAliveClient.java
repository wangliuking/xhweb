package xh.org.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.TalkGroupBean;
import xh.mybatis.service.TalkGroupService;


public class TcpKeepAliveClient extends Thread {
	protected final Log log = LogFactory.getLog(TcpKeepAliveClient.class);
	private String ip;
	private int port;
	private static Socket socket;
	private static int timeout = 600 * 1000;
	private static byte[] result;
	private static byte[] bufferFlag = {};
	private static int slot;
	private static long timeStart;
	private static int comID = -1;
	private static int bufLen = -1;
	private static int usetime = -1;
	private static String callId;
	private static byte[] readBuf;
	private static int dataLen = 0;
	private static byte[] writeBuf = {};
	private static Map<String,Object> ucmGroupMap=new HashMap<String, Object>();
	private static Map<String,Object> ucmRadioUserMap=new HashMap<String, Object>();

	private boolean connected = false;
	private NetDataTypeTransform dd = new NetDataTypeTransform();


	public void run() {

		receive();
	}

	public void connect() {
		if (socket == null || socket.isClosed() || !socket.isConnected()) {
			socket = new Socket();
			ip = FunUtil.readXml("ucm", "ip");
			port = Integer.parseInt(FunUtil.readXml("ucm", "port"));
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
				log.info("recvData timeout 10s,socket is closed and reconnecting!");

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
					log.info("TCP Connected success!!");
					

					/*try {
						Timer timer = new Timer();
						timer.schedule(new HeartBeat(socket), 2000, 3 * 1000);
					} catch (IOException e) {
						log.info("Timer");
					}*/
				}
				// read body
				byte[] buf = new byte[40960];// 收到的包字节数组
				byte[] bufH = new byte[2];// 收到的包字节数组
				byte[] realBuf = new byte[10240];

				while (connected) {
					int len = input.read(buf);
					int recvLen = len;
					
					/*if(len > 0 && len + writeBuf.length >= 4) {
						
					
					byte[] buf2=new byte[len];
					System.arraycopy(buf, 0, buf2, 0,len);
					System.arraycopy(buf2, 0, bufH, 0, 2);
		            int length=dd.BigByteArrayToShort(buf2,4);
		            int commId=dd.BigByteArrayToShort(buf2,6);
		            int status=dd.SmallByteArrayToInt(buf, 20);
		            
		            log.info("length---->"+length);
		            log.info("commId--->"+commId);
		            log.info("status--->"+status);
					log.info("收到的数据："+FunUtil.BytesToHexS(buf2));
					
					handler(commId,buf2);
					}else{
						socket.close();
						log.info("====TCP connection is closed!!====");
						log.info("====reconnection now!!====");
					
						connected = false;
					}*/
					byte[] buf2=new byte[len];
					System.arraycopy(buf, 0, buf2, 0,len);
					
					log.info("收到的数据："+FunUtil.BytesToHexS(buf2));
					log.info("收到的数据：len="+len);
					
					
					if (len > 0 && len + writeBuf.length >= 4) {
						readBuf = new byte[len + writeBuf.length];
						System.arraycopy(writeBuf, 0, readBuf, 0,
								writeBuf.length);
						System.arraycopy(buf, 0, readBuf, writeBuf.length, len);
						len = len + writeBuf.length;

						System.arraycopy(readBuf, 0, bufH, 0, 2);
						String packageHeader = HexString(bufH);
						if (!packageHeader.equals("c4d7")) {
							log.error("SocketError1111:>>!c4d7");
							log.info(packageHeader);
							this.writeBuf=new byte[0];
						} else {
							int length = dd.BigByteArrayToShort(readBuf, 4);
							log.info("数据解析长度="+length);
							if (length + 6 > len) {
								log.info("接收数据长度="+length+"小于一包的长度="+len);
								byte[] temp = new byte[writeBuf.length];
								System.arraycopy(writeBuf, 0, temp, 0,
										writeBuf.length);
								writeBuf = new byte[recvLen + temp.length];
								System.arraycopy(temp, 0, writeBuf, 0,
										temp.length);
								System.arraycopy(buf, 0, writeBuf, temp.length,
										recvLen);
								// break;
							} else if (length + 6 == len) {
								log.info("刚好一包数据长度");
								
					            int commId=dd.BigByteArrayToShort(readBuf,6);
					           /* int status=dd.SmallByteArrayToInt(buf, 20);*/
								/*int comId = dd.BigByteArrayToShort(readBuf, 4);
								String callid = dd.ByteArraytoString(readBuf,
										6, 8);*/
								comID = commId;

								/*callId = dd.ByteArraytoString(readBuf, 6, 8);*/
								handler(commId, length + 6,readBuf, len);
							} else if (len > length + 6) {
								log.info("接收数据长度="+length+"大于一包的长度="+len);
								for (int i = 0; i <= len;) {

									System.arraycopy(readBuf, i, realBuf, 0,
											len - i);
									length = dd.BigByteArrayToShort(realBuf, 4);
									dataLen = length + 6;
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
										/*int comId = dd.BigByteArrayToShort(
												realBuf, 4);
										String callid = dd.ByteArraytoString(
												realBuf, 6, 8);
										length = dd.BigByteArrayToShort(
												realBuf, 2);
										comID = comId;

										callId = dd.ByteArraytoString(realBuf,
												6, 8);*/
										
							            int commId=dd.BigByteArrayToShort(readBuf,6);
										handler(commId, dataLen,realBuf, len);
										i += length + 6;
										writeBuf=null;
									}

								}
							}
						}

					} else {
						socket.close();
						log.info("====TCP connection is closed!!====");
						log.info("====reconnection now!!====");
					
						connected = false;
					}
				}
			} catch (SocketException e) {
				log.info("TCP connection trying");
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
				log.info("recvData timeout 10s,socket is closed and reconnecting!");
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

	public void handler(int comId,int len,byte[] buf,int length) throws Exception {
		
		
		
		
		log.info("======================"+comId+"=============================");
		log.info("DS<-ETRA:comId="+comId+";len="+len+";length="+length);
		log.info("DS<-ETRA:"+FunUtil.BytesToHexS(buf));
		log.info("======================/"+comId+"=============================");
		
		

		try {
			switch (comId) {
			case 103://短信
				
				log.info("发送短信");
				sendsms(len,buf,length);
			    break;
			case 154://终端
				
				log.info("终端");
				radioUser(len,buf,length);
			    break;
			case 156://终端用户业务属性
				log.info("终端用户业务属性");
				break;
			case 160://通话组
				log.info("通话组");
				talkGroup(len,buf,length);
				break;
			case 162://通播组
				log.info("通播组");
				break;
			case 164://通话组、通播组业务属性
				log.info("通话组、通播组业务属性");
				break;
			case 166://状态集
				log.info("状态集");
				break;
			case 168://调度用户
				log.info("调度用户");
				break;
			case 170://调度用户业务属性
				log.info("调度用户业务属性");
				break;
			case 172://状态集单元
				log.info("状态集单元");
				break;
			case 174://遥毙
				log.info("摇启、遥毙");
				dgna(len,buf,length);
				break;
			case 176://遥毙
				
				log.info("动态重组");
				dgna(len,buf,length);
				break;
			case 178://终端用户业务属性有效站点
				log.info("终端用户业务属性有效站点");
				break;
				
			case 180://终端用户有效地域
				log.info("终端用户有效地域");
				break;
			case 182://通话组业务属性有效站点
				log.info("通话组业务属性有效站点");
				break;
			case 184://通话组有效地域
				log.info("通话组有效地域");
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

	// 转16进制
	public String HexString(byte[] src) {
		String str = "";
		int v1 = src[0] & 0xFF;
		int v2 = src[1] & 0xFF;
		str = Integer.toHexString(v1) + Integer.toHexString(v2);
		return str;
	}
	//解析短信会服
	public void sendsms(int len,byte[] buf,int length){
		int status=dd.SmallByteArrayToInt(buf, 26);
		String message="";
		if(status==0)
        {
        message="读成功，输出参数有效";
        }
        else if(status==1)
        {
       	 message="操作成功";
        }
        else if(status==2)
        {
       	 message="参数错误";
        }
        else if(status==3)
        {
       	 message="系统错误";
        }
        else if(status==4)
        {
       	 message="未知错误";
        }
        else{
       	 message=String.valueOf(status)+"操作失败！";
        }
		Map<String,Object> map=new HashMap<String, Object>();
		TalkGroupBean data=new TalkGroupBean();
		map.put("status", status);
		map.put("message", message);
   	    log.info("sendsms->map"+map);
		
		
	}
	//动态重组
	public void dgna(int len,byte[] buf,int length){
		int status=dd.SmallByteArrayToInt(buf, 16);
		backMessage(status);
		Map<String,Object> map=new HashMap<String, Object>();
		TalkGroupBean data=new TalkGroupBean();
		map.put("status", status);
		map.put("message", backMessage(status));
   	    log.info("dgna->map"+map);
		
		
	}
	//解析无线用户数据
	public void radioUser(int len,byte[] buf,int length){
		int status=dd.SmallByteArrayToInt(buf, 20);
		backMessage(status);
		Map<String,Object> map=new HashMap<String, Object>();
		RadioUserStruct user=new RadioUserStruct();
		map.put("status", status);
		map.put("message", backMessage(status));
		 user.setId(dd.SmallByteArrayToInt(buf,304));
       	 /*user.setName(dd.ByteArraytoString(buf, 308, 16));
       	 user.setAlias(dd.ByteArraytoString(buf, 324, 8));
       	 user.setMscId(dd.SmallByteArrayToInt(buf, 332));
       	 user.setVpnId(dd.SmallByteArrayToLong(buf, 336));
       	 user.setSn(dd.ByteArraytoString(buf, 344, 32));
       	 user.setCompany(dd.ByteArraytoString(buf, 376, 32));
       	 user.setType(dd.ByteArraytoString(buf, 408, 16));
       	 user.setEnabled(dd.SmallByteArrayToOneInt(buf, 424));
       	 user.setShortData(dd.SmallByteArrayToOneInt(buf, 425));
       	 user.setFullDuple(dd.SmallByteArrayToOneInt(buf, 426));
       	 user.setRadioType(dd.SmallByteArrayToOneInt(buf, 427));
       	 user.setAnycall(dd.SmallByteArrayToOneInt(buf, 428));
       	 user.setSaId(dd.SmallByteArrayToInt(buf, 429));
       	 user.setIaId(dd.SmallByteArrayToInt(buf, 433));
       	 user.setVaId(dd.SmallByteArrayToInt(buf,437));
       	 user.setRugId(dd.SmallByteArrayToInt(buf, 441));
       	 user.setPacketData(String.valueOf(dd.SmallByteArrayToOneInt(buf, 445)));
       	 user.setIp(dd.ByteArraytoString(buf, 446, 20));
       	 user.setPrimaryTGId(dd.SmallByteArrayToInt(buf, 466));
       	 user.setAmbienceMonitoring(String.valueOf(dd.SmallByteArrayToOneInt(buf, 470)));
       	 user.setAmbienceInitiation(String.valueOf(dd.SmallByteArrayToOneInt(buf, 471)));
       	 user.setDirectDial(dd.ByteArraytoString(buf, 472, 16));
       	 user.setPstnAccess(String.valueOf(dd.SmallByteArrayToOneInt(buf, 488)));
       	 user.setPabxAccess(String.valueOf(dd.SmallByteArrayToOneInt(buf, 489)));
       	 user.setClir(String.valueOf(dd.SmallByteArrayToOneInt(buf, 490)));
       	 user.setClirOverride(String.valueOf(dd.SmallByteArrayToOneInt(buf, 491)));
       	 user.setKilled(String.valueOf(dd.SmallByteArrayToOneInt(buf, 492)));
       	 user.setMsType(String.valueOf(dd.SmallByteArrayToOneInt(buf, 493)));
       	 user.toString();*/
        ucmRadioUserMap.put(String.valueOf(user.getId()), map);
        log.info("addRadioUser->id"+user.getId());
		log.info("ucmRadioUserMap->"+ucmRadioUserMap);
		
		
	}
	//解析通话组数据
	public void talkGroup(int len,byte[] buf,int length){
		int status=dd.SmallByteArrayToInt(buf, 20);
		backMessage(status);
		Map<String,Object> map=new HashMap<String, Object>();
		TalkGroupBean data=new TalkGroupBean();
		map.put("status", status);
		map.put("message", backMessage(status));
   	    data.setTalkgroupID(dd.SmallByteArrayToInt(buf, 112));
   	    /*data.setE_name(dd.ByteArraytoString(buf, 116, 16));
   	    data.setE_alias(dd.ByteArraytoString(buf, 132, 8));
   	    data.setE_mscId(dd.SmallByteArrayToInt(buf, 140));
   	    data.setE_vpnId(dd.SmallByteArrayToInt(buf, 144));
   	    data.setE_said(dd.SmallByteArrayToInt(buf, 148));
   	    data.setE_iaid(dd.SmallByteArrayToInt(buf, 152));
   	    data.setE_vaid(dd.SmallByteArrayToInt(buf, 156));
   	    data.setE_preempt(dd.SmallByteArrayToOneInt(buf, 160));
   	    data.setE_radioType(String.valueOf(dd.SmallByteArrayToOneInt(buf, 161)));
   	    data.setE_regroupable(String.valueOf(dd.SmallByteArrayToOneInt(buf, 162)));
   	    data.setE_enabled(dd.SmallByteArrayToOneInt(buf, 163));
   	    data.setE_directDial(dd.ByteArraytoString(buf, 164, 16));*/
   	    log.info("GroupData->id"+data.getTalkgroupID());

		ucmGroupMap.put(String.valueOf(data.getTalkgroupID()), map);
		
		log.info("ucmGroupMap->"+ucmGroupMap);
		
		
	}
	
	public String backMessage(int status){
		String message="";
		
		switch (status) {
		case 1:
			message="操作成功";
			break;
		case 2:
			message="数据库执行 SQL 语句成功但没任何影响";
			break;
		case 3:
			message="用户 id 与 masterId 相同/无效用户号码/非法参数";
			break;
		case 4:
			message="输入参数不合法/无效组号码";
			break;
		case 5:
			message="数据库执行 SQL 语句失败";
			break;
		case 6:
			message="数据库绑定或获取失败";
			break;
		case 7:
			message="用户名或密码错误";
			break;
		case 8:
			message="添加操作员失败";
			break;
		case 33:
			message="写数据库时 ip 重复";
			break;
		case 35:
			message="号码字冠不存在, SSIPrefix 未设置";
			break;
		case 36:
			message="删除限制：从属于多个交换中心";
			break;
		case 209:
			message="没有 VPN 权限";
			break;

		default:
			message="code:"+status+";error!";
			break;
		}
		log.info("操作结果->"+message);
		return message;
		
	}

	public static byte[] getBufferFlag() {
		return bufferFlag;
	}

	public static void setBufferFlag(byte[] bufferFlag) {
		TcpKeepAliveClient.bufferFlag = bufferFlag;
	}

	public static long getTimeStart() {
		return timeStart;
	}

	public static void setTimeStart(long timeStart) {
		TcpKeepAliveClient.timeStart = timeStart;
	}


	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		TcpKeepAliveClient.socket = socket;
	}

	public static byte[] getResult() {
		return result;
	}

	public static void setResult(byte[] result) {
		TcpKeepAliveClient.result = result;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public TcpKeepAliveClient(String ip, int port) {
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
		TcpKeepAliveClient.comID = comID;
	}

	public static int getBufLen() {
		return bufLen;
	}

	public static void setBufLen(int bufLen) {
		TcpKeepAliveClient.bufLen = bufLen;
	}

	public static int getUsetime() {
		return usetime;
	}

	public static void setUsetime(int usetime) {
		TcpKeepAliveClient.usetime = usetime;
	}

	public static String getCallId() {
		return callId;
	}

	public static void setCallId(String callId) {
		TcpKeepAliveClient.callId = callId;
	}


	public static byte[] getWriteBuf() {
		return writeBuf;
	}

	public static void setWriteBuf(byte[] writeBuf) {
		TcpKeepAliveClient.writeBuf = writeBuf;
	}

	public static Map<String, Object> getUcmGroupMap() {
		return ucmGroupMap;
	}

	public static void setUcmGroupMap(Map<String, Object> ucmGroupMap) {
		TcpKeepAliveClient.ucmGroupMap = ucmGroupMap;
	}

	public static Map<String, Object> getUcmRadioUserMap() {
		return ucmRadioUserMap;
	}

	public static void setUcmRadioUserMap(Map<String, Object> ucmRadioUserMap) {
		TcpKeepAliveClient.ucmRadioUserMap = ucmRadioUserMap;
	}


}

class HeartBeat extends TimerTask {
	private Socket socket = null;
	private TcpKeepAliveClient tcp;
	protected final Log log = LogFactory.getLog(HeartBeat.class);

	public HeartBeat(Socket socket) throws Exception {
		this.socket = socket;

	}

	public void run() {
		NetDataTypeTransform dd = new NetDataTypeTransform();
		// ===============================protoco buf 数据

	

		// ====================================
		// 发送数据，应该获取Socket流中的输出流。
		MessageStruct mStruct = new MessageStruct();
		
	}
}
