package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.TempMemberStatistics;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2020-08-06 13:28:42
 */
public interface TempMemberStatisticsMapper extends BaseMapper<TempMemberStatistics> {

    /**
     * 查询列表(分页)
     * @param tempMemberStatistics 查询参数
     * @param page 分页参数
     * @return list
     */
    List<TempMemberStatistics> selectTempMemberStatisticsList(TempMemberStatistics tempMemberStatistics, IPage page);

}
