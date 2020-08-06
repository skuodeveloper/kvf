package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.TempMemberStatistics;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2020-08-06 13:28:42
 */
public interface TempMemberStatisticsService extends IService<TempMemberStatistics> {

    /**
     * 获取列表。分页
     * @param tempMemberStatistics 查询参数
     * @return page
     */
    Page<TempMemberStatistics> listTempMemberStatisticsPage(TempMemberStatistics tempMemberStatistics);

}
