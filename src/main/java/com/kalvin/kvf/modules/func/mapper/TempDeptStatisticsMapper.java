package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.TempDeptStatistics;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2020-08-05 14:10:48
 */
public interface TempDeptStatisticsMapper extends BaseMapper<TempDeptStatistics> {

    /**
     * 查询列表(分页)
     * @param tempDeptStatistics 查询参数
     * @param page 分页参数
     * @return list
     */
    List<TempDeptStatistics> selectTempDeptStatisticsList(TempDeptStatistics tempDeptStatistics, IPage page);

}
