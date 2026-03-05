package com.cl.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 通知记录
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2025-03-27 15:44:15
 */
@TableName("tongzhijilu")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class TongzhijiluEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public TongzhijiluEntity() {

	}

	public TongzhijiluEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 预约编号
	 */

	private String yuyuebianhao;

	/**
	 * 医生账号
	 */

	private String yishengzhanghao;

	/**
	 * 用户账号
	 */

	private String zhanghao;

	/**
	 * 通知类型（1-预约成功通知 2-就诊前一天提醒 3-就诊当天提醒）
	 */

	private Integer tongzhileixing;

	/**
	 * 通知内容
	 */

	private String tongzhineirong;

	/**
	 * 发送时间
	 */

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date fasongshijian;

	/**
	 * 发送状态（0-待发送 1-发送成功 2-发送失败）
	 */

	private Integer fasongzhuangtai;

	/**
	 * 接收状态（0-未接收 1-已接收 2-已读）
	 */

	private Integer jieshouzhuangtai;

	/**
	 * 失败原因
	 */

	private String shibaiyuanyin;

	/**
	 * 重试次数
	 */

	private Integer chongshicishu;

	/**
	 * 就诊时间
	 */

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date jiuzhenshijian;

	/**
	 * 计划发送时间
	 */

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date jihuafasongshijian;


	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 设置：预约编号
	 */
	public void setYuyuebianhao(String yuyuebianhao) {
		this.yuyuebianhao = yuyuebianhao;
	}
	/**
	 * 获取：预约编号
	 */
	public String getYuyuebianhao() {
		return yuyuebianhao;
	}
	/**
	 * 设置：医生账号
	 */
	public void setYishengzhanghao(String yishengzhanghao) {
		this.yishengzhanghao = yishengzhanghao;
	}
	/**
	 * 获取：医生账号
	 */
	public String getYishengzhanghao() {
		return yishengzhanghao;
	}
	/**
	 * 设置：用户账号
	 */
	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}
	/**
	 * 获取：用户账号
	 */
	public String getZhanghao() {
		return zhanghao;
	}
	/**
	 * 设置：通知类型
	 */
	public void setTongzhileixing(Integer tongzhileixing) {
		this.tongzhileixing = tongzhileixing;
	}
	/**
	 * 获取：通知类型
	 */
	public Integer getTongzhileixing() {
		return tongzhileixing;
	}
	/**
	 * 设置：通知内容
	 */
	public void setTongzhineirong(String tongzhineirong) {
		this.tongzhineirong = tongzhineirong;
	}
	/**
	 * 获取：通知内容
	 */
	public String getTongzhineirong() {
		return tongzhineirong;
	}
	/**
	 * 设置：发送时间
	 */
	public void setFasongshijian(Date fasongshijian) {
		this.fasongshijian = fasongshijian;
	}
	/**
	 * 获取：发送时间
	 */
	public Date getFasongshijian() {
		return fasongshijian;
	}
	/**
	 * 设置：发送状态
	 */
	public void setFasongzhuangtai(Integer fasongzhuangtai) {
		this.fasongzhuangtai = fasongzhuangtai;
	}
	/**
	 * 获取：发送状态
	 */
	public Integer getFasongzhuangtai() {
		return fasongzhuangtai;
	}
	/**
	 * 设置：接收状态
	 */
	public void setJieshouzhuangtai(Integer jieshouzhuangtai) {
		this.jieshouzhuangtai = jieshouzhuangtai;
	}
	/**
	 * 获取：接收状态
	 */
	public Integer getJieshouzhuangtai() {
		return jieshouzhuangtai;
	}
	/**
	 * 设置：失败原因
	 */
	public void setShibaiyuanyin(String shibaiyuanyin) {
		this.shibaiyuanyin = shibaiyuanyin;
	}
	/**
	 * 获取：失败原因
	 */
	public String getShibaiyuanyin() {
		return shibaiyuanyin;
	}
	/**
	 * 设置：重试次数
	 */
	public void setChongshicishu(Integer chongshicishu) {
		this.chongshicishu = chongshicishu;
	}
	/**
	 * 获取：重试次数
	 */
	public Integer getChongshicishu() {
		return chongshicishu;
	}
	/**
	 * 设置：就诊时间
	 */
	public void setJiuzhenshijian(Date jiuzhenshijian) {
		this.jiuzhenshijian = jiuzhenshijian;
	}
	/**
	 * 获取：就诊时间
	 */
	public Date getJiuzhenshijian() {
		return jiuzhenshijian;
	}
	/**
	 * 设置：计划发送时间
	 */
	public void setJihuafasongshijian(Date jihuafasongshijian) {
		this.jihuafasongshijian = jihuafasongshijian;
	}
	/**
	 * 获取：计划发送时间
	 */
	public Date getJihuafasongshijian() {
		return jihuafasongshijian;
	}

}
