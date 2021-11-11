package org.fc.redisaccessor.common.accessor;

import java.util.List;

public interface RedisValueAccessor<T>{
  T get(String key);
  List<T> getAll(String key);
  void set(String key, T obj);
}
