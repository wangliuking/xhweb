package xh.mybatis.bean;
 
import java.util.HashMap;

public class GlobalVarBean {
	//登录用户map
	private static HashMap<String, String> logUserMap=new HashMap<String, String>();

	public static HashMap<String, String> getLogUserMap() {
		return logUserMap;
	}

	public static void setLogUserMap(HashMap<String, String> logUserMap) {
		GlobalVarBean.logUserMap = logUserMap;
	}

	
	
	

}
