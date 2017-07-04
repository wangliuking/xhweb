package xh.mybatis.tools;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.cache.Cache;
import xh.func.plugin.RedisUtil;

public class MybatisRedisCache implements Cache {
	private static Log logger = LogFactory.getLog(MybatisRedisCache.class); 
	/** The ReadWriteLock. */ 
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private String id;
	
	public MybatisRedisCache(final String id) {  
		if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
		logger.debug(">>>MybatisRedisCache:id="+id);
        this.id = id;
    }  
	
	public String getId() {
		return this.id;
	}

	public void putObject(Object key, Object value) {
		logger.debug(">>>putObject:"+key+"="+value);
		RedisUtil.getJedis().set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
	}

	public Object getObject(Object key) {
		Object value = SerializeUtil.unserialize(RedisUtil.getJedis().get(SerializeUtil.serialize(key.toString())));
		logger.debug(">>>getObject:"+key+"="+value);
		return value;
	}

	public Object removeObject(Object key) {
		return RedisUtil.getJedis().expire(SerializeUtil.serialize(key.toString()),0);
	}

	public void clear() {
		RedisUtil.getJedis().flushDB();
	}

	public int getSize() {
		return Integer.valueOf(RedisUtil.getJedis().dbSize().toString());
	}

	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}
	
}