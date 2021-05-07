package com.uni.desk.service;

import com.uni.desk.entity.Material;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Joe
 * @since 2021-05-07
 */
public interface MaterialService extends IService<Material> {

    void importMaterial();
}
