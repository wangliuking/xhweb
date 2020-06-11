package xh.org.listeners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FunUtil;
import xh.mybatis.service.WebUserServices;
import xh.redis.server.UserRedis;

public class SingLoginListener implements HttpSessionListener{
	protected final static Log log = LogFactory.getLog(SingLoginListener.class);
	private static FunUtil funUtil=new FunUtil();
    // 保存sessionID和username的映射
    public static HashMap logUserMap = new HashMap();
    //保存登录用户信息
    public static HashMap<String,Map<String, Object>> logUserInfoMap = new HashMap<String, Map<String,Object>>();
    //保存登录用户权限信息
    public static HashMap<String,Map<String, Object>> loginUserPowerMap = new HashMap<String, Map<String,Object>>();

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		log.info("session:"+se.getSession().getId()+"已失效");
		logUserMap.remove(se.getSession().getId());
		logUserMap.remove(se.getSession().getId()+"-pass");
		logUserInfoMap.remove(se.getSession().getId());
		loginUserPowerMap.remove(se.getSession().getId());
		//redis start
		//session失效时删除对应redis内的消息
        UserRedis.delSessionDisConnect(se.getSession().getId());
        UserRedis.delSession(se.getSession().getId());
        //redis end
		/*se.getSession().invalidate();*/
	}
	 /** 
     * 用于判断用户是否已经登录以及相应的处理方法 
     *  
     * @param sUserName 
     *            String-登录的用户名称 
     * @return boolean-该用户是否已经登录过的标志 
     */  
    public static boolean isLogin(HttpSession session, String sUserName,String password) {  
        boolean flag = false;  
        Map<String, Object> info = WebUserServices.userInfoByName(sUserName);
        Map<String, Object> power = WebUserServices.userPowerInfoByName(sUserName);
        if(info.get("vpnId")==null){
        	info.put("vpnId", "");
        }        
        //redis start      
        String sessionId = session.getId();				
		
        //redis end
		
        // 如果该用户已经登录过，则使上次登录的用户掉线(依据使用户名是否在logUserMap中)  
     /*   if (logUserMap.containsValue(sUserName)) {  
            flag = true;  
            // 遍历原来的logUserMap，删除原用户名对应的sessionID(即删除原来的sessionID和username)  
            Iterator iter = logUserMap.entrySet().iterator(); 
            while (iter.hasNext()) {  
                Map.Entry entry = (Map.Entry) iter.next();  
                Object key = entry.getKey();  
                Object val = entry.getValue();     
                if (((String) val).equals(sUserName)) {  
                    //logUserMap.remove(key);  
                	iter.remove();
                    
                } 
                if (((String) val).equals(password)) {  
                    //logUserMap.remove(key);
                	iter.remove();
                } 
            }  
            // 添加现在的sessionID和username  
            logUserMap.put(session.getId(), sUserName); 
            logUserMap.put(session.getId()+"-pass", password); 
            logUserInfoMap.remove(session.getId());
            logUserInfoMap.put(session.getId(), info);
            
            loginUserPowerMap.remove(session.getId());
            loginUserPowerMap.put(session.getId(), power);
        } else {// 如果该用户没登录过，直接添加现在的sessionID和username  
            flag = false;  
            logUserMap.put(session.getId(), sUserName); 
            logUserMap.put(session.getId()+"-pass", password); 
            logUserInfoMap.put(session.getId(), info);
            loginUserPowerMap.put(session.getId(), power);
        } */
        logUserMap.put(session.getId(), sUserName); 
        logUserMap.put(session.getId()+"-pass", password); 
        logUserInfoMap.put(session.getId(), info);
        loginUserPowerMap.put(session.getId(), power);
        UserRedis.ssoSession(info, power, sUserName, sessionId);
        
        log.info("登录用户数=>"+logUserInfoMap.size());
       // log.info("登录用户MAP=>"+logUserMap);
        return flag;  
    } 
    /** 
     *用于判断用户是否在线 
     *  
     * @param session 
     *            HttpSession-登录的用户名称 
     * @return boolean-该用户是否在线的标志 
     */  
    public static boolean isOnline(HttpSession session) {  
        boolean flag = true;  
        /*log.info("sessionID   =>   " + session.getId()); 
        log.info("logUserMap   =>   " + logUserMap); 
        log.info("logUserInfoMap   =>   " + logUserInfoMap); */
        if (logUserMap.containsKey(session.getId())) {  
            flag = true;  
        } else {  
            flag = false;  
        }  
        return flag;  
    }

	public static HashMap<String, Map<String, Object>> getLoginUserPowerMap() {
		return loginUserPowerMap;
	}

	public static void setLoginUserPowerMap(
			HashMap<String, Map<String, Object>> loginUserPowerMap) {
		SingLoginListener.loginUserPowerMap = loginUserPowerMap;
	}

	public static HashMap getLogUserMap() {
		return logUserMap;
	}

	public static void setLogUserMap(HashMap logUserMap) {
		SingLoginListener.logUserMap = logUserMap;
	}

	public static HashMap<String, Map<String, Object>> getLogUserInfoMap() {
		return logUserInfoMap;
	}

	public static void setLogUserInfoMap(
			HashMap<String, Map<String, Object>> logUserInfoMap) {
		SingLoginListener.logUserInfoMap = logUserInfoMap;
	}  
    
  

}
