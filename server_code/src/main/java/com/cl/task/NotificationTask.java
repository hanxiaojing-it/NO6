package com.cl.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cl.entity.TongzhijiluEntity;
import com.cl.service.TongzhijiluService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class NotificationTask {

    @Autowired
    private TongzhijiluService tongzhijiluService;

    @Scheduled(cron = "0 * * * * ?")
    public void sendScheduledNotifications() {
        Date now = new Date();
        EntityWrapper<TongzhijiluEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("fasongzhuangtai", 0);
        wrapper.le("jihuafasongshijian", now);

        List<TongzhijiluEntity> notifications = tongzhijiluService.selectList(wrapper);
        for (TongzhijiluEntity notification : notifications) {
            tongzhijiluService.sendNotification(notification.getId());
        }
    }

    @Scheduled(cron = "0 */30 * * * ?")
    public void retryFailedNotifications() {
        tongzhijiluService.retryFailedNotifications();
    }
}
