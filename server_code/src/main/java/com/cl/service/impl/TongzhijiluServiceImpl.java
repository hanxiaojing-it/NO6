package com.cl.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.UUID;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cl.utils.PageUtils;
import com.cl.utils.Query;
import com.cl.dao.TongzhijiluDao;
import com.cl.entity.TongzhijiluEntity;
import com.cl.entity.JiuzhentongzhiEntity;
import com.cl.service.TongzhijiluService;
import com.cl.service.JiuzhentongzhiService;

@Service("tongzhijiluService")
public class TongzhijiluServiceImpl extends ServiceImpl<TongzhijiluDao, TongzhijiluEntity> implements TongzhijiluService {
	
	@Autowired
	private JiuzhentongzhiService jiuzhentongzhiService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TongzhijiluEntity> page = this.selectPage(
                new Query<TongzhijiluEntity>(params).getPage(),
                new EntityWrapper<TongzhijiluEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<TongzhijiluEntity> wrapper) {
		  Page<TongzhijiluEntity> page =new Query<TongzhijiluEntity>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<TongzhijiluEntity> selectListView(Wrapper<TongzhijiluEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public TongzhijiluEntity selectView(Wrapper<TongzhijiluEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

    @Override
    public List<Map<String, Object>> selectValue(Map<String, Object> params, Wrapper<TongzhijiluEntity> wrapper) {
        return baseMapper.selectValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, Wrapper<TongzhijiluEntity> wrapper) {
        return baseMapper.selectTimeStatValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectGroup(Map<String, Object> params, Wrapper<TongzhijiluEntity> wrapper) {
        return baseMapper.selectGroup(params, wrapper);
    }

    @Override
    public void createNotificationsForAppointment(String yuyuebianhao, String yishengzhanghao, String zhanghao, Date jiuzhenshijian) {
        Date now = new Date();

        TongzhijiluEntity successNotification = new TongzhijiluEntity();
        successNotification.setYuyuebianhao(yuyuebianhao);
        successNotification.setYishengzhanghao(yishengzhanghao);
        successNotification.setZhanghao(zhanghao);
        successNotification.setTongzhileixing(1);
        successNotification.setTongzhineirong("预约成功！您的预约编号是：" + yuyuebianhao + "，就诊时间：" + formatDate(jiuzhenshijian));
        successNotification.setFasongzhuangtai(0);
        successNotification.setJieshouzhuangtai(0);
        successNotification.setChongshicishu(0);
        successNotification.setJiuzhenshijian(jiuzhenshijian);
        successNotification.setJihuafasongshijian(now);
        successNotification.setAddtime(now);
        this.insert(successNotification);
        
        sendNotification(successNotification.getId());
        
        createJiuzhentongzhi(yuyuebianhao, yishengzhanghao, zhanghao, jiuzhenshijian, now, "预约成功通知：您的预约已审核通过");

        Calendar dayBeforeCal = Calendar.getInstance();
        dayBeforeCal.setTime(jiuzhenshijian);
        dayBeforeCal.add(Calendar.DAY_OF_MONTH, -1);
        dayBeforeCal.set(Calendar.HOUR_OF_DAY, 9);
        dayBeforeCal.set(Calendar.MINUTE, 0);
        dayBeforeCal.set(Calendar.SECOND, 0);

        TongzhijiluEntity dayBeforeNotification = new TongzhijiluEntity();
        dayBeforeNotification.setYuyuebianhao(yuyuebianhao);
        dayBeforeNotification.setYishengzhanghao(yishengzhanghao);
        dayBeforeNotification.setZhanghao(zhanghao);
        dayBeforeNotification.setTongzhileixing(2);
        dayBeforeNotification.setTongzhineirong("就诊提醒！您明天有预约就诊，预约编号：" + yuyuebianhao + "，就诊时间：" + formatDate(jiuzhenshijian));
        dayBeforeNotification.setFasongzhuangtai(0);
        dayBeforeNotification.setJieshouzhuangtai(0);
        dayBeforeNotification.setChongshicishu(0);
        dayBeforeNotification.setJiuzhenshijian(jiuzhenshijian);
        dayBeforeNotification.setJihuafasongshijian(dayBeforeCal.getTime());
        dayBeforeNotification.setAddtime(now);
        this.insert(dayBeforeNotification);

        Calendar sameDayCal = Calendar.getInstance();
        sameDayCal.setTime(jiuzhenshijian);
        sameDayCal.add(Calendar.HOUR_OF_DAY, -2);

        TongzhijiluEntity sameDayNotification = new TongzhijiluEntity();
        sameDayNotification.setYuyuebianhao(yuyuebianhao);
        sameDayNotification.setYishengzhanghao(yishengzhanghao);
        sameDayNotification.setZhanghao(zhanghao);
        sameDayNotification.setTongzhileixing(3);
        sameDayNotification.setTongzhineirong("就诊提醒！您的预约就诊即将开始，预约编号：" + yuyuebianhao + "，就诊时间：" + formatDate(jiuzhenshijian));
        sameDayNotification.setFasongzhuangtai(0);
        sameDayNotification.setJieshouzhuangtai(0);
        sameDayNotification.setChongshicishu(0);
        sameDayNotification.setJiuzhenshijian(jiuzhenshijian);
        sameDayNotification.setJihuafasongshijian(sameDayCal.getTime());
        sameDayNotification.setAddtime(now);
        this.insert(sameDayNotification);
    }
    
    private void createJiuzhentongzhi(String yuyuebianhao, String yishengzhanghao, String zhanghao, Date jiuzhenshijian, Date tongzhishijian, String tongzhibeizhu) {
        JiuzhentongzhiEntity jiuzhentongzhi = new JiuzhentongzhiEntity();
        jiuzhentongzhi.setTongzhibianhao("TZ" + System.currentTimeMillis());
        jiuzhentongzhi.setYishengzhanghao(yishengzhanghao);
        jiuzhentongzhi.setZhanghao(zhanghao);
        jiuzhentongzhi.setJiuzhenshijian(jiuzhenshijian);
        jiuzhentongzhi.setTongzhishijian(tongzhishijian);
        jiuzhentongzhi.setTongzhibeizhu(tongzhibeizhu);
        jiuzhentongzhi.setAddtime(new Date());
        jiuzhentongzhiService.insert(jiuzhentongzhi);
    }

    @Override
    public void sendNotification(Long id) {
        TongzhijiluEntity notification = this.selectById(id);
        if (notification == null || notification.getFasongzhuangtai() == 1) {
            return;
        }

        try {
            notification.setFasongshijian(new Date());
            notification.setFasongzhuangtai(1);
            notification.setJieshouzhuangtai(1);
            this.updateById(notification);
        } catch (Exception e) {
            notification.setFasongzhuangtai(2);
            notification.setShibaiyuanyin(e.getMessage());
            notification.setChongshicishu(notification.getChongshicishu() + 1);
            this.updateById(notification);
        }
    }

    @Override
    public void retryFailedNotifications() {
        EntityWrapper<TongzhijiluEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("fasongzhuangtai", 2);
        wrapper.lt("chongshicishu", 5);

        List<TongzhijiluEntity> failedNotifications = this.selectList(wrapper);
        for (TongzhijiluEntity notification : failedNotifications) {
            sendNotification(notification.getId());
        }
    }

    private String formatDate(Date date) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
