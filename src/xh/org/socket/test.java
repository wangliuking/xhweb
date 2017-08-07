package xh.org.socket;

import xh.func.plugin.Base64Util;
import cn.com.scca.signgw.api.SccaGwSDK;

public class test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Base64Util.decode("MTIzM2RmZHNnc2Rnc2dzZ3NkZ2dzMjMy"));
		
		
	}
	

	public static String tt(String str) {
		int lastPos = 0;
		int a = 0;
		StringBuffer astr = new StringBuffer();
		if (str.length() == 0) {
			return "";
		}
		if (!str.startsWith("{")) {
			return "";
		}
		for (int i = 0; i < str.length(); i++) {
			if (String.valueOf(str.charAt(i)).equals("{")) {
				a++;
				/* System.out.println("point-> a++"+a); */
			} else if (String.valueOf(str.charAt(i)).equals("}")) {
				a--;
				/* System.out.println("point-> a--"+a); */
				if (a == 0) {
					astr.append(str.substring(lastPos, i + 1) + ";");
					lastPos = i + 1;
				}
			} else {

			}

		}
		if (lastPos < str.length()) {
			astr.append(str.substring(lastPos, str.length()));
		}
		System.out.println("a===>"+a);
		return astr.toString();
	}

}
