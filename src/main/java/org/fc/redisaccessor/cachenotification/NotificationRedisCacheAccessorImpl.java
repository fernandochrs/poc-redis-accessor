package org.fc.redisaccessor.cachenotification;

import org.fc.redisaccessor.app.model.Notification;
import org.fc.redisaccessor.common.model.CachePrefixConstant;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class NotificationRedisCacheAccessorImpl implements NotificationCacheAccessor {
  public static final String PREFIX = CachePrefixConstant.NOTIFICATION;
  
  @Override
  public Notification getNotification(String notificationId) {
    return null;
  }
  
  @Override
  public boolean getReadStatus(String notificationId) {
    return false;
  }
  
  @Override
  public Map<String, Boolean> getAllNotificationReadStatuses() {
    return null;
  }
  
  @Override
  public void deleteNotification(String notificationId) {
  
  }
}
