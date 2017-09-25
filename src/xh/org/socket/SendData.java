package xh.org.socket;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.DgnaBean;

public class SendData {
	// private HttpServletRequest request =ServletActionContext.getRequest();
	private String message = "";

	private int CLIENT_PORT = Integer.parseInt(FunUtil.readXml("ucm", "port")); // 端口号12002
	private String IP = FunUtil.readXml("ucm", "ip"); // IP192.168.30.12
	protected final Log log = LogFactory.getLog(SendData.class);

	public  static HashMap hashMap = new HashMap();
	private static MessageStruct header=new MessageStruct();
	Socket socket = TcpKeepAliveClient.getSocket();
	public void connection() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String ip = addr.getHostAddress();
		try {
			if (socket == null || socket.isClosed()) {
				socket = new Socket(IP, CLIENT_PORT);
				System.out.println("connection!!");
			}
		} catch (UnknownHostException e) {
			message = "没有找到主机，请检查端口号或者主机IP地址是否正确";
			System.out.print(message);
			// e.printStackTrace();
		} catch (IOException e) {
			message = "网络断开";
			System.out.println(message);
			// e.printStackTrace();
		}
		try {
			socket.setSoTimeout(10000);
		} catch (SocketException e1) {
			message = "对方没有应答";
			System.out.print(message);
			// e1.printStackTrace();
		}
		try {
			socket.setKeepAlive(true);
		} catch (SocketException e) {
			message = "网络已经断开";
			System.out.print(message);
			// e.printStackTrace();
		}// 开启保持活动状态的套接字
		// socket.setSoTimeout(10000);
	}

	public static void  DGNA(DgnaBean bean) throws IOException {
		MessageStruct m_header = new MessageStruct();
		// 创建客户端的Socket服务，指定目的主机和端口。
		NetDataTypeTransform dd = new NetDataTypeTransform();
		/* connection(); */


		// ====================================
		// 发送数据，应该获取Socket流中的输出流。
		OutputStream out = TcpKeepAliveClient.getSocket().getOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		
		
		dos.writeShort(header.getCommandHeader()); //commandHeader	2	命令开始字段  
    	dos.writeByte(header.getSegNum());//segNum	1	分片总数
    	dos.writeByte(header.getSegFlag());//segFlag	1	当前分片序号
    	dos.writeShort(header.getLength());//length	2	后接数据长度
    	dos.writeShort(175);//commandId	2	命令ID
    	dos.writeShort(header.getProtocolNo());//protocolNo	2	协议号
    	dos.writeInt(header.getBusinessSN());//businessSN	4	业务流水号
    	dos.writeByte(header.getSrcDevice());//srcDevice	1	源设备类型
    	dos.writeByte(header.getDstDevice());//dstDevice	1	目标设备类型
    	/****************content***********************/
    	//operation
    	dos.write(dd.IntToSmallByteArray(bean.getOpra()));
    	//content
    	dos.write(dd.ShortToBigByteArray((short)bean.getMscId()));
    	dos.write(dd.IntToSmallByteArray(bean.getIssi()));
    	dos.write(dd.IntToSmallByteArray(bean.getGssi()));
    	dos.write(dd.LongData(bean.getGroupname(), 32));
    	dos.writeByte(bean.getAttached());
    	dos.writeByte(bean.getCou());
    	dos.writeByte(bean.getOperation());
    	dos.writeByte(bean.getStatus());
    	/****************content***********************/       
    	dos.writeShort(header.getChecksum());//checksum	2	校验码
    	
    	byte[] info=bos.toByteArray();
    	out.write(info);

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



}
