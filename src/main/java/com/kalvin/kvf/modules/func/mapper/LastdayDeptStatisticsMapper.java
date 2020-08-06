package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.LastdayDeptStatistics;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2020-08-06 08:29:07
 */
public interface LastdayDeptStatisticsMapper extends BaseMapper<LastdayDeptStatistics> {

    /**
     * 查询列表(分页)
     * @param lastdayDeptStatistics 查询参数
     * @param page 分页参数
     * @return list
     */
    List<LastdayDeptStatistics> selectLastdayDeptStatisticsList(LastdayDeptStatistics lastdayDeptStatistics, IPage page);

}
