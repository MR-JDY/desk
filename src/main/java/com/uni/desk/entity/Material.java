package com.uni.desk.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.uni.desk.base.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("ud_material")
public class Material extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 作者ID
     */
    private String authorId;

    /**
     * 视频素材名称
     */
    private String name;

    /**
     * 组ID
     */
    private Integer groupId;

    /**
     * 视频ID
     */
    private String videoId;

    /**
     * UID
     */
    private String uid;

    /**
     * 素材ID
     */
    private String materialId;

    /**
     * 所属
     */
    private String belongs;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 商品
     */
    private String item;

    /**
     * 编导
     */
    private String choreographer;

    /**
     * 剪辑
     */
    private String editor;

    /**
     * 批次号
     */
    private Long batchNum;


    /**
     * 拉去的json中的属性，包含videoId跟materialId
     */
    @TableField(exist = false)
    private String extra;

    /**
     * 品牌名
     */
    private String brandName;
    @Override
    protected Serializable pkVal() {
        return null;
    }

}
