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
import xh.func.plugin.RedisUtil;
import xh.mybatis.bean.UserBean;

public class UserRedis {
	private static Jedis jedis;
	protected final Log log4j = LogFactory.getLog(UserRedis.class);
	
	/**
	 *判断key值是否存在 jedis.exists("key");
	 *查看某个key的剩余生存时间,单位【秒】，永久生存或者不存在的都返回-1：jedis.ttl("key")
	 *移除某个key的生存时间 jedis.persist("key001")
	 *查看key所储存的值的类型 jedis.type("key001")
	 *修改键名jedis.rename("key6", "key0")
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		setUp();
		try {
			SaveString();
			//RedisMap();
			//userSet();
		
		} catch (JedisConnectionException e) {
			// TODO: handle exception
			System.out.println("系统连接异常"+e.fillInStackTrace());
		}
		
	}
	public static void setUp() {
		 //连接redis服务器，127.0.0.1:6379
		try {
			//jedis = new Jedis("127.0.0.1", 6379);
			jedis=RedisUtil.getJedis();
			//jedis.flushDB();//清空库中的所有数据；
			Set<String> keys = jedis.keys("*"); 
	        Iterator<String> it=keys.iterator();
	        System.out.println("=============all keys=============");   
	        while(it.hasNext()){   
	            String key = it.next();   
	            System.out.println(key);   
	        }
	        System.out.println("=============================="); 
			
			
		}catch (JedisConnectionException e) {
			// TODO: handle exception
			System.out.println("系统连接异常"+e.fillInStackTrace());
		}
	}
	public static void SaveString(){
		jedis.set("username", "张三");	
		//jedis.expire("username",30);score1030500
		System.out.println(jedis.get("username"));
		//System.out.println(jedis.get("score1030500"));
	}
	public static void userSet(){
		 List<UserBean> users = new ArrayList<UserBean>(); 
		  for (int i = 0; i < 5; i++) {  
	            UserBean user=new UserBean();  
	            user.setId(i);  
	            user.setName("zhang" + i);   
	            user.setAge(20 + i);  
	            users.add(user);  
	      } 
		  Map<String, UserBean> map = new HashMap<String, UserBean>();
		  // 通过Hash存放  
	      /*Map<String, String> map = new HashMap<String, String>(); 
	        for (int i = 0; i < users.size(); i++) {  
	            map.put(users.get(i).getId() + "",  
	                    GsonUtil.object2Json(users.get(i)));  
	  
	            jedis.sadd("user_table_man", users.get(i).getId() + "");  
	           
	        } */
	        /*jedis.hmset("user", map); */
	        
	} 

}
