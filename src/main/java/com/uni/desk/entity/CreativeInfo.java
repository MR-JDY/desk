package com.uni.desk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.uni.desk.base.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 创意维度信息
 * </p>
 *
 * @author Joe
 * @since 2021-04-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ud_creative_info")
public class CreativeInfo extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * UD创意ID
     */
    private String udId;

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
     * 临时视频链接（定时失效）
     */
    private String video;

    /**
     * 投放状态
     */
    private String status;

    /**
     * 批次号
     */
    private Integer batchNum;
    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifiedDate;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
