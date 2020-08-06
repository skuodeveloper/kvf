package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.LastdayDeptStatistics;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2020-08-06 08:29:07
 */
public interface LastdayDeptStatisticsService extends IService<LastdayDeptStatistics> {

    /**
     * 获取列表。分页
     * @param lastdayDeptStatistics 查询参数
     * @return page
     */
    Page<LastdayDeptStatistics> listLastdayDeptStatisticsPage(LastdayDeptStatistics lastdayDeptStatistics);

}
