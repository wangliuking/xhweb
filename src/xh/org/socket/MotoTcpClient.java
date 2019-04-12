package xh.org.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import xh.constant.ConstantMap;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.RadioBean;
import xh.mybatis.bean.RadioUserMotoBean;
import xh.mybatis.bean.TalkGroupBean;
import xh.mybatis.service.RadioService;
import xh.mybatis.service.RadioUserService;
import xh.mybatis.service.TalkGroupService;
import xh.protobuf.MotoCorba;
import xh.protobuf.RadioUserBean;


public class MotoTcpClient extends Thread {
	protected final Log log = LogFactory.getLog(MotoTcpClient.class);
	private String ip;
	private int port;
	private static Socket socket;
	private static int timeout = 100 * 1000;
	private static byte[] result;
	private static byte[] bufferFlag = {};
	private static int slot;
	private static long timeStart;
	private static int comID = -1;
	private static int bufLen = -1;
	private static String callId;
	private static byte[] readBuf;
	private static int dataLen = 0;
	private static byte[] writeBuf = {};

	private boolean connected = false;
	private NetDataTypeTransform dd = new NetDataTypeTransform();


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
				/* log.debug("KeepAlive SocketException"); */
			}
			try {
				socket.setSoTimeout(timeout);
			} catch (SocketException e) {
				log.info("moto tcp recvData timeout ,socket is closed and reconnecting!");

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
					log.info("MOTO TCP CONNECTED SUCCESS!!");
					//GetAllRadio();
					/*timer.schedule(new , 2000);*/
					/*RadioBean bean=new RadioBean();
					bean.setRadioID("12345");
					bean.setRadioReferenceID("12345");
					bean.setRadioSerialNumber("ggtt444");
					bean.setSecurityGroup("亚光");
					SendData.RadioAddReq(bean);*/
					/*SendData.RadioUpdateReq(bean);
					SendData.RadioDelReq(bean);*/
					
					
				/*	required string RadioID	= 1;       
				  	required string RadioUserAlias	= 2;  
				  	required string SecurityGroup	= 3; 
				  	required string RadioUserCapabilityProfileAlias	= 4;
					optional string UserEnabled 	= 5; 
				  	optional string InterconnectEnabled	= 6; 
					optional string PacketDataEnabled	= 7; 
					optional string ShortDataEnabled	= 8; 
					optional string FullDuplexEnabled	= 9; */ 
					
					/*RadioUserBean bean2=new RadioUserBean();
					bean2.setRadioID("12345");
					bean2.setRadioUserAlias("2ssd");
					bean2.setSecurityGroup("亚光");
					bean2.setRadioUserCapabilityProfileAlias("dddd");
					bean2.setUserEnabled("1");
					bean2.setInterconnectEnabled("NO");
					bean2.setPacketDataEnabled("NO");
					bean2.setShortDataEnabled("YES");
					bean2.setFullDuplexEnabled("NO");*/
					
					/*SendData.RadioUserAddReq(bean2);
					SendData.RadioUserUpdateReq(bean2);
					SendData.RadioUserDelReq(bean2);*/
					
					
					
					
					
					
				}
				// read body
				byte[] buf = new byte[40960];// 收到的包字节数组
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
						
						log.debug("DS<-MOTO接收数据："+FunUtil.BytesToHexS(readBuf));

						System.arraycopy(readBuf, 0, bufH, 0, 2);
						String packageHeader = HexString(bufH);
						if (!packageHeader.equals("c4d7")) {
							log.error("SocketError1111:>>!c4d7");
							log.debug(packageHeader);
							this.writeBuf=new byte[0];
						} else {
							int length = dd.BigByteArrayToShort(readBuf, 2);
							log.debug("数据解析长度="+length);
							if (length + 4 > len) {
								log.debug("接收数据长度="+length+"小于一包的长度="+len);
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
								log.debug("刚好一包数据长度");
								
					            int commId=dd.BigByteArrayToShort(readBuf,8);
					           /* int status=dd.SmallByteArrayToInt(buf, 20);*/
								/*int comId = dd.BigByteArrayToShort(readBuf, 4);
								String callid = dd.ByteArraytoString(readBuf,6, 8);*/
								comID = commId;

								callId = dd.ByteArraytoString(readBuf, 10, 8);
								handler(commId, length + 4,readBuf, len,callId);
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
										/*int comId = dd.BigByteArrayToShort(
												realBuf, 4);
										String callid = dd.ByteArraytoString(
												realBuf, 6, 8);
										length = dd.BigByteArrayToShort(
												realBuf, 2);
										comID = comId;

										callId = dd.ByteArraytoString(realBuf,
												6, 8);*/
										callId = dd.ByteArraytoString(realBuf,10, 8);
										
							            int commId=dd.BigByteArrayToShort(realBuf,8);
										handler(commId, dataLen,realBuf, len,callId);
										i += length + 4;
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
				log.info("MOTO TCP connection trying");
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

	public void handler(int comId,int len,byte[] buf,int length,String callId) throws Exception {
		
		try {
			switch (comId) {
			case 3:
				RadioGetRsp(len,buf,callId);
				break;
			case 5:
				RadioAddRsp(len,buf,callId);
				break;
			case 7:
				RadioDelRsp(len,buf,callId);
				break;
			case 9:
				RadioUpdateRsp(len,buf,callId);
				break;
			case 11:
				RadioUserGetRsp(len,buf,callId);
				break;
			case 13:
				RadioUserAddRsp(len,buf,callId);
				break;
			case 15:
				RadioUserDelRsp(len,buf,callId);
				break;
			case 17:
				RadioUserUpdateRsp(len,buf,callId);
				break;
			case 200:
				ACK(len,buf,callId);
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
		}catch (Exception e) {
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
	public void RadioAddRsp(int len, byte[] buf,String callId) {
		result = new byte[len - 20];
		System.arraycopy(buf, 18, result, 0, len - 20);
		MotoCorba.RadioAddRsp resp=null;
		try {
			resp=MotoCorba.RadioAddRsp.parseFrom(result);
			xh.protobuf.RadioAddRspBean bean=new xh.protobuf.RadioAddRspBean();
			ConstantMap.getMotoResultMap().put(callId,resp.getResult());
			ConstantMap.getMotoResultMap().put(callId+"-info",resp.getDetail());
			bean.setResult(resp.getResult());
			bean.setDetail(resp.getDetail());
			log.debug("MOTO->DS[RadioAddRsp]:callId="+callId+";"+bean.toString());
			
		}/*catch (InvalidProtocolBufferException e) {
			log.debug("MOTO->DS:数据解析出错");
		} */catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void RadioDelRsp(int len, byte[] buf,String callId) {
		result = new byte[len - 20];
		System.arraycopy(buf, 18, result, 0, len - 20);
		MotoCorba.RadioDelRsp resp=null;
		try {
			resp=MotoCorba.RadioDelRsp.parseFrom(result);
			xh.protobuf.RadioDelRspBean bean=new xh.protobuf.RadioDelRspBean();
			ConstantMap.getMotoResultMap().put(callId,resp.getResult());
			ConstantMap.getMotoResultMap().put(callId+"-info",resp.getDetail());
			bean.setResult(resp.getResult());
			bean.setDetail(resp.getDetail());
			log.debug("MOTO->DS[RadioDelRsp]:callId="+callId+";"+bean.toString());
			
		}/*catch (InvalidProtocolBufferException e) {
			log.debug("MOTO->DS:数据解析出错");
		} */catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void RadioUpdateRsp(int len, byte[] buf,String callId) {
		result = new byte[len - 20];
		System.arraycopy(buf, 18, result, 0, len - 20);
		MotoCorba.RadioUpdateRsp resp=null;
		try {
			resp=MotoCorba.RadioUpdateRsp.parseFrom(result);
			xh.protobuf.RadioUpdateRspBean bean=new xh.protobuf.RadioUpdateRspBean();
			ConstantMap.getMotoResultMap().put(callId,resp.getResult());
			ConstantMap.getMotoResultMap().put(callId+"-info",resp.getDetail());
			bean.setResult(resp.getResult());
			bean.setDetail(resp.getDetail());
			log.debug("MOTO->DS[RadioUpdateRsp]:callId="+callId+";"+bean.toString());
			
		}/*catch (InvalidProtocolBufferException e) {
			log.debug("MOTO->DS:数据解析出错");
		}*/catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void RadioUserAddRsp(int len, byte[] buf,String callId) {
		result = new byte[len - 20];
		System.arraycopy(buf, 18, result, 0, len - 20);
		MotoCorba.RadioUserAddRsp resp=null;
		try {
			resp=MotoCorba.RadioUserAddRsp.parseFrom(result);
			xh.protobuf.RadioUserAddRspBean bean=new xh.protobuf.RadioUserAddRspBean();
			ConstantMap.getMotoResultMap().put(callId,resp.getResult());
			ConstantMap.getMotoResultMap().put(callId+"-info",resp.getDetail());
			bean.setResult(resp.getResult());
			bean.setDetail(resp.getDetail());
			log.debug("MOTO->DS:callId="+callId+";"+bean.toString());
			
		}/*catch (InvalidProtocolBufferException e) {
			log.debug("MOTO->DS:数据解析出错");
		} */catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void RadioUserDelRsp(int len, byte[] buf,String callId) {
		result = new byte[len - 20];
		System.arraycopy(buf, 18, result, 0, len - 20);
		MotoCorba.RadioUserDelRsp resp=null;
		try {
			resp=MotoCorba.RadioUserDelRsp.parseFrom(result);
			xh.protobuf.RadioUserDelRspBean bean=new xh.protobuf.RadioUserDelRspBean();
			ConstantMap.getMotoResultMap().put(callId,resp.getResult());
			ConstantMap.getMotoResultMap().put(callId+"-info",resp.getDetail());
			bean.setResult(resp.getResult());
			bean.setDetail(resp.getDetail());
			log.debug("MOTO->DS:callId="+callId+";"+bean.toString());
			
		}/*catch (InvalidProtocolBufferException e) {
			log.debug("MOTO->DS:数据解析出错");
		} */catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void RadioUserUpdateRsp(int len, byte[] buf,String callId) {
		result = new byte[len - 20];
		System.arraycopy(buf, 18, result, 0, len - 20);
		MotoCorba.RadioUserUpdateRsp resp=null;
		try {
			resp=MotoCorba.RadioUserUpdateRsp.parseFrom(result);
			xh.protobuf.RadioUserUpdateRspBean bean=new xh.protobuf.RadioUserUpdateRspBean();
			ConstantMap.getMotoResultMap().put(callId,resp.getResult());
			ConstantMap.getMotoResultMap().put(callId+"-info",resp.getDetail());
			bean.setResult(resp.getResult());
			bean.setDetail(resp.getDetail());
			log.debug("MOTO->DS:callId="+callId+";"+bean.toString());
			
		}/*catch (InvalidProtocolBufferException e) {
			log.debug("MOTO->DS:数据解析出错");
		}*/ catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void RadioGetRsp(int len, byte[] buf,String callId) {
		result = new byte[len - 20];
		System.arraycopy(buf, 18, result, 0, len - 20);
		MotoCorba.RadioGetRsp resp=null;
		try {
			resp=MotoCorba.RadioGetRsp.parseFrom(result);
			RadioBean bean=new RadioBean();
			bean.setRadioID(resp.getRadioID());
			bean.setRadioReferenceID(resp.getRadioReferenceID());
			bean.setRadioSerialNumber(resp.getRadioSerialNumber());
			bean.setSecurityGroup(resp.getSecurityGroup());
			bean.setCallId(callId);
			ConstantMap.getMotoResultMap().put(callId,resp.getRadioID());
			ConstantMap.getMotoResultMap().put(callId+"map",bean);
			log.debug("MOTO->DS[RadioGetRsp]:callId="+callId+";"+bean.toString());
			
		}/*catch (InvalidProtocolBufferException e) {
			log.debug("MOTO->DS:数据解析出错");
		}*/ catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void RadioUserGetRsp(int len, byte[] buf,String callId) {
		result = new byte[len - 20];
		System.arraycopy(buf, 18, result, 0, len - 20);
		MotoCorba.RadioUserGetRsp resp=null;
		try {
			resp=MotoCorba.RadioUserGetRsp.parseFrom(result);
			/*RadioUserBean bean=new RadioUserBean();*/
			RadioUserMotoBean bean=new RadioUserMotoBean();
			bean.setC_ID(Integer.parseInt(resp.getRadioUserAlias()));
			bean.setRadioID(resp.getRadioID());
			bean.setRadioUserAlias(resp.getRadioUserAlias());
			bean.setSecurityGroup(resp.getSecurityGroup());
			bean.setRadioUserCapabilityProfileAlias(resp.getRadioUserCapabilityProfileAlias());
			bean.setUserEnabled(resp.getUserEnabled().equals("Y")?"1":"0");
			bean.setInterconnectEnabled(resp.getInterconnectEnabled().equals("Y")?"1":"0");
			bean.setPacketDataEnabled(resp.getPacketDataEnabled().equals("Y")?"1":"0");
			bean.setShortDataEnabled(resp.getShortDataEnabled().equals("Y")?"1":"0");
			bean.setFullDuplexEnabled(resp.getFullDuplexEnabled().equals("Y")?"1":"0");
			bean.setEnableAmbienceListeningMonitoring(resp.getEnableAmbienceListeningMonitoring().equals("Y")?"1":"0");
			bean.setEnableAmbienceListeningInitiation(resp.getEnableAmbienceListeningInitiation().equals("Y")?"1":"0");
			bean.setCallId(callId);
			ConstantMap.getMotoResultMap().put(callId,resp.getRadioID());
			ConstantMap.getMotoResultMap().put(callId+"map",bean);
			log.debug("MOTO->DS[RadioUserGetRsp]:callId="+callId+";"+bean.toString());
			
		}/*catch (InvalidProtocolBufferException e) {
			log.debug("MOTO->DS:数据解析出错");
		}*/ catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void ACK(int len, byte[] buf,String callId) {
		result = new byte[len - 20];
		System.arraycopy(buf, 18, result, 0, len - 20);
		MotoCorba.Ack resp=null;
		try {
			resp=MotoCorba.Ack.parseFrom(result);
			log.debug("MOTO->DS: ACK="+resp.getAck());
			
		}/*catch (InvalidProtocolBufferException e) {
			log.debug("MOTO->DS:数据解析出错");
		}*/catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void GetAllRadio(){
		List<HashMap> list=new ArrayList<HashMap>();
		list=RadioUserService.allRadioUser("0");
		for (HashMap map : list) {
			System.out.println("user:->"+map.get("C_ID"));
			try {
				radioGet(map.get("C_ID").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String radioGet(String radioId) throws Exception{
		RadioBean bean=new RadioBean();
		if(MotoTcpClient.getSocket().isConnected()){			
			bean.setRadioID(radioId);
			bean.setCallId(FunUtil.RandomAlphanumeric(8));
			bean.setRadioReferenceID("0");
			bean.setRadioSerialNumber("0");
			bean.setSecurityGroup("0");
			SendData.RadioGetReq(bean);
			long nowtime=System.currentTimeMillis();
			tag:for(;;){			
				long tt=System.currentTimeMillis();
				if(ConstantMap.getMotoResultMap().containsKey(bean.getCallId())){
					if(ConstantMap.getMotoResultMap().get(bean.getCallId()).equals("0")){
						
					}else{
						bean=(RadioBean) ConstantMap.getMotoResultMap().get(bean.getCallId()+"map");
						RadioService.vAdd(bean);
						
					}	
					ConstantMap.getMotoResultMap().remove(bean.getCallId());
					ConstantMap.getMotoResultMap().remove(bean.getCallId()+"map");
					break tag;
				}else{
					if(tt-nowtime>10000){
						break tag;
					}
				}
			}			
		}
		return "SUCCESS";
	}
	
	public static byte[] getBufferFlag() {
		return bufferFlag;
	}

	public static void setBufferFlag(byte[] bufferFlag) {
		MotoTcpClient.bufferFlag = bufferFlag;
	}

	public static long getTimeStart() {
		return timeStart;
	}

	public static void setTimeStart(long timeStart) {
		MotoTcpClient.timeStart = timeStart;
	}


	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		MotoTcpClient.socket = socket;
	}

	public static byte[] getResult() {
		return result;
	}

	public static void setResult(byte[] result) {
		MotoTcpClient.result = result;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public MotoTcpClient() {
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
		MotoTcpClient.comID = comID;
	}

	public static int getBufLen() {
		return bufLen;
	}

	public static void setBufLen(int bufLen) {
		MotoTcpClient.bufLen = bufLen;
	}


	public static String getCallId() {
		return callId;
	}

	public static void setCallId(String callId) {
		MotoTcpClient.callId = callId;
	}


	public static byte[] getWriteBuf() {
		return writeBuf;
	}

	public static void setWriteBuf(byte[] writeBuf) {
		MotoTcpClient.writeBuf = writeBuf;
	}

	


}

