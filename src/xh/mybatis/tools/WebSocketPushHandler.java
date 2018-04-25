package xh.mybatis.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import xh.org.listeners.SingLoginListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 消息处理类（包括websocket建立连接后，断开连接后，发送信息等相关操作）
 * @author ts
 *
 */
public class WebSocketPushHandler implements WebSocketHandler{
	
	public static final List<WebSocketSession> users = new ArrayList<WebSocketSession>();
	
	//用户进入系统监听，建立websocket连接后执行
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("用户名："+session.getHandshakeAttributes().get("userName").toString()+" sessionId为： "+session.getId()+"---成功完成了websocket连接。。。");
		users.add(session);
		System.out.println("当前所有session为："+users.toString());
	}

	//
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
	    //将消息进行转化，因为是消息是json数据，可能里面包含了发送给某个人的信息，所以需要用json相关的工具类处理之后再封装成TextMessage，
	   // 我这儿并没有做处理，消息的封装格式一般有{from:xxxx,to:xxxxx,msg:xxxxx}，来自哪里，发送给谁，什么消息等等	    
	    TextMessage msg = (TextMessage)message.getPayload();
	    //给所有用户群发消息
	    sendMessagesToUsers(msg);
	    //给指定用户群发消息
	    //sendMessageToUser(userId,msg);
	}
        
        //后台错误信息处理方法
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		//System.out.println("进入handleTransportError方法，可能是什么出错了！！！");
	}

	//用户退出后的处理，不如退出之后，要将用户信息从websocket的session中remove掉，这样用户就处于离线状态了，也不会占用系统资源
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		if(session.isOpen()){
			session.close();
		}
		users.remove(session);
		System.out.println("用户名："+session.getHandshakeAttributes().get("userName").toString()+" sessionId为： "+session.getId()+"---已经断开了websocket连接。。。");
		
	}

	@Override
	public boolean supportsPartialMessages() {
		//System.out.println("进入了supportsPartialMessages方法，这个不知道什么玩意儿？？？");
		return false;
	}
	
	/**
	 * 给所有的用户发送消息
	 */
	public void sendMessagesToUsers(TextMessage message){
		System.out.println("准备给所有用户发送信息了！！！！！");
		for(WebSocketSession user : users){
			try {
			    //isOpen()在线就发送
				if(user.isOpen()){
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 发送消息给指定的用户
	 */
	public void sendMessageToUser(String userId,TextMessage message){
		System.out.println("准备给"+userId+"发送信息了！！！！！");
		for(WebSocketSession user : users){
			if(user.getHandshakeAttributes().get("userName").equals(userId)){
				try {
				    //isOpen()在线就发送
					if(user.isOpen()){
						user.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}