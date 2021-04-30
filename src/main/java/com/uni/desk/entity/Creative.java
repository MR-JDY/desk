package com.uni.desk.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Creative {

    @ExcelProperty(value = "计划")
    private String plan;
    @ExcelProperty(value = "所属计划组")
    private String groupPlan;
    /*@ExcelProperty(value = "所属账户")
    @ExcelProperty(value = "日期")
    @ExcelProperty(value = "消耗")
    @ExcelProperty(value = "展现量")
    @ExcelProperty(value = "千次展现成本")
    @ExcelProperty(value = "点击量")
    @ExcelProperty(value = "点击率")
    @ExcelProperty(value = "点击单价")
    @ExcelProperty(value = "转化数")
    @ExcelProperty(value = "转化率")
    @ExcelProperty(value = "转化成本")
    @ExcelProperty(value = "成交订单量")
    @ExcelProperty(value = "成交订单金额")
    @ExcelProperty(value = "收藏宝贝量")
    @ExcelProperty(value = "添加购物车量")
    @ExcelProperty(value = "投资回报率")*/


}