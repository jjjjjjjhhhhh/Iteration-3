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
 * 家庭成员
 * 数据库通用操作实体类（普通增删改查）
 */
@TableName("jiatingchengyuan")
public class JiatingchengyuanEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public JiatingchengyuanEntity() {
    }
    
    public JiatingchengyuanEntity(T t) {
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
     * 用户账号
     */
    private String yonghuzhanghao;
    
    /**
     * 用户姓名
     */
    private String yonghuxingming;
    
    /**
     * 家庭成员账号
     */
    private String chengyuanzhanghao;
    
    /**
     * 家庭成员姓名
     */
    private String chengyuanxingming;
    
    /**
     * 关系
     */
    private String relationship;
    
    /**
     * 备注
     */
    private String remark;

    private String shoujihaoma;
    
    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date addtime;

    public String getShoujihaoma() {
        return shoujihaoma;
    }
    public void setShoujihaoma(String shoujihaoma) {
        this.shoujihaoma = shoujihaoma;
    }

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
    
    public String getYonghuzhanghao() {
        return yonghuzhanghao;
    }
    public void setYonghuzhanghao(String yonghuzhanghao) {
        this.yonghuzhanghao = yonghuzhanghao;
    }
    
    public String getYonghuxingming() {
        return yonghuxingming;
    }
    public void setYonghuxingming(String yonghuxingming) {
        this.yonghuxingming = yonghuxingming;
    }
    
    public String getChengyuanzhanghao() {
        return chengyuanzhanghao;
    }
    public void setChengyuanzhanghao(String chengyuanzhanghao) {
        this.chengyuanzhanghao = chengyuanzhanghao;
    }
    
    public String getChengyuanxingming() {
        return chengyuanxingming;
    }
    public void setChengyuanxingming(String chengyuanxingming) {
        this.chengyuanxingming = chengyuanxingming;
    }
    
    public String getRelationship() {
        return relationship;
    }
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
} 