package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.PersonTypeStatistic;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2020-08-12 16:31:03
 */
public interface PersonTypeStatisticService extends IService<PersonTypeStatistic> {

    /**
     * 获取列表。分页
     * @param personTypeStatistic 查询参数
     * @return page
     */
    Page<PersonTypeStatistic> listPersonTypeStatisticPage(PersonTypeStatistic personTypeStatistic);

}
