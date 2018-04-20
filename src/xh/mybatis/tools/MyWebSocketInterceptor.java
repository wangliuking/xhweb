package xh.mybatis.tools;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import xh.org.listeners.SingLoginListener;

/**
 * websocket握手类，用于建立连接之前
 * @author ts
 *
 */
public class MyWebSocketInterceptor implements HandshakeInterceptor{
		
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		//将ServerHttpRequest转换成request请求相关的类，用来获取request域中的用户信息	
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest  = (ServletServerHttpRequest) request;	
			HttpServletRequest httpRequest = servletRequest.getServletRequest();
			//通过request域获取用户信息
			HashMap tempMap = (HashMap) SingLoginListener.getLogUserInfoMap().get(httpRequest.getSession().getId());
			//把用户名存入WebSocketSession，后续可以调用方法获取websocket会话的用户名
			attributes.put("userName",tempMap.get("userName"));
		}
		return true;
	}
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		System.out.println("握手成功，建立websocket连接");
	}
}
