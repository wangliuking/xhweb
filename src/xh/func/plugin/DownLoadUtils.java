package xh.func.plugin;
 
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 解决文件下载时，中文不被浏览器识别的问题
 * 
 * @author 李长军
 *
 */
public class DownLoadUtils {
	public static String getName(String agent, String filename) throws UnsupportedEncodingException {
		if (agent.contains("MSIE")) {
			// IE浏览器
			filename = URLEncoder.encode(filename, "utf-8");
			filename = filename.replace("+", " ");
		} else if (agent.contains("Firefox")) {
			// 火狐浏览器
			/*
			Encoder base64Encoder = Base64.getEncoder();
			filename = "=?utf-8?B?" + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";*/
		} else {
			// 其它浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}
}
