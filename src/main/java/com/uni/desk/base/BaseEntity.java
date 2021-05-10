package com.uni.desk.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 继承的基础类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity extends Model {

    //id 主键  暂定主键是UUID 具体生成方案待定 TODO
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id ;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModifiedDate = LocalDateTime.now();
/*
    //创建人
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdDate;
    //最后修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastModifiedBy;
    //最后修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModifiedDate;

    public BaseEntity setCreateInfo(String currentUserId) {
        this.createdBy = currentUserId;
        this.createdDate = LocalDateTime.now();
        setModifiedInfo(currentUserId);
        return this;
    }

    public BaseEntity setModifiedInfo(String currentUserId){
        this.lastModifiedBy = currentUserId;
        this.lastModifiedDate = LocalDateTime.now();
        return this;
    }

    public BaseEntity clearCreateInfo(){
        this.createdBy = null;
        this.createdDate = null;
        return this;
    }
    public BaseEntity clearModifiedInfo(){
        this.lastModifiedBy = null;
        this.lastModifiedDate = null;
        return this;
    }*/

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
