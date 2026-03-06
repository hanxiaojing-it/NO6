package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.TongzhijiluEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface TongzhijiluService extends IService<TongzhijiluEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<TongzhijiluEntity> selectListView(Wrapper<TongzhijiluEntity> wrapper);
   	
   	TongzhijiluEntity selectView(@Param("ew") Wrapper<TongzhijiluEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<TongzhijiluEntity> wrapper);
   	
    List<Map<String, Object>> selectValue(Map<String, Object> params,Wrapper<TongzhijiluEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,Wrapper<TongzhijiluEntity> wrapper);

    List<Map<String, Object>> selectGroup(Map<String, Object> params,Wrapper<TongzhijiluEntity> wrapper);

    void createNotificationsForAppointment(String yuyuebianhao, String yishengzhanghao, String zhanghao, java.util.Date jiuzhenshijian);

    void sendNotification(Long id);

    void retryFailedNotifications();
}
