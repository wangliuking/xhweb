package xh.org.listeners;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

/**
 * send text
 * 
 * @author klay
 * 
 */
@SuppressWarnings("deprecation")
public class SendText {

	public static String requestUrl = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg";
	public static String cookie = "";

	public static void sendMsg(String requestUrl, String cookie) {
		String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg";// 请求链接
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.addHeader(new BasicHeader("cookie", cookie)); // 其实cookie可以不设置，也能发送
		Long currentTimeMillis = System.currentTimeMillis();
		
		String requestParam = "{\"BaseRequest\":"
				+ "{\"Uin\":2101879619,"
				+ "\"Sid\":\"/Uw7Q2a5A75MJWwl\","
				+ "\"Skey\":\"@crypt_f2aa539c_25c66b651cff661cf5dc4a2fb3e31cce\","
				+ "\"DeviceID\":\"e345245611239124\"},"
				+ "\"Msg\":{\"Type\":1,"
				+ "\"Content\":\"123\","
				+ "\"FromUserName\":\"@1cee88e90d91cdf62b392ad2329b47ed914beecec3f065008e35ed34fa91f591\","
				+ "\"ToUserName\":\"@@eb4317bcb9028fee3ab9c34cc6063bd1e7c9966b2e4288ce3aa6c67b29fb9e8c\","
				+ "\"LocalID\":\""+ currentTimeMillis
				+ "\",\"ClientMsgId\":\""+ currentTimeMillis + "\"}}";
		try {
			StringEntity s = new StringEntity(requestParam);
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		sendMsg(requestUrl, cookie);
	}

}