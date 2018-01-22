package com.tcpServer;

import java.util.HashMap;
import java.util.Map;

import com.tcpBean.DispatchTable;
import com.tcpBean.DispatchTableAck;
import com.tcpBean.ErrCheck;
import com.tcpBean.ErrCheckAck;
import com.tcpBean.ErrProTable;
import com.tcpBean.ErrProTableAck;
import com.tcpBean.LoginAck;
import com.tcpBean.MovebsTable;
import com.tcpBean.MovebsTableAck;
import com.tcpBean.NetManagerTable;
import com.tcpBean.NetManagerTableAck;
import com.tcpBean.OwnbsTable;
import com.tcpBean.OwnbsTableAck;
import com.tcpBean.UserInfo;
import com.tcpBean.UserInfoAck;
import com.tcpBean.UserLogin;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;





/**
 * 工具类，处理接收的数据
 */

public class Util {
	//接收用对象
	private static UserLogin userLogin;
	private static UserInfo userInfo;
	private static ErrProTableAck errProTableAck;
	private static ErrCheck errCheck;
	private static NetManagerTable netManagerTable;
	private static DispatchTable dispatchTable;
	private static MovebsTable movebsTable;
	private static OwnbsTable ownbsTable;
	
	
	/**
	 * 测试用主方法
	 */
	public static void main(String[] args) {
		String objectStr="{\"cmdtype\":\"userlogin\",\"userid\":\"admin\",\"passwd\":\"12345\",\"serialnumber\":\"qwertyui123\"}"+"\n";
		JSONObject jsonObject=JSONObject.fromObject(objectStr);
		userLogin = (UserLogin) JSONObject.toBean(jsonObject, UserLogin.class);
		System.out.println(userLogin);
		 
	}
	
	/**
	 * 根据消息对象构建Json对象
	 *
	 * @param message
	 * @return
	 */
	public static JSONObject initJsonObject(SocketMessage message) {
		JSONObject jsonObject = new JSONObject();
		try {
			if (message.getType() != Custom.MESSAGE_ACTIVE)
				jsonObject.put("message", message.getMessage());
			else
				jsonObject.put("message", "");
			jsonObject.put("type", message.getType());
			jsonObject.put("from", message.getFrom());
			jsonObject.put("to", message.getTo());
			jsonObject.put("userId", message.getUserId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 解析Json数据（需重写，待定）
	 *
	 * @param json
	 * @return
	 */
	public static Map<String,String> parseJson(String json) {
		Map<String,String> map = new HashMap<String,String>();
		try {		
			JSONObject jsonObject=JSONObject.fromObject(json);
			String cmdtype = (String) jsonObject.get("cmdtype");
			if("userlogin".equals(cmdtype)){
				userLogin = (UserLogin) JSONObject.toBean(jsonObject, UserLogin.class);
				LoginAck loginAck = Service.appLogin(userLogin);
				if("0".equals(loginAck.getAck())){
					map.put("userId", userLogin.getUserid());
				}			
				map.put("returnMessage", Object2Json(loginAck));
				return map;
			}else if("getuserinfo".equals(cmdtype)){
				userInfo = (UserInfo) JSONObject.toBean(jsonObject, UserInfo.class);
				UserInfoAck userInfoAck = Service.appUserInfo(userInfo);
				map.put("returnMessage", Object2Json(userInfoAck));
				return map;			
			}else if("errprotableack".equals(cmdtype)){
				errProTableAck = (ErrProTableAck) JSONObject.toBean(jsonObject, ErrProTableAck.class);
				
			}
						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * java对象转json字符串
	 */
	public static  String Object2Json(Object obj){  
        JSONObject json = JSONObject.fromObject(obj);//将java对象转换为json对象  
        String str = json.toString();//将json对象转换为字符串          
        return str;  
    }  
	
	
}
