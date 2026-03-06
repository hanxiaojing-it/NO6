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

@TableName("tongzhijilu")
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
	
	@TableId(type = IdType.AUTO)
	private Long id;
	
	private String yuyuebianhao;
	
	private String yishengzhanghao;
	
	private String zhanghao;
	
	private Integer tongzhileixing;
	
	private String tongzhineirong;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 		
	private Date fasongshijian;
	
	private Integer fasongzhuangtai;
	
	private Integer jieshouzhuangtai;
	
	private String shibaiyuanyin;
	
	private Integer chongshicishu;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 		
	private Date jiuzhenshijian;
	
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

	public String getYuyuebianhao() {
		return yuyuebianhao;
	}

	public void setYuyuebianhao(String yuyuebianhao) {
		this.yuyuebianhao = yuyuebianhao;
	}

	public String getYishengzhanghao() {
		return yishengzhanghao;
	}

	public void setYishengzhanghao(String yishengzhanghao) {
		this.yishengzhanghao = yishengzhanghao;
	}

	public String getZhanghao() {
		return zhanghao;
	}

	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}

	public Integer getTongzhileixing() {
		return tongzhileixing;
	}

	public void setTongzhileixing(Integer tongzhileixing) {
		this.tongzhileixing = tongzhileixing;
	}

	public String getTongzhineirong() {
		return tongzhineirong;
	}

	public void setTongzhineirong(String tongzhineirong) {
		this.tongzhineirong = tongzhineirong;
	}

	public Date getFasongshijian() {
		return fasongshijian;
	}

	public void setFasongshijian(Date fasongshijian) {
		this.fasongshijian = fasongshijian;
	}

	public Integer getFasongzhuangtai() {
		return fasongzhuangtai;
	}

	public void setFasongzhuangtai(Integer fasongzhuangtai) {
		this.fasongzhuangtai = fasongzhuangtai;
	}

	public Integer getJieshouzhuangtai() {
		return jieshouzhuangtai;
	}

	public void setJieshouzhuangtai(Integer jieshouzhuangtai) {
		this.jieshouzhuangtai = jieshouzhuangtai;
	}

	public String getShibaiyuanyin() {
		return shibaiyuanyin;
	}

	public void setShibaiyuanyin(String shibaiyuanyin) {
		this.shibaiyuanyin = shibaiyuanyin;
	}

	public Integer getChongshicishu() {
		return chongshicishu;
	}

	public void setChongshicishu(Integer chongshicishu) {
		this.chongshicishu = chongshicishu;
	}

	public Date getJiuzhenshijian() {
		return jiuzhenshijian;
	}

	public void setJiuzhenshijian(Date jiuzhenshijian) {
		this.jiuzhenshijian = jiuzhenshijian;
	}

	public Date getJihuafasongshijian() {
		return jihuafasongshijian;
	}

	public void setJihuafasongshijian(Date jihuafasongshijian) {
		this.jihuafasongshijian = jihuafasongshijian;
	}
}
