package xh.org.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FunUtil;


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
				byte[] buf = new byte[4096];// 收到的包字节数组
				byte[] bufH = new byte[2];// 收到的包字节数组
				byte[] realBuf = new byte[10240];

				while (connected) {
					int len = input.read(buf);
					int recvLen = len;
					
					if(len > 0 && len + writeBuf.length >= 4) {
						
					
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
					}
					
					
					/*if (len > 0 && len + writeBuf.length >= 4) {
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
							 writeBuf=null; 
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
						log.info("====TCP connection is closed!!====");
						log.info("====reconnection now!!====");
					
						connected = false;
					}
				}*/
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

	public void handler(int comId,byte[] buf) throws Exception {

		try {
			switch (comId) {
			case 154://终端
			    break;
			case 156://终端用户业务属性
				break;
			case 160://通话组
				break;
			case 162://通播组
				break;
			case 164://通话组、通播组业务属性
				break;
			case 166://状态集
				break;
			case 168://调度用户
				break;
			case 170://调度用户业务属性
				break;
			case 172://状态集单元
				break;
			case 174://遥毙
				break;
			case 178://终端用户业务属性有效站点
				break;
				
			case 180://终端用户有效地域
				break;
			case 182://通话组业务属性有效站点
				break;
			case 184://通话组有效地域
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
