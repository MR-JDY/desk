package com.uni.desk.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.uni.desk.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Joe
 * @since 2021-05-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ud_json_creative")
public class JsonCreative extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 消耗（单位：分）
     */
    private BigDecimal cost;

    /**
     * 成交订单金额（单位：分）
     */
    private Long transactionAmount;

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
    private Long ecpc;

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
    private Long convertCost;

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
    private Long takeOrderAmount;

    /**
     * 页面到达nv
     */
    private String pageArriveUv;

    /**
     * 页面到达量
     */
    private String pageArrive;

    /**
     * 媒体创意ID
     */
    private String directCreativeId;

    /**
     * 创意名称


     */
    private String creativeName;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 计划组ID
     */
    private Integer adgroupId;

    /**
     * 计划组名称
     */
    private String adgroupName;

    /**
     * 计划ID
     */
    private Integer campaignId;

    /**
     * 计划名称
     */
    private String campaignName;

    /**
     * 视频ID
     */
    private String videoId;

    /**
     * 投放状态
     */
    private String status;

    /**
     * 批次号
     */
    private Long batchNum;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
