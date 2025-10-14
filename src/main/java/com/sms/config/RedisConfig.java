package com.sms.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.sentinel.master}")
	private String redisMaster;

	@Value("#{'${spring.redis.sentinel.nodes}'.split(',')}")
	private List<String> redisNodes;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.database}")
	private int database;

	/**
	 * 配置 Redis 哨兵模式
	 */
	@Bean
	public RedisSentinelConfiguration sentinelConfiguration() {
		RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
		redisSentinelConfiguration.master(redisMaster);

		// 配置 Redis Sentinel 节点
		Set<RedisNode> redisNodeSet = new HashSet<>();
		redisNodes.forEach(node -> {
			String[] parts = node.split(":");
			redisNodeSet.add(new RedisNode(parts[0], Integer.parseInt(parts[1])));
		});

		redisSentinelConfiguration.setSentinels(redisNodeSet);
		redisSentinelConfiguration.setDatabase(database);
		redisSentinelConfiguration.setPassword(password);
		return redisSentinelConfiguration;
	}

	/**
	 * 创建 Redis 连接工厂
	 */
	@Bean
	public LettuceConnectionFactory redisConnectionFactory(RedisSentinelConfiguration sentinelConfiguration) {
		return new LettuceConnectionFactory(sentinelConfiguration);
	}

	/**
	 * 配置 RedisTemplate
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		// 统一使用 String 作为 Key 的序列化方式
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(jsonRedisSerializer);
		redisTemplate.setHashValueSerializer(jsonRedisSerializer);

		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
//
//	@Value("${spring.redis.host}")
//	private String redisHost;
//
//	@Value("${spring.redis.port}")
//	private int redisPort;
//
//	@Value("${spring.redis.password:}") // 允许为空
//	private String redisPassword;
//
//	@Value("${spring.redis.database}")
//	private int database;
//
//	/**
//	 * 配置单点 Redis 连接
//	 */
//	@Bean
//	public LettuceConnectionFactory redisConnectionFactory() {
//		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//		config.setHostName(redisHost);
//		config.setPort(redisPort);
//		config.setDatabase(database);
//
//		if (redisPassword != null && !redisPassword.isEmpty()) {
//			config.setPassword(RedisPassword.of(redisPassword));
//		}
//
//		return new LettuceConnectionFactory(config);
//	}
//
//	/**
//	 * 配置 RedisTemplate
//	 */
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//		// 统一 Key 和 Value 序列化方式
//		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//		GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//
//		redisTemplate.setKeySerializer(stringRedisSerializer);
//		redisTemplate.setHashKeySerializer(stringRedisSerializer);
//		redisTemplate.setValueSerializer(jsonRedisSerializer);
//		redisTemplate.setHashValueSerializer(jsonRedisSerializer);
//
//		redisTemplate.afterPropertiesSet();
//		return redisTemplate;
//	}
}
