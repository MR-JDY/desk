package com.uni.desk.entity;

import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.uni.desk.base.BaseEntity;
import java.io.Serializable;

import com.uni.desk.converter.CustomStr2DateConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Joe
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ud_report_campaign")
public class ReportCampaign extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 计划名称
     */
    @ExcelProperty("计划")
    private String campaignName;

    /**
     * 计划组名称
     */
    @ExcelProperty("所属计划组")
    private String adgroupName;

    /**
     * 账户名称
     */
    @ExcelProperty("所属账户")
    private String accountName;

    /**
     * 数据日期
     */
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = "日期",converter = CustomStr2DateConverter.class)
    private LocalDateTime date;

    /**
     * 消耗（元）
     */
    @ExcelProperty("消耗")
    private BigDecimal cost;

    /**
     * 成交订单金额（元）
     */
    @ExcelProperty("成交订单金额")
    private BigDecimal transactionAmount;

    /**
     * 展现量
     */
    @ExcelProperty("展现量")
    private Long adPv;

    /**
     * 千次展现成本（元）
     */
    @ExcelProperty("千次展现成本")
    private BigDecimal ecpm;

    /**
     * 点击量
     */
    @ExcelProperty("点击量")
    private Long click;

    /**
     * 点击率（原样保存百分号前面的数值）
     */
    @ExcelProperty("点击率")
    private BigDecimal adCtr;

    /**
     * 点击单价（元）
     */
    @ExcelProperty("点击单价")
    private BigDecimal ecpc;

    /**
     * 转化数
     */
    @ExcelProperty("转化数")
    private Integer convertVolume;

    /**
     * 转化率
     */
    @ExcelProperty("转化率")
    private BigDecimal convertRate;

    /**
     * 转化成本（元）
     */
    @ExcelProperty("转化成本")
    private BigDecimal converCost;

    /**
     * 投资回报率
     */
    @ExcelProperty("投资回报率")
    private BigDecimal returnOnInvestment;

    /**
     * 添加购物车量
     */
    @ExcelProperty("添加购物车量")
    private Integer addCartVolume;

    /**
     * 收藏宝贝量
     */
    @ExcelProperty("收藏宝贝量")
    private Integer favoriteBabyVolume;

    /**
     * 成交订单量
     */
    @ExcelProperty("成交订单量")
    private Integer transactionVolume;

    /**
     * 回访量
     */
    private Integer visitVolume;

    /**
     * 收藏店铺量
     */
    @ExcelProperty("收藏店铺量")
    private String favoriteStores;

    /**
     * 拍下订单量
     */
    @ExcelProperty("拍下订单量")
    private String takeOrderVolume;

    /**
     * 拍下订单金额
     */
    @ExcelProperty("拍下订单金额")
    private String takeOrderAmount;

    /**
     * 页面到达nv
     */
    @ExcelProperty("页面到达UV")
    private String pageArriveUv;

    /**
     * 页面到达量
     */
    @ExcelProperty("页面到达量")
    private String pageArrive;

    /**
     * 完播率
     */
    @ExcelProperty("播完率")
    private BigDecimal finishedRate;

    /**
     * 批次号
     */
    private Long batchNum;
    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifiedDate;

    /**
     * 数据类型，7天,15天,30天等
     * @return
     */
    private String dataType;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
