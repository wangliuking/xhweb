package xh.func.plugin;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RedisUtil {
	
	private static PropertiesUtil pUtil=new PropertiesUtil();
	 //Redis服务器IP
    private static String ADDR1 = pUtil.ReadConfig("ip1");
    //Redis服务器备用IP
    private static String ADDR2 = pUtil.ReadConfig("ip2");
    //Redis的端口号      
    private static int PORT = Integer.parseInt(pUtil.ReadConfig("port"));   
    //访问密码
    private static String AUTH = "admin";   
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = Integer.parseInt(pUtil.ReadConfig("MAX_ACTIVE"));    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;  
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 1000;    
    private static int TIMEOUT = 10000;
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
   // private static JedisPool jedisPool = null;
    private static  Jedis jedis;//非切片额客户端连接
    private static  JedisPool jedisPool;//非切片连接
    private static  ShardedJedis shardedJedis;//切片额客户端连接
    private static  ShardedJedisPool shardedJedisPool;//切片连接池
    
    protected final static Log log4j = LogFactory.getLog(RedisUtil.class);

    public static void main(String[] args) {
        boolean b = isHostConnectable("192.168.120.150",6379);
        System.out.println(" b : "+b);
    }

    /**
     * 初始化非切片池
     */
    private static void initialPool() {
        //判断ip是否有效(用ping的机制)
        try {
            // 池基本配置
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            if(jedisPool==null){
                jedisPool = new JedisPool(config, ADDR1, PORT, TIMEOUT);
            }
            /*if(ping(ADDR1)){
                System.out.println("ADDR1 ："+ADDR1+"可用");
                // 池基本配置
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxActive(MAX_ACTIVE);
                config.setMaxIdle(MAX_IDLE);
                config.setMaxWait(MAX_WAIT);
                config.setTestOnBorrow(TEST_ON_BORROW);
                if(jedisPool==null){
                    jedisPool = new JedisPool(config, ADDR1, PORT, TIMEOUT);
                }
            }else{
                System.out.println("ADDR2 ："+ADDR2+"可用");
                // 池基本配置
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxActive(MAX_ACTIVE);
                config.setMaxIdle(MAX_IDLE);
                config.setMaxWait(MAX_WAIT);
                config.setTestOnBorrow(TEST_ON_BORROW);
                if(jedisPool==null){
                    jedisPool = new JedisPool(config, ADDR2, PORT, TIMEOUT);
                }
            }*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /** 
     * 初始化切片池 
     */ 
    private void initialShardedPool() { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxActive(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWait(MAX_WAIT);
        config.setTestOnBorrow(TEST_ON_BORROW);
        // slave链接 
       /* List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
        shards.add(new JedisShardInfo("127.0.0.1", 6379, "master")); */

        // 构造池 
       // shardedJedisPool = new ShardedJedisPool(config, shards); 
    } 
    
    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
    	
        try {
        	initialPool();
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (JedisConnectionException e) {
        	
        	/*log4j.info("error:Could not get a resource from the pool");*/
        	/*log4j.error(e.getMessage(), e);*/
        	log4j.error("获取redis服务失败");
           
            return null;
        }
    }
    
    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

	public static JedisPool getJedisPool() {
		return jedisPool;
	}

	public static void setJedisPool(JedisPool jedisPool) {
		RedisUtil.jedisPool = jedisPool;
	}

    /**
     * ping ip是否可以通
     * @param ipAddress
     * @return
     * @throws Exception
     */
    public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  3000 ;  //超时应该在3钞以上
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }

    /**
     * 检测端口是否可以连接
     * @param host
     * @param port
     * @return
     */
    public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    
}
