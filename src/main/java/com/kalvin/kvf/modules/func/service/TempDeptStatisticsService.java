package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.TempDeptStatistics;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2020-08-05 14:10:48
 */
public interface TempDeptStatisticsService extends IService<TempDeptStatistics> {

    /**
     * 获取列表。分页
     * @param tempDeptStatistics 查询参数
     * @return page
     */
    Page<TempDeptStatistics> listTempDeptStatisticsPage(TempDeptStatistics tempDeptStatistics);

}
