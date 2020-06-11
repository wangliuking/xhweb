package xh.redis.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import xh.func.plugin.GsonUtil;
import xh.func.plugin.PropertiesUtil;
import xh.func.plugin.RedisUtil;
import xh.mybatis.bean.UserBean;
import xh.mybatis.service.WebUserServices;
import xh.org.listeners.SingLoginListener;

public class UserRedis {
	protected final Log log4j = LogFactory.getLog(UserRedis.class);
	
	/**
	 *判断key值是否存在 jedis.exists("key");
	 *查看某个key的剩余生存时间,单位【秒】，永久生存或者不存在的都返回-1：jedis.ttl("key")
	 *移除某个key的生存时间 jedis.persist("key001")
	 *查看key所储存的值的类型 jedis.type("key001")
	 *修改键名jedis.rename("key6", "key0")
	 * @param args
	 */
	
	/**
	 * 根据sessionId查询redis1是否有用户登录
	 */
	public static String searchUserInRedisOne(String sessionId){
		String userId;
		//获取redis连接
		Jedis jedis = RedisUtil.getJedis();
		//切换到redis1数据库，查询用户是否已经登录
		jedis.select(1);
		//根据sessionId查询redis1数据库中是否有用户
		if(jedis.exists(sessionId)){
			userId = jedis.hgetAll(sessionId).get("user");
		}else{
			userId = null;
		}
		//释放redis连接资源
		RedisUtil.returnResource(jedis);
		return userId;
	}
	
	/**
	 * 单点登录session处理
	 */
	public static void ssoSession(Map<String, Object> info,Map<String, Object> power,String sUserName,String sessionId) {
		// 设置redis0和redis1的session失效时间
		PropertiesUtil pUtil=new PropertiesUtil();
		int sessionTimeOut = Integer.parseInt(pUtil.ReadConfig("sessionTimeOut"));
		// 组合新的infomap
		Map<String, String> finalInfo = new HashMap<String, String>();
		for (String str : info.keySet()) {
			finalInfo.put(str, info.get(str).toString());
		}
		//组合新的powerInfo
		Map<String, String> powerInfo = new HashMap<String, String>();
		for (String str : power.keySet()) {
			powerInfo.put(str, power.get(str).toString());
		}
		//获取redis连接
		Jedis jedis = RedisUtil.getJedis();
		//设置redis0 key失效时间
		jedis.expire(sessionId, sessionTimeOut);// redis0
		//切换到redis1数据库，查询用户是否已经登录
		jedis.select(1);
		// 遍历redis1所有sessionId，查询是否有该用户登录过
		Set s = jedis.keys("*");
		Iterator it = s.iterator();
		// 标志状态位(是否查询到该用户session)
	/*	int status = 0;
		//标志是否已添加此session
		boolean sessionStatus = true;
		while (it.hasNext()) {
			String key = (String) it.next();
			Map<String, String> tMap = jedis.hgetAll(key);
			if (tMap.get("user").equals(sUserName)) {
				status = 1;
				// 存在此用户登录的session，将此session踢掉
				jedis.del(key);
				// 重新添加新的session
				if(sessionStatus){
					jedis.hmset(sessionId, finalInfo);
					sessionStatus = false;
				}				
			}
		}*/
		// 若未查询到此用户session存在，则添加新的session
		/*if (status == 0) {
			jedis.hmset(sessionId, finalInfo);
		}*/
		jedis.hmset(sessionId, finalInfo);
		//设置redis1 key失效时间
		jedis.expire(sessionId, sessionTimeOut);// redis1

		//切换到redis2数据库，查询用户是否已经登录
		jedis.select(2);
		// 遍历redis2所有sessionId，查询是否有该用户登录过
		Set s2 = jedis.keys("*");
		Iterator it2 = s2.iterator();
		// 标志状态位(是否查询到该用户session)
		/*int status2 = 0;
		//标志是否已添加此session
		boolean sessionStatus2 = true;
		while (it2.hasNext()) {
			String key = (String) it2.next();
			Map<String, String> tMap = jedis.hgetAll(key);
			if (tMap.get("user").equals(sUserName)) {
				status2 = 1;
				// 存在此用户登录的session，将此session踢掉
				jedis.del(key);
				// 重新添加新的session
				if(sessionStatus2){
					jedis.hmset(sessionId, powerInfo);
					sessionStatus2 = false;
				}
			}
		}*/
		// 若未查询到此用户session存在，则添加新的session
		/*if (status2 == 0) {
			jedis.hmset(sessionId, powerInfo);
		}*/
		jedis.hmset(sessionId, powerInfo);
		jedis.expire(sessionId, sessionTimeOut);// redis2
		//释放redis连接资源
		RedisUtil.returnResource(jedis);
	}

	/**
	 * 删除redis0库中的session(当session失效时)
	 */
	public static void delSessionDisConnect(String sessionId) {
		//获取redis连接
		Jedis jedis = RedisUtil.getJedis();
		jedis.select(0);
		jedis.del(sessionId);
		//释放redis连接资源
		RedisUtil.returnResource(jedis);
	}
	
	/**
	 * 删除redis1库中的session(用户手动退出时)
	 */
	public static void delSession(String sessionId) {
		//获取redis连接
		Jedis jedis = RedisUtil.getJedis();
		jedis.select(1);
		jedis.del(sessionId);
		//释放redis连接资源
		RedisUtil.returnResource(jedis);
	}
	
	/**
	 * 获取redis1库中的所有session信息，将其添加到tomcat的session信息中
	 */
	public static void getAllUserSession() {
		// 获取redis连接
		Jedis jedis = RedisUtil.getJedis();
		jedis.select(1);
		// 遍历redis1所有sessionId
		Set s = jedis.keys("*");
		Iterator it = s.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Map<String, String> tMap = jedis.hgetAll(key);
			//判断logUserMap中是否有相同的key
			if(!compareRedisDataToLogUserMap(key)){
				//logUserMap中不含有此key，进行添加
				//添加sessionID和username的映射logUserMap
				SingLoginListener.logUserMap.put(key, tMap.get("user"));
			}
			//判断logUserInfoMap中是否有相同的key
			if(!compareRedisDataToLogUserInfoMap(key)){
				//logUserInfoMap中不含有此key，进行添加
				//添加用户信息
				SingLoginListener.logUserInfoMap.put(key, mapStrToObj(tMap));
			}
		}

		//切换到redis2，同步用户权限配置
		jedis.select(2);
		// 遍历redis2所有sessionId
		Set s2 = jedis.keys("*");
		Iterator it2 = s2.iterator();
		while (it2.hasNext()) {
			String key = (String) it2.next();
			Map<String, String> tMap = jedis.hgetAll(key);
			//判断loginUserPowerMap中是否有相同的key
			if(!compareRedisDataToLoginUserPowerMap(key)){
				//loginUserPowerMap中不含有此key，进行添加
				//添加用户权限信息
				SingLoginListener.loginUserPowerMap.put(key, WebUserServices.userPowerInfoByName(tMap.get("user")));
			}
		}
		// 释放redis连接资源
		RedisUtil.returnResource(jedis);
	}

	/**
	 * 比对logUserMap中的key，查询是否存在
	 * @param key
	 * @return
	 */
	public static boolean compareRedisDataToLogUserMap(String key){
		Set s = SingLoginListener.logUserMap.keySet();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			String logUserMapKey = (String) it.next();
			if(logUserMapKey.equals(key)){
				//logUserMap存在与redis相同的key
				return true;
			}
		}
		return false;
	}

	/**
	 * 比对logUserInfoMap中的key，查询是否存在
	 * @param key
	 * @return
	 */
	public static boolean compareRedisDataToLogUserInfoMap(String key){
		Set s = SingLoginListener.logUserInfoMap.keySet();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			String logUserInfoMapKey = (String) it.next();
			if(logUserInfoMapKey.equals(key)){
				//logUserInfoMap存在与redis相同的key
				return true;
			}
		}
		return false;
	}

	/**
	 * 比对loginUserPowerMap中的key，查询是否存在
	 * @param key
	 * @return
	 */
	public static boolean compareRedisDataToLoginUserPowerMap(String key){
		Set s = SingLoginListener.loginUserPowerMap.keySet();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			String loginUserPowerMapKey = (String) it.next();
			if(loginUserPowerMapKey.equals(key)){
				//loginUserPowerMap存在与redis相同的key
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Map<String,String>转Map<String,Object>
	 */
	public static Map<String,Object> mapStrToObj(Map<String,String> info){
		Map<String, Object> finalInfo = new HashMap<String, Object>();
		for (String str : info.keySet()) {
			finalInfo.put(str, info.get(str));
		}
		return finalInfo;
	}

}
