package org.fc.redisaccessor.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
public final class Notification {
   private final String content;
}

/**
 * Cache format:
 *
 * Key: notification:id:{notifId}, Value: Notification                         (value)
 * Key: notification:readstatus, Value: {"id": true/false, "id2": true/false}  (hash)
 */
