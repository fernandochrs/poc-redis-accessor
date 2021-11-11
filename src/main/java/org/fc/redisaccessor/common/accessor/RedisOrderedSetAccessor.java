package org.fc.redisaccessor.common.accessor;

import java.util.Set;

public interface RedisOrderedSetAccessor<T> {
  void add(String key, T value, double score);
  void remove(String key, T value);
  Double incrementScore(String key, T value, double score);
  Double score(String key, T value);
  Long rank(String key, T value);
  Long size(String key);
  Set<T> range(String key, int start, int end);
}
