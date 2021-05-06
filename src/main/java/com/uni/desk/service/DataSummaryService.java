package com.uni.desk.service;

import com.uni.desk.entity.DataSummary;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * unidesk各类数据汇总 服务类
 * </p>
 *
 * @author Joe
 * @since 2021-05-06
 */
public interface DataSummaryService extends IService<DataSummary> {

    void importSummary();
}
