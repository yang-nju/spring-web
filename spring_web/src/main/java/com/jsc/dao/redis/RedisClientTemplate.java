package com.jsc.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedis;

public class RedisClientTemplate {

	@Autowired
	private RedisDataSouce dataSource;
	
	public void disconnect(){
		ShardedJedis jedis = dataSource.getRedisClient();
		jedis.disconnect();
	}
	
	
	
	public String set(String key, String value){
		String result = null;
		ShardedJedis jedis = dataSource.getRedisClient();
		if(jedis == null){
			return result;
		}
		result = jedis.set(key, value);
		return result;
	}
	
	public String get(String key){
		
		ShardedJedis jedis = dataSource.getRedisClient();
		return jedis.get(key);
		
	}
	
	
}
