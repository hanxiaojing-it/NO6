package com.cl.task;

import com.cl.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 通知定时任务
 * 用于定时发送待发送的通知和重试失败的通知
 */
@Component
public class NotificationTask {

    @Autowired
    private NotificationService notificationService;

    /**
     * 每分钟检查一次待发送的通知
     * 发送计划时间已到的通知
     */
    @Scheduled(cron = "0 * * * * ?")
    public void sendPendingNotifications() {
        try {
            notificationService.sendPendingNotifications();
        } catch (Exception e) {
            System.err.println("发送待通知任务执行失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 每5分钟重试一次发送失败的通知
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void retryFailedNotifications() {
        try {
            notificationService.retryFailedNotifications();
        } catch (Exception e) {
            System.err.println("重试失败通知任务执行失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
