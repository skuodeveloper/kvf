package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.LastdayMemberStatistics;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2020-08-06 13:28:40
 */
public interface LastdayMemberStatisticsService extends IService<LastdayMemberStatistics> {

    /**
     * 获取列表。分页
     * @param lastdayMemberStatistics 查询参数
     * @return page
     */
    Page<LastdayMemberStatistics> listLastdayMemberStatisticsPage(LastdayMemberStatistics lastdayMemberStatistics);

}
