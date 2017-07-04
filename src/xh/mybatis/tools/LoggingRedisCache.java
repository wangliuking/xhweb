package xh.mybatis.tools;

import org.apache.ibatis.cache.decorators.LoggingCache;

public class LoggingRedisCache extends LoggingCache{

	public LoggingRedisCache(String id) {
		super(new MybatisRedisCache(id));
		// TODO Auto-generated constructor stub
	}

}
