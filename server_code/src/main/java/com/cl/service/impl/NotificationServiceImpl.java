package com.cl.service.impl;

import com.cl.entity.TongzhijiluEntity;
import com.cl.entity.YishengyuyueEntity;
import com.cl.service.NotificationService;
import com.cl.service.TongzhijiluService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 通知服务实现类
 */
@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private TongzhijiluService tongzhijiluService;

    // 通知类型常量
    public static final int TYPE_APPOINTMENT_SUCCESS = 1;  // 预约成功通知
    public static final int TYPE_ONE_DAY_BEFORE = 2;       // 就诊前一天提醒
    public static final int TYPE_ON_DAY = 3;               // 就诊当天提醒

    // 发送状态常量
    public static final int STATUS_PENDING = 0;    // 待发送
    public static final int STATUS_SUCCESS = 1;    // 发送成功
    public static final int STATUS_FAILED = 2;     // 发送失败

    // 接收状态常量
    public static final int RECEIVE_UNRECEIVED = 0;  // 未接收
    public static final int RECEIVE_RECEIVED = 1;    // 已接收
    public static final int RECEIVE_READ = 2;        // 已读

    // 最大重试次数
    public static final int MAX_RETRY_COUNT = 3;

    @Override
    @Transactional
    public void createNotifications(YishengyuyueEntity yuyue) {
        if (yuyue == null || yuyue.getYuyueshijian() == null) {
            return;
        }

        Date yuyueTime = yuyue.getYuyueshijian();
        String yuyuebianhao = yuyue.getYuyuebianhao();
        String yishengzhanghao = yuyue.getYishengzhanghao();
        String zhanghao = yuyue.getZhanghao();

        Date now = new Date();

        // 1. 创建预约成功通知 - 立即发送
        TongzhijiluEntity successNotification = new TongzhijiluEntity();
        successNotification.setYuyuebianhao(yuyuebianhao);
        successNotification.setYishengzhanghao(yishengzhanghao);
        successNotification.setZhanghao(zhanghao);
        successNotification.setTongzhileixing(TYPE_APPOINTMENT_SUCCESS);
        successNotification.setTongzhineirong(String.format(
            "您的预约已成功！预约编号：%s，医生账号：%s，就诊时间：%s",
            yuyuebianhao,
            yishengzhanghao,
            formatDate(yuyueTime)
        ));
        successNotification.setJihuafasongshijian(now);
        successNotification.setJiuzhenshijian(yuyueTime);
        successNotification.setFasongzhuangtai(STATUS_PENDING);
        successNotification.setJieshouzhuangtai(RECEIVE_UNRECEIVED);
        successNotification.setChongshicishu(0);
        successNotification.setAddtime(now);

        tongzhijiluService.insert(successNotification);

        // 立即发送预约成功通知
        sendNotification(successNotification);

        // 2. 创建就诊前一天提醒
        Calendar calOneDayBefore = Calendar.getInstance();
        calOneDayBefore.setTime(yuyueTime);
        calOneDayBefore.add(Calendar.DAY_OF_MONTH, -1);
        calOneDayBefore.set(Calendar.HOUR_OF_DAY, 9);  // 上午9点发送
        calOneDayBefore.set(Calendar.MINUTE, 0);
        calOneDayBefore.set(Calendar.SECOND, 0);

        // 如果计算出的时间早于当前时间，则设置为当前时间
        Date oneDayBefore = calOneDayBefore.getTime();
        if (oneDayBefore.before(now)) {
            oneDayBefore = now;
        }

        TongzhijiluEntity oneDayNotification = new TongzhijiluEntity();
        oneDayNotification.setYuyuebianhao(yuyuebianhao);
        oneDayNotification.setYishengzhanghao(yishengzhanghao);
        oneDayNotification.setZhanghao(zhanghao);
        oneDayNotification.setTongzhileixing(TYPE_ONE_DAY_BEFORE);
        oneDayNotification.setTongzhineirong(String.format(
            "温馨提醒：您明天（%s）有就诊预约，请准时前往。预约编号：%s，医生账号：%s",
            formatDate(yuyueTime),
            yuyuebianhao,
            yishengzhanghao
        ));
        oneDayNotification.setJihuafasongshijian(oneDayBefore);
        oneDayNotification.setJiuzhenshijian(yuyueTime);
        oneDayNotification.setFasongzhuangtai(STATUS_PENDING);
        oneDayNotification.setJieshouzhuangtai(RECEIVE_UNRECEIVED);
        oneDayNotification.setChongshicishu(0);
        oneDayNotification.setAddtime(now);

        tongzhijiluService.insert(oneDayNotification);

        // 如果就诊前一天就是今天或已经过去，立即发送
        if (isSameDay(oneDayBefore, now) || oneDayBefore.before(now)) {
            sendNotification(oneDayNotification);
        }

        // 3. 创建就诊当天提醒
        Calendar calOnDay = Calendar.getInstance();
        calOnDay.setTime(yuyueTime);
        calOnDay.set(Calendar.HOUR_OF_DAY, 8);  // 上午8点发送
        calOnDay.set(Calendar.MINUTE, 0);
        calOnDay.set(Calendar.SECOND, 0);

        Date onDay = calOnDay.getTime();
        if (onDay.before(now)) {
            onDay = now;
        }

        TongzhijiluEntity onDayNotification = new TongzhijiluEntity();
        onDayNotification.setYuyuebianhao(yuyuebianhao);
        onDayNotification.setYishengzhanghao(yishengzhanghao);
        onDayNotification.setZhanghao(zhanghao);
        onDayNotification.setTongzhileixing(TYPE_ON_DAY);
        onDayNotification.setTongzhineirong(String.format(
            "就诊提醒：您今天（%s）有就诊预约，请准时前往。预约编号：%s，医生账号：%s",
            formatDate(yuyueTime),
            yuyuebianhao,
            yishengzhanghao
        ));
        onDayNotification.setJihuafasongshijian(onDay);
        onDayNotification.setJiuzhenshijian(yuyueTime);
        onDayNotification.setFasongzhuangtai(STATUS_PENDING);
        onDayNotification.setJieshouzhuangtai(RECEIVE_UNRECEIVED);
        onDayNotification.setChongshicishu(0);
        onDayNotification.setAddtime(now);

        tongzhijiluService.insert(onDayNotification);

        // 如果就诊时间就是今天，立即发送
        if (isSameDay(onDay, now)) {
            sendNotification(onDayNotification);
        }
    }

    @Override
    public boolean sendNotification(TongzhijiluEntity tongzhijilu) {
        if (tongzhijilu == null) {
            return false;
        }

        try {
            // 模拟发送通知的逻辑
            // 实际项目中这里可能是：
            // 1. 发送短信
            // 2. 发送邮件
            // 3. 推送APP消息
            // 4. WebSocket推送

            boolean sendSuccess = doSend(tongzhijilu);

            if (sendSuccess) {
                tongzhijilu.setFasongzhuangtai(STATUS_SUCCESS);
                tongzhijilu.setFasongshijian(new Date());
                tongzhijilu.setShibaiyuanyin(null);
            } else {
                tongzhijilu.setFasongzhuangtai(STATUS_FAILED);
                tongzhijilu.setShibaiyuanyin("发送失败：网络异常");
            }

            tongzhijiluService.updateById(tongzhijilu);
            return sendSuccess;

        } catch (Exception e) {
            tongzhijilu.setFasongzhuangtai(STATUS_FAILED);
            tongzhijilu.setShibaiyuanyin("发送异常：" + e.getMessage());
            tongzhijiluService.updateById(tongzhijilu);
            return false;
        }
    }

    /**
     * 实际发送通知的方法
     * 这里模拟发送，实际项目中需要接入真实的短信/邮件/推送服务
     */
    private boolean doSend(TongzhijiluEntity tongzhijilu) {
        // 模拟发送成功率95%
        double random = Math.random();
        return random > 0.05;
    }

    @Override
    public void sendPendingNotifications() {
        // 查询所有待发送且计划发送时间已到的通知
        List<TongzhijiluEntity> pendingList = tongzhijiluService.selectByFasongzhuangtai(STATUS_PENDING);
        Date now = new Date();

        for (TongzhijiluEntity notification : pendingList) {
            // 检查是否到达计划发送时间
            if (notification.getJihuafasongshijian() != null &&
                notification.getJihuafasongshijian().before(now)) {
                sendNotification(notification);
            }
        }
    }

    @Override
    public void retryFailedNotifications() {
        // 查询所有发送失败且重试次数未超过最大值的记录
        List<TongzhijiluEntity> failedList = tongzhijiluService.selectByFasongzhuangtai(STATUS_FAILED);

        for (TongzhijiluEntity notification : failedList) {
            if (notification.getChongshicishu() < MAX_RETRY_COUNT) {
                // 增加重试次数
                notification.setChongshicishu(notification.getChongshicishu() + 1);
                // 重置为待发送状态
                notification.setFasongzhuangtai(STATUS_PENDING);
                tongzhijiluService.updateById(notification);

                // 立即重试发送
                sendNotification(notification);
            }
        }
    }

    @Override
    public void updateReceiveStatus(Long id, Integer status) {
        TongzhijiluEntity notification = tongzhijiluService.selectById(id);
        if (notification != null) {
            notification.setJieshouzhuangtai(status);
            tongzhijiluService.updateById(notification);
        }
    }

    @Override
    public List<TongzhijiluEntity> getUserNotifications(String zhanghao) {
        // 这里简化处理，实际应该通过Wrapper查询
        return null;
    }

    @Override
    public void markAsRead(Long id) {
        updateReceiveStatus(id, RECEIVE_READ);
    }

    /**
     * 格式化日期
     */
    private String formatDate(Date date) {
        if (date == null) return "";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    /**
     * 判断两个日期是否为同一天
     */
    private boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
