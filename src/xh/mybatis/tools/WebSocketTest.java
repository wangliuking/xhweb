package xh.mybatis.tools;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocketTest {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TextMessage text = null;
		
		WebSocketTest webSocketTest = new WebSocketTest();
		webSocketTest.getWebSocketPushHandler().sendMessagesToUsers(text);
	}
	
	/**
	 * 注入发送消息类实例
	 * @return
	 */
	@Bean
	public WebSocketPushHandler getWebSocketPushHandler() {
		return new WebSocketPushHandler();
	}
	
	/**
	 * 根据用户获取消息内容
	 */
	public TextMessage getMsgByUserId(String userId){
		ObjectMapper mapper = new ObjectMapper();
		String msg = "";
		try {
			msg = mapper.writeValueAsString("");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return new TextMessage(msg);
	}

	

}
