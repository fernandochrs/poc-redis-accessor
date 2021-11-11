package org.fc.redisaccessor.common.accessor.impl;

import org.fc.redisaccessor.common.accessor.RedisOrderedSetAccessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class RedisOrderedSetAccessorImpl<T> implements RedisOrderedSetAccessor<T> {
  private final RedisTemplate<String, T> redisTemplate;
  private final ZSetOperations<String, T> zSetOperations;
  
  public RedisOrderedSetAccessorImpl(@Qualifier ("redisTemplateConfiguration") RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = (RedisTemplate<String, T>) redisTemplate;
    this.zSetOperations = this.redisTemplate.opsForZSet();
  }
  
  @Override
  public void add(String key, T value, double score) {
    this.zSetOperations.add(key, value, score);
  }
  
  @Override
  public void remove(String key, T value) {
    this.zSetOperations.remove(key, value);
  }
  
  @Override
  public Double incrementScore(String key, T value, double score) {
    return this.zSetOperations.incrementScore(key, value, score);
  }
  
  @Override
  public Double score(String key, T value) {
    return this.zSetOperations.score(key, value);
  }
  
  @Override
  public Long rank(String key, T value) {
    return this.zSetOperations.rank(key, value);
  }
  
  @Override
  public Long size(String key) {
    return this.zSetOperations.size(key);
  }
  
  @Override
  public Set<T> range(String key, int start, int end) {
    return this.zSetOperations.range(key, start, end);
  }
}
