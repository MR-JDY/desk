package com.uni.desk.service;

import com.uni.desk.entity.DataCreative;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 各条创意的日报表 服务类
 * </p>
 *
 * @author Joe
 * @since 2021-04-30
 */
public interface DataCreativeService extends IService<DataCreative> {

    List<DataCreative> parseStr2DataCreative(String jsonStr);

    void importCreative();
}
