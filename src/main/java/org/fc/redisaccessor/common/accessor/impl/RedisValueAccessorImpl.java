package org.fc.redisaccessor.common.accessor.impl;

import org.fc.redisaccessor.common.accessor.RedisValueAccessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class RedisValueAccessorImpl<T> implements RedisValueAccessor<T> {
  private final ValueOperations<String, T> valueOperations;
  
  public RedisValueAccessorImpl(@Qualifier ("redisTemplateConfiguration") final RedisTemplate<String, Object> redisTemplate) {
    final RedisTemplate<String, T> template = (RedisTemplate<String, T>) redisTemplate;
    this.valueOperations = template.opsForValue();
  }
  
  public T get(String key) {
    return valueOperations.get(key);
  }
  
  @Override
  public List<T> getAll(String key) {
    //    final ScanOptions options = ScanOptions.scanOptions().match("key").count(100).build();
    //    final Cursor c = redisConnection.scan(options);
    //    while (c.hasNext()) {
    //      logger.info(new String((byte[]) c.next()));
    //    }
    return Collections.emptyList();
  }
  
  @Override
  public void set(String key, T obj) {
    valueOperations.set(key, obj);
  }
}
