package org.fc.redisaccessor;

import lombok.extern.slf4j.Slf4j;
import org.fc.redisaccessor.app.model.Todo;
import org.fc.redisaccessor.cachetodo.TodoCacheAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class RedisAccessorApplication {
  @Autowired
  private TodoCacheAccessor todoCacheAccessor;
  
  public static void main(String[] args) {
    SpringApplication.run(RedisAccessorApplication.class, args);
  }
  
  @PostConstruct
  public void test() {
    log.info("===========================");
    log.info("Beginning test method");
    log.info("===========================");
    
    // Begin todo test
    todoCacheAccessor.insert(new Todo(1L, 1L, "todo1", 1.2));
    todoCacheAccessor.insert(new Todo(2L, 1L, "todo2", 1.0));
    todoCacheAccessor.insert(new Todo(3L, 1L, "todo3", 1.5));
    
    final List<Todo> todos = todoCacheAccessor.getAll();
    log.info("todos: {}", todos);
    
    final Set<Todo> todosByCreatorSortedByImportance = todoCacheAccessor.getByCreatorSortedByImportance(1);
    log.info("todos by creator sorted: {}", todosByCreatorSortedByImportance);
    
    final Todo todoById = todoCacheAccessor.getById(2);
    log.info("todo 2: {}", todoById);
    
    // Begin notification test
  }
}
