package xh.func.plugin;
 
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;


public class Base64Util {
	
	/**
	 * 编码
	 * @param str
	 * @return
	 */
	public static String encode(String str){
		Base64 base64=new Base64();
		String result="";
		/*try {
			byte[] text=str.getBytes("UTF-8");
			result = base64.encodeToString(text);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return result;
	}

    /**
     * 解码
     * @param str
     * @return
     */
	public static String decode(String str){
		Base64 base64=new Base64();
		String result="";
		/*try {
			result = new String(base64.decode(str), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return result;
		
		
	}

}
