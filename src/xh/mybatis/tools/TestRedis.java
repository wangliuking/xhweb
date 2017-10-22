package xh.mybatis.tools;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class TestRedis {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server sucessfully");
		// store data in redis list
		// Get the stored data and print it
		jedis.select(0);
		for(int i=1;i<=jedis.dbSize();i++){
			Map<String,String> map = jedis.hgetAll("1");
			System.out.println(map);
		}
	}
}
