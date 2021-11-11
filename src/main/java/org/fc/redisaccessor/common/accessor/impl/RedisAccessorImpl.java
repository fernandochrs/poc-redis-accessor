package org.fc.redisaccessor.common.accessor.impl;

import org.fc.redisaccessor.common.accessor.RedisAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class RedisAccessorImpl<T> implements RedisAccessor<T> {
  private final RedisTemplate<String, T> redisTemplate;
  
  @Autowired
  public RedisAccessorImpl(@Qualifier ("redisTemplateConfiguration") final RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = (RedisTemplate<String, T>) redisTemplate;
  }
  
  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }
  
  @Override
  public void exec(SessionCallback<T> sessionCallback) {
    redisTemplate.execute(sessionCallback);
  }
}
