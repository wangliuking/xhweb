package xh.org.socket;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.RadioBean;
import xh.mybatis.bean.smsBean;
import xh.protobuf.MotoCorba;
import xh.protobuf.MotoMessageStruct;
import xh.protobuf.RadioUserBean;

public class SendData {
	private String message = "";
	private Socket socket = null;
	protected static final Log log = LogFactory.getLog(SendData.class);

	private static MotoMessageStruct header = new MotoMessageStruct();

	public void connection() {
		try {
			socket = new Socket(FunUtil.readXml("gps", "ip"),
					Integer.parseInt(FunUtil.readXml("gps", "port")));
			log.debug("---------------------------------");
			log.debug("GPS设置客户端已建立链接:");
			log.debug("---------------------------------");
		} catch (UnknownHostException e) {
			message = "没有找到主机，请检查端口号或者主机IP地址是否正确";
			log.debug("---------------------------------");
			log.debug("GPS-》" + message);
			log.debug("---------------------------------");
		} catch (IOException e) {
			message = "网络无响应，检查端口号或者主机IP地址是否正确,或者检查相关调度台是否已经开启";
			System.out.println(message);
			log.debug("---------------------------------");
			log.debug("GPS-》" + message);
			log.debug("---------------------------------");
			// e.printStackTrace();
		}
		try {
			if(socket!=null){
				socket.setSoTimeout(10000);
			}
		} catch (SocketException e1) {
			message = "对方没有应答";
			log.debug("---------------------------------");
			log.debug("GPS设置客户端已经关闭连接");
			log.debug("---------------------------------");
			e1.printStackTrace();
		}
		try {
			socket.setKeepAlive(true);
		} catch (SocketException e) {
			message = "网络已经断开";
			log.debug("---------------------------------");
			log.debug("GPS网络已经断开");
			log.debug("---------------------------------");
			// e.printStackTrace();
		}// 开启保持活动状态的套接字
			// socket.setSoTimeout(10000);

	}
	public void connection(String ip,int port) {
		try {
			socket = new Socket(ip,port);
			log.debug("---------------------------------");
			log.debug("GPS设置客户端已建立连接:");
			log.debug("连接IP:"+ip);
			log.debug("连接端口:"+port);
			log.debug("---------------------------------");
		} catch (UnknownHostException e) {
			message = "没有找到主机，请检查端口号或者主机IP地址是否正确";
			log.debug("---------------------------------");
			log.debug("GPS-》" + message);
			log.debug("---------------------------------");
		} catch (IOException e) {
			message = "网络无响应，检查端口号或者主机IP地址是否正确";
			System.out.println(message);
			log.debug("---------------------------------");
			log.debug("GPS-》" + message);
			log.debug("---------------------------------");
			// e.printStackTrace();
		}
		try {
			if(socket!=null){
				socket.setSoTimeout(3000);
			}
			
		} catch (SocketException e1) {
			message = "对方没有应答";
			log.debug("---------------------------------");
			log.debug("GPS设置客户端已经关闭连接");
			log.debug("---------------------------------");
			e1.printStackTrace();
		}
		/*try {
			socket.setKeepAlive(true);
		} catch (SocketException e) {
			message = "网络已经断开";
			log.debug("---------------------------------");
			log.debug("GPS网络已经断开");
			log.debug("---------------------------------");
			// e.printStackTrace();
		}*/

	}


	/* private static MessageStruct header = new MessageStruct(); */
	/* gps立即请求 */
	public String ImmGps(MessageStruct header, GpsSetStruct getData)
			throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		connection(getData.getIp(),getData.getPort());
		if(socket==null){
			return "网络无响应，检查端口号或者主机IP地址是否正确,或者检查相关调度台是否已经开启";
		}

		// ====================================
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = socket.getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		dos.writeShort(header.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(header.getSegNum());// segNum 1 分片总数
		dos.writeByte(header.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(header.getLength());// length 2 后接数据长度
		dos.writeShort(header.getCommandId());// commandId 2 命令ID
		dos.writeShort(header.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getData.getUserId());// businessSN 4 业务流水号
		dos.writeByte(header.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(header.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// content
		dos.write(dd.IntToSmallByteArray(getData.getSrcId()));
		dos.write(dd.IntToSmallByteArray(getData.getDstId()));
		dos.writeByte(getData.getReferenceNumber());
		dos.write(dd.IntToSmallByteArray(getData.getLocationDstId()));
		/**************** content ***********************/
		dos.writeShort(header.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("ImmGps-length:" + info.length);
		log.debug("ImmGps:" + getData.toString());
		
		socket.close();
		message="success";
		log.debug("socket连接自动关闭");
		return message;

	}

	/* gps使能 */
	public String GpsEn(MessageStruct header, GpsSetStruct getData)
			throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		connection(getData.getIp(),getData.getPort());
		if(socket==null){
			return "网络无响应，检查端口号或者主机IP地址是否正确,或者检查相关调度台是否已经开启";
		}
		// ====================================
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = socket.getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		dos.writeShort(header.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(header.getSegNum());// segNum 1 分片总数
		dos.writeByte(header.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(header.getLength());// length 2 后接数据长度
		dos.writeShort(header.getCommandId());// commandId 2 命令ID
		dos.writeShort(header.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getData.getUserId());// businessSN 4 业务流水号
		dos.writeByte(header.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(header.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// content
		dos.write(dd.IntToSmallByteArray(getData.getSrcId()));
		dos.write(dd.IntToSmallByteArray(getData.getDstId()));
		dos.writeByte(getData.getReferenceNumber());
		dos.writeByte(getData.getEnableFlag());
		/**************** content ***********************/
		dos.writeShort(header.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("GpsEn-userId:" + getData.getUserId());
		log.debug("GpsEn-length:" + info.length);
		log.debug("GpsEn:" + getData.toString());
		socket.close();
		log.debug("socket连接自动关闭");
		return "success";

	}

	/* gps触发器设置 */
	public String GpsTrigger(MessageStruct header, GpsSetStruct getData)
			throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		connection(getData.getIp(),getData.getPort());
		if(socket==null){
			return "网络无响应，检查端口号或者主机IP地址是否正确,或者检查相关调度台是否已经开启";
		}
		// ====================================
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = socket.getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		dos.writeShort(header.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(header.getSegNum());// segNum 1 分片总数
		dos.writeByte(header.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(header.getLength());// length 2 后接数据长度
		dos.writeShort(header.getCommandId());// commandId 2 命令ID
		dos.writeShort(header.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getData.getUserId());// businessSN 4 业务流水号
		dos.writeByte(header.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(header.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// content
		dos.write(dd.IntToSmallByteArray(getData.getSrcId()));
		dos.write(dd.IntToSmallByteArray(getData.getDstId()));
		dos.write(dd.IntToSmallByteArray(getData.getLocationDstId()));
		dos.writeByte(getData.getReferenceNumber());
		dos.writeByte(getData.getTriggerType());
		dos.write(dd.IntToSmallByteArray(getData.getTriggerPara()));
		/**************** content ***********************/
		dos.writeShort(header.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("GpsTrigger-length:" + info.length);
		log.debug("GpsTrigger:" + getData.toString());
		
		// 获得服务器发过来的数据，先获得输入流

		/*InputStream in = socket.getInputStream();
		DataInputStream din = new DataInputStream(in);
		message = "接收数据中";
		try {
			int comm = 54;
			byte[] buf = new byte[1024];
			do {
				int len = din.read(buf);
				int head = dd.BigByteArrayToShort(buf, 0);
				int status = dd.SmallByteArrayToInt(buf, 25);
				comm = dd.BigByteArrayToShort(buf, 6);
				String str = "";
				for (int i = 0; i < len; i++) {
					System.out.print(buf[i] + " ");
					str += buf[i] + " ";

				}
				System.out.print("\n");
				System.out.println(status);
				log.debug(str);
				log.debug(status);
				if (status == 0 || status == 24) {
					message = "success";
				} else {
					message = String.valueOf(status) + "操作失败！";
				}
				if (head != 0xc4d7) {
					message = "接收的数据包头不正确";
				}
			} while (comm != 54);
			// 注意：read会产生阻塞
			socket.close();
			log.debug("socket连接自动关闭");
		} catch (SocketTimeoutException e) {
			message = "对方回复超时";
			log.debug(message);
			// TODO: handle exception
		}*/

		return "success";

	}

	public static void DGNA(MessageStruct header, addDgnaStruct bean)
			throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		/* connection(); */

		// ====================================
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		dos.writeShort(header.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(header.getSegNum());// segNum 1 分片总数
		dos.writeByte(header.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(header.getLength());// length 2 后接数据长度
		dos.writeShort(header.getCommandId());// commandId 2 命令ID
		dos.writeShort(header.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(header.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(header.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(header.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// operation
		dos.write(dd.IntToSmallByteArray(bean.getOpra()));
		// content
		dos.write(dd.ShortToBigByteArray((short) bean.getMscId()));
		dos.write(dd.IntToSmallByteArray(bean.getIssi()));
		dos.write(dd.IntToSmallByteArray(bean.getGssi()));
		dos.write(dd.LongData(bean.getGroupname(), 32));
		dos.writeByte(bean.getAttached());
		dos.writeByte(bean.getCou());
		dos.writeByte(bean.getOperation());
		dos.writeByte(bean.getStatus());
		/**************** content ***********************/
		dos.writeShort(header.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("DGNAData-length:" + info.length);
		log.debug("DGNAData:" + bean.toString());

	}

	// 发送遥毙数据包
	public static void sendKilledData(MessageStruct getHeader,
			KillStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();

		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// operation
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));
		// content
		dos.write(dd.ShortToBigByteArray((short) getData.getMsId()));
		dos.write(dd.IntToSmallByteArray(getData.getUserId()));
		dos.writeByte(getData.getKillCmd());
		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("KillData-length:" + info.length);
		log.debug("KillData:" + getData.toString());

	}

	// 发送终端业务属性有效站点数据包
	public static void sendRadioUserSavalidSiteData(MessageStruct getHeader,
			savalidsiteStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();

		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		// 486 153
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/

		dos.write(dd.IntToSmallByteArray(getData.getOperation()));

		dos.write(dd.IntToSmallByteArray(getData.getId()));
		dos.write(dd.IntToSmallByteArray(getData.getSaId()));
		dos.write(dd.LongData(getData.getSaName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getMscId()));
		dos.write(dd.LongData(getData.getMscName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getBsId()));
		dos.write(dd.LongData(getData.getBsName(), 48));

		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("sendRadioUserSavalidSiteData-length:" + info.length);
		log.debug("sendRadioUserSavalidSiteData:" + getData.toString());

	}

	// 发送通话组业务属性有效站点数据包
	public static void sendTalkGroupSavalidSiteData(MessageStruct getHeader,
			savalidsiteStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();

		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		// 486 153
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/

		dos.write(dd.IntToSmallByteArray(getData.getOperation()));

		dos.write(dd.IntToSmallByteArray(getData.getId()));
		dos.write(dd.IntToSmallByteArray(getData.getSaId()));
		dos.write(dd.LongData(getData.getSaName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getMscId()));
		dos.write(dd.LongData(getData.getMscName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getBsId()));
		dos.write(dd.LongData(getData.getBsName(), 48));
		dos.writeByte(getData.getRequired());

		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("sendTalkGroupSavalidSiteData:" + info.length);
		log.debug("sendTalkGroupSavalidSiteData:" + getData.toString());

	}

	// 发送短信
	public int sendSms(MessageStruct getHeader, smsBean getData) {
		// 创建客户端的Socket服务，指定目的主机和端口。
		int status = -1;
		try {
			NetDataTypeTransform dd = new NetDataTypeTransform();
			connection();

			// ====================================
			// 发送数据，应该获取Socket流中的输出流。
			OutputStream out = socket.getOutputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2
															// 命令开始字段
			dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
			dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
			dos.writeShort(getHeader.getLength());// length 2 后接数据长度
			dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
			dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
			dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
			dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
			dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
			/**************** content ***********************/
			dos.write(dd.IntToSmallByteArray(getData.getSrcId()));
			dos.write(dd.IntToSmallByteArray(getData.getDstId()));
			dos.writeByte(getData.getReferenceNumber());
			dos.writeByte(getData.getConsume());
			dos.writeByte(getData.getLen());
			dos.write(dd.LongData(getData.getContent(), 210));
			dos.writeByte(getData.getType());
			/**************** content ***********************/
			dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

			byte[] info = bos.toByteArray();
			out.write(info);
			log.debug("sendSms:" + info.length);
			log.debug("sendSms:" + getData.toString());

			// 获得服务器发过来的数据，先获得输入流

			InputStream in = socket.getInputStream();
			DataInputStream din = new DataInputStream(in);
			byte[] buf = new byte[1024];

			// 注意：read会产生阻塞
			int len = din.read(buf);
			int header = dd.BigByteArrayToShort(buf, 0);
			String str = "";
			status = dd.SmallByteArrayToInt(buf, 26);
			int status2 = dd.SmallByteArrayToInt(buf, 7);
			/*
			 * for (int i = 0; i < len; i++) { System.out.print(buf[i] + " ");
			 * str += buf[i] + " ";
			 * 
			 * }
			 */
			if (status == 0) {
				message = "短信发送成功";
			} else if (status == 1) {
				message = "短信发送失败";
			} else if (status == 2) {
				message = "短信已读";
			} else if (status == 3) {
				message = "短信发送中";
			} else if (status == 4) {
				message = "短信发送失败";
			} else {
				message = String.valueOf(status) + "操作失败！";
			}
			if (header != 0xc4d7) {
				message = "接收的数据包头不正确";
			}
			log.debug("发送状态:" + status + ";message:" + message);

			socket.close();
			return status;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return status;
		}

	}

	// 发送终端用户数据包
	public static void sendRadioUserData(MessageStruct getHeader,
			RadioUserStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		// 486 153
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));
		// /moto 280
		dos.write(dd.LongData("", 280));
		// 东信 190
		dos.write(dd.IntToSmallByteArray(getData.getId()));
		dos.write(dd.LongData(getData.getName(), 48));
		dos.write(dd.LongData(getData.getAlias(), 8));
		dos.write(dd.IntToSmallByteArray(getData.getMscId()));
		dos.write(dd.LongToSmallByteArray(getData.getVpnId()));
		dos.write(dd.LongData(getData.getSn(), 32));
		dos.write(dd.LongData(getData.getCompany(), 32));
		dos.write(dd.LongData(getData.getType(), 16));
		dos.writeByte(getData.getEnabled());
		dos.writeByte(getData.getShortData());
		dos.writeByte(getData.getFullDuple());
		dos.writeByte(getData.getRadioType());
		dos.writeByte(getData.getAnycall());
		dos.write(dd.IntToSmallByteArray(getData.getSaId()));
		dos.write(dd.IntToSmallByteArray(getData.getIaId()));
		dos.write(dd.IntToSmallByteArray(getData.getVaId()));
		dos.write(dd.IntToSmallByteArray(getData.getRugId()));
		dos.writeByte(Integer.parseInt(getData.getPacketData()));
		dos.write(dd.LongData(getData.getIp(), 20));
		dos.write(dd.IntToSmallByteArray(getData.getPrimaryTGId()));
		dos.writeByte(Integer.parseInt(getData.getAmbienceMonitoring()));
		dos.writeByte(Integer.parseInt(getData.getAmbienceInitiation()));
		dos.write(dd.LongData(getData.getDirectDial(), 16));
		dos.writeByte(Integer.parseInt(getData.getPstnAccess()));
		dos.writeByte(Integer.parseInt(getData.getPabxAccess()));
		dos.writeByte(Integer.parseInt(getData.getClir()));
		dos.writeByte(Integer.parseInt(getData.getClirOverride()));
		dos.writeByte(Integer.parseInt(getData.getKilled()));
		dos.writeByte(Integer.parseInt(getData.getMsType()));
		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("RadioUserData-length:" + info.length);
		log.debug("RadioUserData:" + getData.toString());

	}

	// 发送通话组数据包
	public static void sendTalkGroupData(MessageStruct getHeader,
			TalkGroupStruct getData) throws IOException {

		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// operation
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));
		// motarol
		dos.write(dd.LongData("", 88));
		// etra
		dos.write(dd.IntToSmallByteArray(getData.getId()));
		dos.write(dd.LongData(getData.getName(), 48));
		dos.write(dd.LongData(getData.getAlias(), 8));
		dos.write(dd.IntToSmallByteArray(getData.getMscId()));
		dos.write(dd.LongToSmallByteArray(getData.getVpnId()));
		dos.write(dd.IntToSmallByteArray(getData.getSaId()));
		dos.write(dd.IntToSmallByteArray(getData.getIaId()));
		dos.write(dd.IntToSmallByteArray(getData.getVaId()));
		dos.writeByte(getData.getPreempt());
		dos.writeByte(getData.getRadioType());
		dos.writeByte(getData.getRegroupAble());
		dos.writeByte(getData.getEnabled());
		dos.write(dd.LongData(getData.getDirectDial(), 16));
		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("TalkGroupData-length:" + info.length);
		log.debug("TalkGroupData:" + getData.toString());

	}

	// 发送通播组数据包
	public static void sendMultiGroupData(MessageStruct getHeader,
			MultiGroupStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		// 1776 161
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// operation
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));
		// moto
		dos.write(dd.LongData("", 1689));
		// 东信
		// 71
		dos.write(dd.IntToSmallByteArray(getData.getId()));
		dos.write(dd.LongData(getData.getName(), 48));
		dos.write(dd.LongData(getData.getAlias(), 8));
		dos.write(dd.IntToSmallByteArray(getData.getMscId()));
		dos.write(dd.LongToSmallByteArray(getData.getVpnId()));
		dos.write(dd.IntToSmallByteArray(getData.getSaId()));
		dos.write(dd.IntToSmallByteArray(getData.getIaId()));
		dos.write(dd.IntToSmallByteArray(getData.getVaId()));
		dos.writeByte(getData.getPreempt());
		dos.writeByte(getData.getRadioType());
		dos.writeByte(getData.getEnabled());
		dos.write(dd.LongData(getData.getDirectDial(), 16));

		// 新增
		dos.writeByte(getData.getInterruptWait());
		dos.writeShort(getData.getPdtType());
		dos.writeShort(getData.getNpType());
		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("MultiGroupData-length:" + info.length);
		log.debug("MultiGroupData:" + getData.toString());
		/* log.debug("MultiGroupData-info:" + FunUtil.BytesToHexS(info)) */;

	}

	// 发送调度用户数据包
	public static void sendDispatchUserData(MessageStruct getHeader,
			DispatchUserStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();

		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// operation
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));
		// content
		dos.write(dd.IntToSmallByteArray(getData.getId()));
		dos.write(dd.LongData(getData.getName(), 48));
		dos.write(dd.LongData(getData.getAlias(), 8));
		dos.write(dd.ShortToBigByteArray((short) getData.getMscId()));
		dos.write(dd.LongToSmallByteArray(getData.getVpnId()));
		dos.writeByte(getData.getUserType());
		dos.write(dd.LongData(getData.getPassword(), 16));
		dos.write(dd.ShortToSmallByteArray((short) getData.getVaid()));
		dos.write(dd.ShortToSmallByteArray((short) getData.getSaid()));
		dos.write(dd.IntToSmallByteArray(getData.getMasterId()));
		dos.writeByte(getData.getFullDuplex());
		dos.writeByte(getData.getAmbienceInitiation());
		dos.writeByte(getData.getClir());
		dos.writeByte(getData.getClirOverride());

		// 新增
		dos.write(dd.IntToSmallByteArray(getData.getCode()));
		dos.write(dd.IntToSmallByteArray(getData.getDispatchNum()));
		dos.write(dd.LongData(getData.getSaName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getSupervisorStatus()));
		dos.write(dd.IntToSmallByteArray(getData.getDispatcherType()));
		dos.write(dd.LongData(getData.getDialString(), 16));

		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("DispatchUserData-length:" + info.length);
		log.debug("DispatchUserData:" + getData.toString());
		/* log.debug("DispatchUserData-info:" + FunUtil.BytesToHexS(info)); */

	}

	// 发送状态集数据包
	public static void sendStatusSetData(MessageStruct getHeader,
			statusSetStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();

		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// operation
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));
		// content
		dos.write(dd.ShortToBigByteArray((short) getData.getId()));
		dos.write(dd.LongData(getData.getName(), 48));
		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		System.out.println(getData.getId());
		System.out.println(getData.getName());
		System.out.println(dd.ShortToBigByteArray((short) getData.getId()));
		System.out.println(dd.LongData(getData.getName(), 16));

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("sendStatusSetData-length:" + info.length);
		log.debug("sendStatusSetData:" + getData.toString());

	}

	// 发送状态集单元数据包
	public static void sendStatusSetUnitData(MessageStruct getHeader,
			statusSetUnitStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();

		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// operation
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));
		// content
		dos.write(dd.ShortToBigByteArray((short) getData.getSsId()));
		dos.write(dd.IntToSmallByteArray(getData.getNumber()));
		dos.write(dd.LongData(getData.getText(), 80));
		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("sendStatusSetUnitData-length:" + info.length);
		log.debug("sendStatusSetUnitData:" + getData.toString());

	}

	// 发送终端用户业务属性数据包
	public static void sendRadioUserAttrData(MessageStruct getHeader,
			RadioUserAttrStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		// 141 155
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// operation
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));
		// moto 91
		dos.write(dd.LongData("", 91));
		// 东信
		// 34 29
		dos.write(dd.IntToSmallByteArray(getData.getId()));
		dos.write(dd.LongData(getData.getName(), 48));

		dos.write(dd.IntToSmallByteArray(getData.getSsId()));
		// 新增
		dos.write(dd.LongData(getData.getSsName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getDispatchPriority()));
		dos.writeByte(getData.getPccEnabled());
		dos.writeByte(getData.getMgEnabled());
		dos.writeByte(getData.getTgEnabled());
		dos.writeByte(getData.getMgEmergencyEnabled());
		dos.writeByte(getData.getDispatchPreempt());
		dos.writeByte(getData.getAllSitesAllowed());
		dos.writeByte(getData.getCalledPreempt());
		dos.writeByte(getData.getUserGroup());
		dos.writeByte(getData.getEmergIndCallEnabled());
		dos.writeByte(getData.getEmergGroupCallEnabled());
		/**************** content ***********************/
		dos.writeShort(0x0000);// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("RadioUserAttrData-length:" + info.length);
		log.debug("RadioUserAttrData:" + getData.toString());

	}

	// 发送通话组/通播组业务属性数据包
	public static void sendTalkGroupAttrData(MessageStruct getHeader,
			TalkGroupAttrStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		// 131 163
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// operation
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));
		// moto 79
		dos.write(dd.LongData("", 94));
		// 东信
		// 36
		dos.write(dd.IntToSmallByteArray(getData.getId()));
		dos.write(dd.LongData(getData.getName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getMessageTransmission()));
		dos.write(dd.IntToSmallByteArray(getData.getBusyOverride()));
		dos.writeByte(getData.getEmergencyCall());
		dos.writeByte(getData.getEmergencyAtNVS());
		dos.write(dd.IntToSmallByteArray(getData.getDispatchPriority()));
		dos.writeByte(getData.getPriorityMonitor());
		dos.writeByte(getData.getTgDataPreempt());

		// 新增
		dos.writeByte(getData.getInactivityTime());
		dos.writeByte(getData.getCallsWithoutMGEG());
		dos.writeByte(getData.getAudioInterrupt());
		dos.writeByte(getData.getUserGroup());

		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		/*
		 * System.out.println(getData.getId());
		 * System.out.println(getData.getUserGroup());
		 */

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("TalkGroupAttrData-length:" + info.length);
		log.debug("TalkGroupAttrData:" + getData.toString());
		/* log.debug("TalkGroupAttrData-info:" + FunUtil.BytesToHexS(info)); */

	}

	// 发送调度用户属性数据包
	public static void sendDispatchUserIAData(MessageStruct getHeader,
			DispatchUserIAStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		// operation
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));
		// content
		dos.write(dd.ShortToBigByteArray((short) getData.getId()));
		dos.write(dd.LongData(getData.getName(), 48));
		dos.writeByte(getData.getMonitorOn());
		dos.writeByte(getData.getPCPreempt());
		dos.writeByte(getData.getCallPriority());
		dos.writeByte(getData.getAllMute());
		dos.writeByte(getData.getAllMuteTimeout());
		dos.writeByte(getData.getPttPriority());

		// 新增
		dos.writeByte(getData.getProhibitTone());
		dos.writeByte(getData.getSideTone());
		dos.write(dd.IntToSmallByteArray(getData.getPatchGroupNum()));
		dos.write(dd.IntToSmallByteArray(getData.getMSGroupNum()));
		dos.write(dd.IntToSmallByteArray(getData.getAPBNum()));
		dos.write(dd.IntToSmallByteArray(getData.getCalledPreempt()));
		dos.writeByte(getData.getInboundCall());
		dos.writeByte(getData.getInboundPTT());
		dos.writeByte(getData.getInstantTransmit());
		dos.writeByte(getData.getPatchPC());

		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();

		out.write(info);
		log.debug("sendDispatchUserIAData-length:" + info.length);
		log.debug("sendDispatchUserIAData:" + getData.toString());

	}

	// 发送终端用户有效地域数据包
	public static void sendRadioUserSavalidRegionData(MessageStruct getHeader,
			validregionStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();

		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		// 486 153
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));

		dos.write(dd.IntToSmallByteArray(getData.getId()));
		dos.write(dd.IntToSmallByteArray(getData.getSaId()));
		dos.write(dd.LongData(getData.getSaName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getMscId()));
		dos.write(dd.LongData(getData.getMscName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getType()));
		dos.write(dd.IntToSmallByteArray(getData.getNeType()));

		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("sendRadioUserSavalidRegionData-length:" + info.length);
		log.debug("sendRadioUserSavalidRegionData:" + getData.toString());

	}

	// 发送通话组有效地域数据包
	public static void sendTalkGroupSavalidRegionData(MessageStruct getHeader,
			validregionStruct getData) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();

		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		// 486 153
		dos.writeShort(getHeader.getCommandHeader()); // commandHeader 2 命令开始字段
		dos.writeByte(getHeader.getSegNum());// segNum 1 分片总数
		dos.writeByte(getHeader.getSegFlag());// segFlag 1 当前分片序号
		dos.writeShort(getHeader.getLength());// length 2 后接数据长度
		dos.writeShort(getHeader.getCommandId());// commandId 2 命令ID
		dos.writeShort(getHeader.getProtocolNo());// protocolNo 2 协议号
		dos.writeInt(getHeader.getBusinessSN());// businessSN 4 业务流水号
		dos.writeByte(getHeader.getSrcDevice());// srcDevice 1 源设备类型
		dos.writeByte(getHeader.getDstDevice());// dstDevice 1 目标设备类型
		/**************** content ***********************/
		dos.write(dd.IntToSmallByteArray(getData.getOperation()));

		dos.write(dd.IntToSmallByteArray(getData.getId()));
		dos.write(dd.IntToSmallByteArray(getData.getSaId()));
		dos.write(dd.LongData(getData.getSaName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getMscId()));
		dos.write(dd.LongData(getData.getMscName(), 48));
		dos.write(dd.IntToSmallByteArray(getData.getType()));
		dos.write(dd.IntToSmallByteArray(getData.getNeType()));

		/**************** content ***********************/
		dos.writeShort(getHeader.getChecksum());// checksum 2 校验码

		byte[] info = bos.toByteArray();
		out.write(info);
		log.debug("sendTalkGroupSavalidRegionData-length:" + info.length);
		log.debug("sendTalkGroupSavalidRegionData:" + getData.toString());

	}

	// moto
	public static String RadioAddReq(RadioBean bean) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		MotoCorba.RadioGetReq.Builder builder = MotoCorba.RadioGetReq
				.newBuilder();

		/*
		 * message RadioGetReq{ required string RadioID = 1; required string
		 * RadioReferenceID = 2; required string RadioSerialNumber = 3; required
		 * string SecurityGroup = 4; ｝
		 */
		builder.setRadioID(bean.getRadioID());
		builder.setRadioReferenceID(bean.getRadioReferenceID());
		builder.setRadioSerialNumber(bean.getRadioSerialNumber());
		builder.setSecurityGroup(bean.getSecurityGroup());

		MotoCorba.RadioGetReq req = builder.build();
		byte[] buffer = req.toByteArray();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = MotoTcpClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		dos.writeShort(header.getCMDHeader()); // commandHeader 2 命令开始字段
		dos.writeShort(header.getLength() + buffer.length);// length 2 后接数据长度
		dos.writeInt(header.getTargetId());
		;
		dos.writeShort((short) 4);// commandId 2 命令ID
		dos.write(dd.LongData(bean.getCallId(), 8));// businessSN 8 业务流水号
		/**************** content ***********************/
		dos.write(buffer);
		/**************** content ***********************/
		dos.writeShort(header.getCheckSum());

		byte[] info = bos.toByteArray();
		if (MotoTcpClient.getSocket().isConnected()) {
			log.debug("DS->MOTO[RadioAddReq]:" + bean.toString());
			log.debug("DS->MOTO[RadioAddReq]:" + FunUtil.BytesToHexS(info));
			out.write(info);
		} else {
			return "NO";
		}
		return "OK";
	}

	// 删除终端
	public static String RadioDelReq(RadioBean bean) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		MotoCorba.RadioDelReq.Builder builder = MotoCorba.RadioDelReq
				.newBuilder();

		/*
		 * message RadioGetReq{ required string RadioID = 1; required string
		 * RadioReferenceID = 2; required string RadioSerialNumber = 3; required
		 * string SecurityGroup = 4; ｝
		 */
		builder.setRadioID(bean.getRadioID());
		builder.setRadioReferenceID(bean.getRadioReferenceID());
		builder.setRadioSerialNumber(bean.getRadioSerialNumber());
		builder.setSecurityGroup(bean.getSecurityGroup());

		MotoCorba.RadioDelReq req = builder.build();
		byte[] buffer = req.toByteArray();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = MotoTcpClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		dos.writeShort(header.getCMDHeader()); // commandHeader 2 命令开始字段
		dos.writeShort(header.getLength() + buffer.length);// length 2 后接数据长度
		dos.writeInt(header.getTargetId());
		;
		dos.writeShort((short) 6);// commandId 2 命令ID
		dos.write(dd.LongData(bean.getCallId(), 8));// businessSN 8 业务流水号
		/**************** content ***********************/
		dos.write(buffer);
		/**************** content ***********************/
		dos.writeShort(header.getCheckSum());

		byte[] info = bos.toByteArray();
		if (MotoTcpClient.getSocket().isConnected()) {
			log.debug("DS->MOTO[RadioDelReq]:" + bean.toString());
			log.debug("DS->MOTO[RadioDelReq]:" + FunUtil.BytesToHexS(info));
			out.write(info);
		} else {
			return "NO";
		}
		return "OK";
	}

	public static String RadioUpdateReq(RadioBean bean) throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		MotoCorba.RadioUpdateReq.Builder builder = MotoCorba.RadioUpdateReq
				.newBuilder();

		/*
		 * message RadioGetReq{ required string RadioID = 1; required string
		 * RadioReferenceID = 2; required string RadioSerialNumber = 3; required
		 * string SecurityGroup = 4; ｝
		 */
		builder.setRadioID(bean.getRadioID());
		builder.setRadioReferenceID(bean.getRadioReferenceID());
		builder.setRadioSerialNumber(bean.getRadioSerialNumber());
		builder.setSecurityGroup(bean.getSecurityGroup());

		MotoCorba.RadioUpdateReq req = builder.build();
		byte[] buffer = req.toByteArray();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = MotoTcpClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		dos.writeShort(header.getCMDHeader()); // commandHeader 2 命令开始字段
		dos.writeShort(header.getLength() + buffer.length);// length 2 后接数据长度
		dos.writeInt(header.getTargetId());
		;
		dos.writeShort((short) 8);// commandId 2 命令ID
		dos.write(dd.LongData(bean.getCallId(), 8));// businessSN 8 业务流水号
		/**************** content ***********************/
		dos.write(buffer);
		/**************** content ***********************/
		dos.writeShort(header.getCheckSum());

		byte[] info = bos.toByteArray();
		if (MotoTcpClient.getSocket().isConnected()) {
			log.debug("DS->MOTO[RadioUpdateReq]:" + bean.toString());
			log.debug("DS->MOTO[RadioUpdateReq]:" + FunUtil.BytesToHexS(info));
			out.write(info);
		} else {
			return "NO";
		}
		return "OK";
	}

	// 新增终端用户
	public static String RadioUserAddReq(RadioUserBean bean)
			throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		MotoCorba.RadioUserAddReq.Builder builder = MotoCorba.RadioUserAddReq
				.newBuilder();
		builder.setRadioID(bean.getRadioID());
		builder.setRadioUserAlias(bean.getRadioUserAlias());
		builder.setSecurityGroup(bean.getSecurityGroup());
		builder.setRadioUserCapabilityProfileAlias(bean
				.getRadioUserCapabilityProfileAlias());
		builder.setUserEnabled(bean.getUserEnabled());
		builder.setInterconnectEnabled(bean.getInterconnectEnabled());
		builder.setPacketDataEnabled(bean.getPacketDataEnabled());
		builder.setShortDataEnabled(bean.getShortDataEnabled());
		builder.setFullDuplexEnabled(bean.getFullDuplexEnabled());

		MotoCorba.RadioUserAddReq req = builder.build();
		byte[] buffer = req.toByteArray();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = MotoTcpClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		dos.writeShort(header.getCMDHeader()); // commandHeader 2 命令开始字段
		dos.writeShort(header.getLength() + buffer.length);// length 2 后接数据长度
		dos.writeInt(header.getTargetId());
		;
		dos.writeShort((short) 12);// commandId 2 命令ID
		dos.write(dd.LongData(bean.getCallId(), 8));// businessSN 8 业务流水号
		/**************** content ***********************/
		dos.write(buffer);
		/**************** content ***********************/
		dos.writeShort(header.getCheckSum());

		byte[] info = bos.toByteArray();
		if (MotoTcpClient.getSocket().isConnected()) {
			log.debug("DS->MOTO[RadioUserAddReq]:" + bean.toString());
			log.debug("DS->MOTO[RadioUserAddReq]:" + FunUtil.BytesToHexS(info));
			out.write(info);
		} else {
			return "NO";
		}
		return "OK";
	}

	// 删除终端
	public static String RadioUserDelReq(RadioUserBean bean)
			throws IOException {
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		MotoCorba.RadioUserDelReq.Builder builder = MotoCorba.RadioUserDelReq
				.newBuilder();
		builder.setRadioID(bean.getRadioID());
		builder.setRadioUserAlias(bean.getRadioUserAlias());
		builder.setSecurityGroup(bean.getSecurityGroup());
		builder.setRadioUserCapabilityProfileAlias(bean
				.getRadioUserCapabilityProfileAlias());
		builder.setUserEnabled(bean.getUserEnabled());
		builder.setInterconnectEnabled(bean.getInterconnectEnabled());
		builder.setPacketDataEnabled(bean.getPacketDataEnabled());
		builder.setShortDataEnabled(bean.getShortDataEnabled());
		builder.setFullDuplexEnabled(bean.getFullDuplexEnabled());

		MotoCorba.RadioUserDelReq req = builder.build();
		byte[] buffer = req.toByteArray();
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = MotoTcpClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		dos.writeShort(header.getCMDHeader()); // commandHeader 2 命令开始字段
		dos.writeShort(header.getLength() + buffer.length);// length 2 后接数据长度
		dos.writeInt(header.getTargetId());
		;
		dos.writeShort((short) 14);// commandId 2 命令ID
		dos.write(dd.LongData(bean.getCallId(), 8));// businessSN 8 业务流水号
		/**************** content ***********************/
		dos.write(buffer);
		/**************** content ***********************/
		dos.writeShort(header.getCheckSum());

		byte[] info = bos.toByteArray();
		if (MotoTcpClient.getSocket().isConnected()) {
			log.debug("DS->MOTO[RadioUserDelReq]:" + bean.toString());
			log.debug("DS->MOTO[RadioUserDelReq]:" + FunUtil.BytesToHexS(info));
			out.write(info);
		} else {
			return "NO";
		}
		return "OK";
	}
	// 更新终端
		public static String RadioUserUpdateReq(RadioUserBean bean)
				throws IOException {
			// 创建客户端的Socket服务，指定目的主机和端口。
			NetDataTypeTransform dd = new NetDataTypeTransform();
			MotoCorba.RadioUserUpdateReq.Builder builder = MotoCorba.RadioUserUpdateReq
					.newBuilder();
			builder.setRadioID(bean.getRadioID());
			builder.setRadioUserAlias(bean.getRadioUserAlias());
			builder.setSecurityGroup(bean.getSecurityGroup());
			builder.setRadioUserCapabilityProfileAlias(bean
					.getRadioUserCapabilityProfileAlias());
			builder.setUserEnabled(bean.getUserEnabled());
			builder.setInterconnectEnabled(bean.getInterconnectEnabled());
			builder.setPacketDataEnabled(bean.getPacketDataEnabled());
			builder.setShortDataEnabled(bean.getShortDataEnabled());
			builder.setFullDuplexEnabled(bean.getFullDuplexEnabled());

			MotoCorba.RadioUserUpdateReq req = builder.build();
			byte[] buffer = req.toByteArray();
			// 发送数据，应该获取Socket流中的输出流。
			OutputStream out = MotoTcpClient.getSocket().getOutputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);

			dos.writeShort(header.getCMDHeader()); // commandHeader 2 命令开始字段
			dos.writeShort(header.getLength() + buffer.length);// length 2 后接数据长度
			dos.writeInt(header.getTargetId());
			;
			dos.writeShort((short) 16);// commandId 2 命令ID
			dos.write(dd.LongData(bean.getCallId(), 8));// businessSN 8 业务流水号
			/**************** content ***********************/
			dos.write(buffer);
			/**************** content ***********************/
			dos.writeShort(header.getCheckSum());

			byte[] info = bos.toByteArray();
			if (MotoTcpClient.getSocket().isConnected()) {
				log.debug("DS->MOTO[RadioUserUpdateReq]:" + bean.toString());
				log.debug("DS->MOTO[RadioUserUpdateReq]:" + FunUtil.BytesToHexS(info));
				out.write(info);
			} else {
				return "NO";
			}
			return "OK";
		}
		// 获取终端
		public static String RadioGetReq(RadioBean bean)
				throws IOException {
			// 创建客户端的Socket服务，指定目的主机和端口。
			NetDataTypeTransform dd = new NetDataTypeTransform();
			MotoCorba.RadioGetReq.Builder builder = MotoCorba.RadioGetReq
					.newBuilder();
			builder.setRadioID(bean.getRadioID());

			MotoCorba.RadioGetReq req = builder.build();
			byte[] buffer = req.toByteArray();
			// 发送数据，应该获取Socket流中的输出流。
			OutputStream out = MotoTcpClient.getSocket().getOutputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);

			dos.writeShort(header.getCMDHeader()); // commandHeader 2 命令开始字段
			dos.writeShort(header.getLength() + buffer.length);// length 2 后接数据长度
			dos.writeInt(header.getTargetId());
			;
			dos.writeShort((short) 2);// commandId 2 命令ID
			dos.write(dd.LongData(bean.getCallId(), 8));// businessSN 8 业务流水号
			/**************** content ***********************/
			dos.write(buffer);
			/**************** content ***********************/
			dos.writeShort(header.getCheckSum());

			byte[] info = bos.toByteArray();
			if (MotoTcpClient.getSocket().isConnected()) {
				log.debug("DS->MOTO[RadioGetReq]:" + bean.toString());
				log.debug("DS->MOTO[RadioGetReq]:" + FunUtil.BytesToHexS(info));
				out.write(info);
			} else {
				return "NO";
			}
			return "OK";
		}
		// 获取终端用户
				public static String RadioUserGetReq(RadioUserBean bean)
						throws IOException {
					// 创建客户端的Socket服务，指定目的主机和端口。
					NetDataTypeTransform dd = new NetDataTypeTransform();
					MotoCorba.RadioUserGetReq.Builder builder = MotoCorba.RadioUserGetReq
							.newBuilder();
					builder.setRadioUserAlias(bean.getRadioUserAlias());

					MotoCorba.RadioUserGetReq req = builder.build();
					byte[] buffer = req.toByteArray();
					// 发送数据，应该获取Socket流中的输出流。
					OutputStream out = MotoTcpClient.getSocket().getOutputStream();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					DataOutputStream dos = new DataOutputStream(bos);

					dos.writeShort(header.getCMDHeader()); // commandHeader 2 命令开始字段
					dos.writeShort(header.getLength() + buffer.length);// length 2 后接数据长度
					dos.writeInt(header.getTargetId());
					;
					dos.writeShort((short) 10);// commandId 2 命令ID
					dos.write(dd.LongData(bean.getCallId(), 8));// businessSN 8 业务流水号
					/**************** content ***********************/
					dos.write(buffer);
					/**************** content ***********************/
					dos.writeShort(header.getCheckSum());

					byte[] info = bos.toByteArray();
					if (MotoTcpClient.getSocket().isConnected()) {
						log.debug("DS->MOTO[RadioUserGetReq]:" + bean.toString());
						log.debug("DS->MOTO[RadioUserGetReq]:" + FunUtil.BytesToHexS(info));
						out.write(info);
					} else {
						return "NO";
					}
					return "OK";
				}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
