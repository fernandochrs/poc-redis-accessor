package org.fc.redisaccessor.cachenotification;

import org.fc.redisaccessor.app.model.Notification;

import java.util.Map;

interface NotificationCacheAccessor {
  Notification getNotification(String notificationId);
  boolean getReadStatus(String notificationId);
  Map<String, Boolean> getAllNotificationReadStatuses();
  void deleteNotification(String notificationId);
}
