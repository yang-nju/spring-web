package com.jsc.dao.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisDataSouce {

//	private static final Logger log = Logger.getLogger(RedisDataSouce.class);
	
	@Autowired
	private ShardedJedisPool redisPool;
	
	/**
	 * 获取redis客户端
	 * @return
	 */
	public ShardedJedis getRedisClient(){
		
		ShardedJedis shardJedis = redisPool.getResource();
		
		return shardJedis;
	}
	
	/**
	 * 关闭/释放资源
	 * @param jedis
	 */
	public void close(ShardedJedis jedis){
		jedis.close();
	}
	
	
}
