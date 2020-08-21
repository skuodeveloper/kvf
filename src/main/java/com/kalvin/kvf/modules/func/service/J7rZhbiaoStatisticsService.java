package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.J7rZhbiaoStatistics;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2020-08-18 10:07:13
 */
public interface J7rZhbiaoStatisticsService extends IService<J7rZhbiaoStatistics> {

    /**
     * 获取列表。分页
     * @param j7rZhbiaoStatistics 查询参数
     * @return page
     */
    Page<J7rZhbiaoStatistics> listJ7rZhbiaoStatisticsPage(J7rZhbiaoStatistics j7rZhbiaoStatistics);

}
