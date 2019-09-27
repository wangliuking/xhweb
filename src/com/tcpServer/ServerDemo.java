package com.tcpServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.tcpBean.ErrProTable;
import com.tcpBean.UserLogin;

public class ServerDemo {
	private boolean isStartServer = false;
	//未发送的消息
	public static ConcurrentHashMap<String,LinkedList<Object>> unsentMessageList = new ConcurrentHashMap<String,LinkedList<Object>>();
	
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
		ServerSocket ss = new ServerSocket(7788);
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
			List<String> userList = new ArrayList<String>();			
			for (SocketThread st : mThreadList) {// 分别向每个客户端发送消息
				//将所有userId放入userList，用于后续判断是否存在此userId
				userList.add(st.userId);
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
				} else
					continue;
			}
			/*System.out.println("+++++++++++++++++++++");
			System.out.println(userId);
			System.out.println(object);
			System.out.println(userList);
			System.out.println(unsentMessageList);
			System.out.println("+++++++++++++++++++++");*/
			if(!userList.contains(userId)){//此时该用户不在线，保存此派单信息
				if(unsentMessageList.containsKey(userId)){
					//未发送消息中有该用户
					LinkedList<Object> list = unsentMessageList.get(userId);
					list.add(object);
					unsentMessageList.put(userId,list);
				}else{
					//未发送消息中无该用户
					LinkedList<Object> list = new LinkedList<Object>();
					list.add(object);
					unsentMessageList.put(userId,list);
				}
				System.out.println("当前所有连接为："+mThreadList);
				System.out.println("当前所有未发送的信息为："+unsentMessageList);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch blockl
			e.printStackTrace();
		}

	}


	/**
	 * 关闭与SocketThread所代表的客户端的连接
	 * @param
	 * @throws IOException
	 */
	public void closeSocketClient(SocketThread socketThread) {
		try {
			if (socketThread.socket != null && !socketThread.socket.isClosed()) {
				if (socketThread.reader != null)
					socketThread.reader.close();
				if (socketThread.writer != null)
					socketThread.writer.close();
				socketThread.socket.close();
			}
			mThreadList.remove(socketThread);
			socketThread = null;
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("关闭线程出现错误！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			e.printStackTrace();
		}
	
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
					long interval = System.currentTimeMillis() - lastTime;
					if (interval >= (Custom.SOCKET_ACTIVE_TIME * 1000 * 3)) {
						System.out.println("客户端发包间隔时间严重延迟，可能已经断开了：" + interval);
						closeSocketClient(this);
						break;
					}
					if (reader.ready()) {
						lastTime = System.currentTimeMillis();
						//System.out.println("收到消息，准备解析:");
						String data = reader.readLine();
						if(!"{\"cmdtype\":\"heartbeat\"}".equals(data)){
							System.out.println("接收到的数据为："+data);
						}
						Map<String, String> tempMap = Util.parseJson(data);
						String returnMessage = tempMap.get("returnMessage");
						if(!"".equals(returnMessage) && returnMessage!=null){
							if("for".equals(returnMessage)){
								for(int i=0;i<tempMap.keySet().size();i++){
									for(String key:tempMap.keySet()){
										if(key.equals("returnMessage"+i)){
											System.out.println("返回的消息为："+tempMap.get(key));
											writer.write(tempMap.get(key)+"\n");
											writer.flush();
										}
									}
								}
							}else{
								if(!"{\"cmdtype\":\"heartbeat\"}".equals(returnMessage)){
									System.out.println("返回的消息为："+returnMessage);
								}
								writer.write(returnMessage+"\n");
								writer.flush();
							}						
						}
						
						if(tempMap.containsKey("userId")){
							//将此线程与userId进行绑定
							String userId = tempMap.get("userId");
							this.userId = userId;
						}

						if(tempMap.containsKey("unsentMessageList")){
							//查询此userId是否有未发送的消息
							Thread.sleep(2000);
							if(unsentMessageList.containsKey(userId)){
								LinkedList<Object> list = unsentMessageList.get(userId);
								for(int i=0;i<list.size();i++){
									System.out.println("准备发送未收到的消息！！！！！ "+list.get(i));
									startMessageThread(userId,list.get(i));
									Thread.sleep(200);
								}
								unsentMessageList.remove(userId);
							}
						}

						if(!"{\"cmdtype\":\"heartbeat\"}".equals(returnMessage)){
							System.out.println("当前SocketThread的userId为："+userId+"==="+"当前所有连接为："+mThreadList);
							System.out.println("============================");
							for(String key : unsentMessageList.keySet()){
								System.out.println(key);
							}
							System.out.println("============================");
						}

					}
					Thread.sleep(100);
				}
				
			} catch (Exception e) {
				//出现异常，关闭该线程
				System.out.println("出现异常了！！！！！！马上关闭该线程！！！！！！！！！！！！");
				closeSocketClient(this);
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
