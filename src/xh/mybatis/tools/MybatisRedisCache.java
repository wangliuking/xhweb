package xh.mybatis.tools;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import redis.clients.jedis.Jedis;
import xh.func.plugin.RedisUtil;

public class MybatisRedisCache implements Cache {
	private static Log logger = LogFactory.getLog(MybatisRedisCache.class); 

    /** The ReadWriteLock. */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private String id;
    private static final int DB_INDEX = 1;
    private final String COMMON_CACHE_KEY = "COM:";
    private static final String UTF8 = "utf-8";
    private static  Jedis jedis=null;//非切片额客户端连接
    private static  JdkSerializationRedisSerializer jdkSerializer=new JdkSerializationRedisSerializer();
    private static int expireTime=1000*60*60*24;//单位。秒

    /**
     * 按照一定规则标识key
     */
    private String getKey(Object key) {
        StringBuilder accum = new StringBuilder();
        accum.append(COMMON_CACHE_KEY);
        accum.append(this.id).append(":");
        accum.append(DigestUtils.md5Hex(String.valueOf(key)));
        return accum.toString();
    }

    /**
     * redis key规则前缀
     */
    private String getKeys() {
        return COMMON_CACHE_KEY + this.id + ":*";
    }

    public MybatisRedisCache() {

    }

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("必须传入ID");
        }
        jedis=RedisUtil.getJedis();
        logger.info(">>>>>>>>>MybatisRedisCache:id=" + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getSize() {
        int result = 0;
        boolean borrowOrOprSuccess = true;
        try {
            jedis.select(DB_INDEX);
            Set<byte[]> keys = jedis.keys(getKeys().getBytes(UTF8));
            if (null != keys && !keys.isEmpty()) {
                result = keys.size();
            }
            logger.info(this.id + "---->>>>总缓存数:" + result);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
            	RedisUtil.getJedisPool().returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
            	RedisUtil.getJedisPool().returnResource(jedis);
        }
        return result;
    }

    @Override
    public void putObject(Object key, Object value) {
        boolean borrowOrOprSuccess = true;
        try {
            jedis.select(DB_INDEX);
            byte[] keys = getKey(key).getBytes(UTF8);
            jedis.set(keys,jdkSerializer.serialize(value));
            jedis.expire(keys, expireTime);
            logger.info("-------------------------------------------");
            logger.info("添加缓存:key->" + this.id);
            logger.info("过期时间:(seconds)->" + expireTime);
            logger.info("当前keys:keylength->" + jedis.dbSize());
            logger.info("-------------------------------------------");
            // getSize();
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
            	RedisUtil.getJedisPool().returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
            	RedisUtil.getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public Object getObject(Object key) {
        Object value = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis.select(DB_INDEX);
            value = jdkSerializer.deserialize(jedis.get(getKey(key).getBytes(UTF8)));
            logger.info("-------------------------------------------");
            logger.info("从缓存中获取数据：key->" + this.id);
            logger.info("-------------------------------------------");
            // getSize();
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
            	RedisUtil.getJedisPool().returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
            	RedisUtil.getJedisPool().returnResource(jedis);
        }
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        Object value = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis.select(DB_INDEX);
            value = jedis.del(getKey(key).getBytes(UTF8));
            logger.info("LRU算法从缓存中移除-----" + this.id);
            // getSize();
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
            	RedisUtil.getJedisPool().returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
            	RedisUtil.getJedisPool().returnResource(jedis);
        }
        return value;
    }

    @Override
    public void clear() {
        boolean borrowOrOprSuccess = true;
        try {
            jedis.select(DB_INDEX);
            // 如果有删除操作，会影响到整个表中的数据，因此要清空一个mapper的缓存（一个mapper的不同数据操作对应不同的key）
            Set<byte[]> keys = jedis.keys(getKeys().getBytes(UTF8));
            logger.info("-------------------------------------------");
            logger.info("出现CUD操作，清空对应Mapper缓存======>" + keys.size());
           
            for (byte[] key : keys) {
                jedis.del(key);
                logger.info("正在删除缓存key->"+new String(key, "GB2312"));
            }
            logger.info("-------------------------------------------");
            // 下面是网上流传的方法，极大的降低系统性能，没起到加入缓存应有的作用，这是不可取的。
            // jedis.flushDB();
            // jedis.flushAll();
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
            	RedisUtil.getJedisPool().returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
            	RedisUtil.getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
	
}