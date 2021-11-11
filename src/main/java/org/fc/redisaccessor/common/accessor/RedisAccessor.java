package org.fc.redisaccessor.common.accessor;

import org.springframework.data.redis.core.SessionCallback;

public interface RedisAccessor<T> {
  void delete(String key);
  void exec(SessionCallback<T> sessionCallback);
}
