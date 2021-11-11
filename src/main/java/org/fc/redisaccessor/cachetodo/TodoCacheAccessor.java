package org.fc.redisaccessor.cachetodo;

import org.fc.redisaccessor.app.model.Todo;

import java.util.List;
import java.util.Set;

public interface TodoCacheAccessor {
  List<Todo> getAll();
  Todo getById(long id);
  boolean insert(Todo todo);
  Set<Todo> getByCreatorSortedByImportance(long creatorId);
}
