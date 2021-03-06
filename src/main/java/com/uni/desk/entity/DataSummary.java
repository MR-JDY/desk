package com.uni.desk.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.uni.desk.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * unidesk各类数据汇总
 * </p>
 *
 * @author Joe
 * @since 2021-05-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ud_data_summary")
public class DataSummary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 数据汇总的类型：分为计划组、计划、创意，分别用GROUP_PLAN,PLAN,CREATIVE
     */
    private String type;

    /**
     * 消耗（元）
     */
    private BigDecimal cost;

    /**
     * 成交订单金额（元）
     */
    private BigDecimal transactionAmount;

    /**
     * 展现量
     */
    private Long adPv;

    /**
     * 千次展现成本（元）
     */
    private BigDecimal ecpm;

    /**
     * 点击量
     */
    private Long click;

    /**
     * 点击率（原样保存百分号前面的数值）
     */
    private BigDecimal adCtr;

    /**
     * 点击单价（元）
     */
    private BigDecimal ecpc;

    /**
     * 转化数
     */
    private Integer convertVolume;

    /**
     * 转化率
     */
    private BigDecimal convertRate;

    /**
     * 转化成本（元）
     */
    private BigDecimal convertCost;

    /**
     * 投资回报率
     */
    private BigDecimal returnOnInvestment;

    /**
     * 添加购物车量
     */
    private Integer addCartVolume;

    /**
     * 收藏宝贝量
     */
    private Integer favoriteBabyVolume;

    /**
     * 成交订单量
     */
    private Integer transactionVolume;

    /**
     * 回访量
     */
    private Integer visitVolume;

    /**
     * 账号标识
     */
    private String accountCode;

    /**
     * 账号名称
     */
    private String accountName;

    /**
     * 收藏店铺量
     */
    private String favoriteStores;

    /**
     * 拍下订单量
     */
    private String takeOrderVolume;

    /**
     * 拍下订单金额
     */
    private String takeOrderAmount;

    /**
     * 页面到达nv
     */
    private String pageArriveUv;

    /**
     * 页面到达量
     */
    private String pageArrive;

    /**
     * 批次号
     */
    private Long batchNum;



    /**
     * 品牌名
     */
    private String brandName;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
