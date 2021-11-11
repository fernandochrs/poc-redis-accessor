package org.fc.redisaccessor.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
  @Autowired
  private RedisConfig redisConfig;
  
  @Bean (name = "redisClusterConnectionFactory")
  public RedisConnectionFactory connectionFactory() {
    final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
    jedisPoolConfig.setMinIdle(redisConfig.getMinIdle());
    jedisPoolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
    jedisPoolConfig.setMaxTotal(redisConfig.getMaxTotal());
  
    JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
            .usePooling()
            .poolConfig(jedisPoolConfig)
            .build();
    final JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(new RedisStandaloneConfiguration(redisConfig.getHostname(), redisConfig.getPort()));
    return jedisConnectionFactory;
  }
  
  @Bean("redisTemplateConfiguration")
  public RedisTemplate<String, Object> redisTemplate(
          @Qualifier ("redisClusterConnectionFactory") RedisConnectionFactory factory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    JdkSerializationRedisSerializer jdkSeri = new JdkSerializationRedisSerializer();
    
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setDefaultSerializer(jdkSeri);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(jdkSeri);
    redisTemplate.setHashValueSerializer(jdkSeri);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
  
  @Bean
  @ConfigurationProperties ("redis.config")
  public RedisConfig redisConfig() {
    return new RedisConfig();
  }
}
