package org.fc.redisaccessor.app.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class Todo implements Serializable {
   private final long id;
   private final long creatorId;
   private final String description;
   private final double importance;
}

/**
 * Cache format:
 *
 * Key: todo:id:{todoId}, Value: Todo                                             (value)
 * Key: todo:bycreator:{creatorId}, Value: {(0, Todo), (1, Todo)}                 (ordered set of (importance, Todo))
 */
