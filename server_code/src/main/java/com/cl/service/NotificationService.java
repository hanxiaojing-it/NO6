package com.cl.service;

import com.cl.entity.TongzhijiluEntity;
import com.cl.entity.YishengyuyueEntity;
import java.util.List;

/**
 * 通知服务接口
 * 用于处理就诊通知的发送、重试等逻辑
 */
public interface NotificationService {

    /**
     * 用户预约成功后，立即创建所有后续通知记录
     * 包括：预约成功通知、就诊前一天提醒、就诊当天提醒
     *
     * @param yuyue 预约信息
     */
    void createNotifications(YishengyuyueEntity yuyue);

    /**
     * 发送单条通知
     *
     * @param tongzhijilu 通知记录
     * @return 是否发送成功
     */
    boolean sendNotification(TongzhijiluEntity tongzhijilu);

    /**
     * 批量发送待发送状态的通知
     */
    void sendPendingNotifications();

    /**
     * 重试发送失败的通知
     */
    void retryFailedNotifications();

    /**
     * 更新用户接收状态
     *
     * @param id 通知记录ID
     * @param status 接收状态
     */
    void updateReceiveStatus(Long id, Integer status);

    /**
     * 获取用户的通知列表
     *
     * @param zhanghao 用户账号
     * @return 通知列表
     */
    List<TongzhijiluEntity> getUserNotifications(String zhanghao);

    /**
     * 标记通知为已读
     *
     * @param id 通知记录ID
     */
    void markAsRead(Long id);

}
