package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.cl.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;
import com.cl.annotation.SysLog;

import com.cl.entity.TongzhijiluEntity;
import com.cl.entity.view.TongzhijiluView;

import com.cl.service.TongzhijiluService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

/**
 * 通知记录
 * 后端接口
 * @author
 * @email
 * @date 2025-03-27 15:44:15
 */
@RestController
@RequestMapping("/tongzhijilu")
public class TongzhijiluController {
    @Autowired
    private TongzhijiluService tongzhijiluService;








    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TongzhijiluEntity tongzhijilu,
                                                                                                                                                                                                                                        HttpServletRequest request){
        EntityWrapper<TongzhijiluEntity> ew = new EntityWrapper<TongzhijiluEntity>();


        PageUtils page = tongzhijiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tongzhijilu), params), params));
        return R.ok().put("data", page);
    }







    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,TongzhijiluEntity tongzhijilu,
		HttpServletRequest request){
        EntityWrapper<TongzhijiluEntity> ew = new EntityWrapper<TongzhijiluEntity>();

		PageUtils page = tongzhijiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tongzhijilu), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( TongzhijiluEntity tongzhijilu){
       	EntityWrapper<TongzhijiluEntity> ew = new EntityWrapper<TongzhijiluEntity>();
      	ew.allEq(MPUtil.allEQMapPre( tongzhijilu, "tongzhijilu"));
        return R.ok().put("data", tongzhijiluService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(TongzhijiluEntity tongzhijilu){
        EntityWrapper< TongzhijiluEntity> ew = new EntityWrapper< TongzhijiluEntity>();
 		ew.allEq(MPUtil.allEQMapPre( tongzhijilu, "tongzhijilu"));
		TongzhijiluView tongzhijiluView =  tongzhijiluService.selectView(ew);
		return R.ok("查询通知记录成功").put("data", tongzhijiluView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TongzhijiluEntity tongzhijilu = tongzhijiluService.selectById(id);
		tongzhijilu = tongzhijiluService.selectView(new EntityWrapper<TongzhijiluEntity>().eq("id", id));
        return R.ok().put("data", tongzhijilu);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TongzhijiluEntity tongzhijilu = tongzhijiluService.selectById(id);
		tongzhijilu = tongzhijiluService.selectView(new EntityWrapper<TongzhijiluEntity>().eq("id", id));
        return R.ok().put("data", tongzhijilu);
    }




    /**
     * 后端保存
     */
    @RequestMapping("/save")
    @SysLog("新增通知记录")
    public R save(@RequestBody TongzhijiluEntity tongzhijilu, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(tongzhijilu);
        tongzhijiluService.insert(tongzhijilu);
        return R.ok();
    }

    /**
     * 前端保存
     */
    @SysLog("新增通知记录")
    @RequestMapping("/add")
    public R add(@RequestBody TongzhijiluEntity tongzhijilu, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(tongzhijilu);
        tongzhijiluService.insert(tongzhijilu);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改通知记录")
    public R update(@RequestBody TongzhijiluEntity tongzhijilu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(tongzhijilu);
        tongzhijiluService.updateById(tongzhijilu);//全部更新
        return R.ok();
    }

    /**
     * 批量重试发送失败的通知
     */
    @RequestMapping("/retryBatch")
    @Transactional
    @SysLog("批量重试发送通知")
    public R retryBatch(@RequestBody Long[] ids){
        List<TongzhijiluEntity> list = new ArrayList<TongzhijiluEntity>();
        for(Long id : ids) {
            TongzhijiluEntity tongzhijilu = tongzhijiluService.selectById(id);
            // 重置为待发送状态
            tongzhijilu.setFasongzhuangtai(0);
            tongzhijilu.setShibaiyuanyin(null);
            list.add(tongzhijilu);
        }
        tongzhijiluService.updateBatchById(list);
        return R.ok();
    }




    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除通知记录")
    public R delete(@RequestBody Long[] ids){
        tongzhijiluService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }









    /**
     * 根据发送状态查询
     */
    @RequestMapping("/byStatus/{status}")
    public R byStatus(@PathVariable("status") Integer status){
        List<TongzhijiluEntity> list = tongzhijiluService.selectByFasongzhuangtai(status);
        return R.ok().put("data", list);
    }

    /**
     * 根据预约编号查询
     */
    @RequestMapping("/byYuyue/{yuyuebianhao}")
    public R byYuyue(@PathVariable("yuyuebianhao") String yuyuebianhao){
        List<TongzhijiluEntity> list = tongzhijiluService.selectByYuyuebianhao(yuyuebianhao);
        return R.ok().put("data", list);
    }







}
