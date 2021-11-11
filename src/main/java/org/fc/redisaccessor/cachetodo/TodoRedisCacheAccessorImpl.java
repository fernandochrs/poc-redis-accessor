package org.fc.redisaccessor.cachetodo;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.fc.redisaccessor.app.model.Todo;
import org.fc.redisaccessor.common.accessor.RedisAccessor;
import org.fc.redisaccessor.common.accessor.RedisValueAccessor;
import org.fc.redisaccessor.common.accessor.RedisOrderedSetAccessor;
import org.fc.redisaccessor.common.model.CachePrefixConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
public final class TodoRedisCacheAccessorImpl implements TodoCacheAccessor {
  public static final String PREFIX = CachePrefixConstant.TODO;
  
  private final RedisValueAccessor<Todo> todoValueAccessor;
  private final RedisOrderedSetAccessor<Todo> todoOrderedSetAcecssor;
  private final RedisAccessor<List<Object>> trxAccessor;
  
  @Autowired
  public TodoRedisCacheAccessorImpl(final RedisAccessor<List<Object>> trxAccessor,
                                    final RedisValueAccessor<Todo> todoValueAccessor,
                                    final RedisOrderedSetAccessor<Todo> todoOrderedSetAcecssor) {
    this.todoValueAccessor = todoValueAccessor;
    this.todoOrderedSetAcecssor = todoOrderedSetAcecssor;
    this.trxAccessor = trxAccessor;
  }
  
  @Override
  public List<Todo> getAll() {
    return todoValueAccessor.getAll(String.format("%s:%s:", PREFIX, "id"));
  }
  
  @Override
  public Todo getById(long id) {
    return todoValueAccessor.get(String.format("%s:%s:%d", PREFIX, "id", id));
  }
  
  @Override
  public boolean insert(Todo todo) {
    final CompletableFuture<Boolean> trxCallback = new CompletableFuture<>();
    trxAccessor.exec(new InsertNewTodoRedisTransaction(todo, trxCallback));
    return trxCallback.join();
  }
  
  @Override
  public Set<Todo> getByCreatorSortedByImportance(long creatorId) {
    return todoOrderedSetAcecssor.range(String.format("%s:%s:%d", PREFIX, "bycreator", creatorId), 0, -1);
  }
  
  // For operations requiring transaction, create an static inner class with class name = {title of transaction}RedisTransaction
  @Slf4j
  static class InsertNewTodoRedisTransaction implements SessionCallback<List<Object>> {
    private final Todo todo;
    private final CompletableFuture<Boolean> callback;
  
    public InsertNewTodoRedisTransaction(Todo todo, CompletableFuture<Boolean> callback) {
      this.todo = todo;
      this.callback = callback;
    }
  
    @Override
    public <K, V> List<Object> execute(RedisOperations<K, V> operations) throws DataAccessException {
      try {
        final RedisTemplate<String, Todo> redisTemplate = (RedisTemplate<String, Todo>) operations;
        operations.multi();
        redisTemplate.opsForZSet().add(String.format("%s:%s:%d", PREFIX, "bycreator", todo.getCreatorId()), todo, todo.getImportance());
        redisTemplate.opsForValue().set(String.format("%s:%s:%d", PREFIX, "id", todo.getId()), todo);
        final List<Object> execResult = operations.exec();
        callback.complete(true);
        return execResult;
      } catch (Exception e) {
        // print exc
        log.error("execute trx error for spec {}", todo, e);
        operations.discard();
      }
      callback.complete(false);
      return Collections.emptyList();
    }
  }
}
