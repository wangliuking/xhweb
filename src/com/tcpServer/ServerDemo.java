package com.tcpServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.tcpBean.ErrProTable;
import com.tcpBean.UserLogin;

public class ServerDemo {
	private boolean isStartServer = false;
	public static ArrayList<SocketThread> mThreadList = new ArrayList<SocketThread>();

	public static ArrayList<SocketThread> getmThreadList() {
		return mThreadList;
	}

	public static void setmThreadList(ArrayList<SocketThread> mThreadList) {
		ServerDemo.mThreadList = mThreadList;
	}

	public static void main(String[] args) throws IOException {
		ServerDemo server = new ServerDemo();
		server.start();
	}

	/**
	 * 开启服务端的Socket
	 * @throws IOException
	 */
	public void start() throws IOException {
		// 启动服务ServerSocket，设置端口号
		ServerSocket ss = new ServerSocket(9001);
		System.out.println("服务端已开启，等待客户端连接:");
		isStartServer = true;
		int socketID = 0;
		Socket socket = null;
		while (isStartServer) {
			// 此处是一个阻塞方法，当有客户端连接时，就会调用此方法
			socket = ss.accept();
			System.out.println("客户端连接成功" + socket.getInetAddress());
			// 4. 为这个客户端的Socket数据连接
			SocketThread thread = new SocketThread(socket, socketID++);
			thread.start();
			mThreadList.add(thread);
		}
	}

	public static void startMessageThread(final String userId, final Object object) {
		try {
			for (SocketThread st : mThreadList) {// 分别向每个客户端发送消息
				if (st.socket == null || st.socket.isClosed())
					continue;
				if (st.userId == null || "".equals(st.userId))// 如果暂时没有确定Socket对应的用户Id先不发
					continue;
				// 根据userId模拟服务端向不同的客户端推送消息
				if (st.userId.equals(userId)) {
					BufferedWriter writer = st.writer;
					//BufferedReader reader = st.reader;
					String sendMessage = Util.Object2Json(object) + "\n";
					writer.write(sendMessage);
					writer.flush();
					System.out.println("向客户端" + st.userId + "===" + st.socket.getInetAddress()
							+ "发送了消息" + sendMessage);
					/*boolean result = true;
					while (result) {
						if (reader.ready()) {
							System.out.println("收到消息，准备解析:");
							String data = reader.readLine();
							System.out.println("接受到回复：" + data);
							result = false;
						}
					}*/
				} else
					continue;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch blockl
			e.printStackTrace();
		}

	}


	/**
	 * 关闭与SocketThread所代表的客户端的连接
	 * @param socketThread要关闭的客户端
	 * @throws IOException
	 */
	public void closeSocketClient(SocketThread socketThread) throws IOException {
		if (socketThread.socket != null && !socketThread.socket.isClosed()) {
			if (socketThread.reader != null)
				socketThread.reader.close();
			if (socketThread.writer != null)
				socketThread.writer.close();
			socketThread.socket.close();
		}
		mThreadList.remove(socketThread);
		socketThread = null;
	}

	/**
	 * 客户端Socket线程
	 *
	 */
	public class SocketThread extends Thread {
		public int socketID;
		public Socket socket;//客户端的Socket
		public BufferedWriter writer;
		public BufferedReader reader;
		public String userId;//客户端的UserId
		private long lastTime;

		public SocketThread(Socket socket, int count) {
			socketID = count;
			this.socket = socket;
			lastTime = System.currentTimeMillis();
		}

		@Override
		public void run() {
			super.run();
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				//循环监控读取客户端发来的消息
				while (isStartServer) {
					// 超出了发送心跳包规定时间，说明客户端已经断开连接了这时候要断开与该客户端的连接
					/*long interval = System.currentTimeMillis() - lastTime;
					if (interval >= (Custom.SOCKET_ACTIVE_TIME * 1000 * 4)) {
						System.out.println("客户端发包间隔时间严重延迟，可能已经断开了interval：" + interval);
						System.out.println("Custom.SOCKET_ACTIVE_TIME * 1000:" + Custom.SOCKET_ACTIVE_TIME * 1000);
						closeSocketClient(this);
						break;
					}*/
					if (reader.ready()) {
						lastTime = System.currentTimeMillis();
						System.out.println("收到消息，准备解析:");					
						String data = reader.readLine();
						System.out.println("接收到的数据为："+data);
						Map<String, String> tempMap = Util.parseJson(data);
						if(tempMap.containsKey("userId")){
							this.userId=tempMap.get("userId");
						}
						System.out.println("当前SocketThread的userId为："+userId);
						
						String returnMessage = tempMap.get("returnMessage");
						if(!"".equals(returnMessage) && returnMessage!=null){
							System.out.println("返回的消息为："+returnMessage);
							writer.write(returnMessage+"\n");
							writer.flush();
						}						
						
						
						/*SocketMessage from = Util.parseJson(data);
						//给UserID赋值，此处是我们项目的需求，根据客户端不同的UserId来分别进行推送
						if (userId == null || "".equals(userId))
							userId = from.getUserId();
						System.out.println(from);
						SocketMessage to = new SocketMessage();
						if (from.getType() == Custom.MESSAGE_ACTIVE) {//心跳包
							System.out.println("收到心跳包：" + socket.getInetAddress());
							to.setType(Custom.MESSAGE_ACTIVE);
							to.setFrom(Custom.NAME_SERVER);
							to.setTo(Custom.NAME_CLIENT);
							to.setMessage("");
							to.setUserId(userId);
							writer.write(Util.initJsonObject(to).toString() + "\n");
							writer.flush();
						} else if (from.getType() == Custom.MESSAGE_CLOSE) {//关闭包
							System.out.println("收到断开连接的包：" + socket.getInetAddress());
							to.setType(Custom.MESSAGE_CLOSE);
							to.setFrom(Custom.NAME_SERVER);
							to.setTo(Custom.NAME_CLIENT);
							to.setMessage("");
							to.setUserId(userId);
							writer.write(Util.initJsonObject(to).toString() + "\n");
							writer.flush();
							closeSocketClient(this);
							break;
						} else if (from.getType() == Custom.MESSAGE_EVENT) {//事件包，客户端可以向服务端发送自定义消息
							System.out.println("收到普通消息包：" + from.getMessage());
							writer.write("OK!已收到！");
							writer.flush();
						}*/
					}
					Thread.sleep(100);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	public class TestServer{	
		public void test(){
			System.out.println(mThreadList);
		}
	}
}
