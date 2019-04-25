package xh.constant;

import java.util.HashMap;
import java.util.Map;

public class ConstantMap {
	private static Map<String,Object> motoResultMap=new HashMap<String,Object>();

	public static Map<String, Object> getMotoResultMap() {
		return motoResultMap;
	}

	public static void setMotoResultMap(Map<String, Object> motoResultMap) {
		ConstantMap.motoResultMap = motoResultMap;
	}
	
	

}
