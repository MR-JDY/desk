package com.uni.desk.service;

import com.uni.desk.entity.JsonCreative;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Joe
 * @since 2021-05-07
 */
public interface JsonCreativeService extends IService<JsonCreative> {

    void importJsonCreative();
}
