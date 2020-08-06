package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.LastdayMemberStatistics;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2020-08-06 13:28:40
 */
public interface LastdayMemberStatisticsMapper extends BaseMapper<LastdayMemberStatistics> {

    /**
     * 查询列表(分页)
     * @param lastdayMemberStatistics 查询参数
     * @param page 分页参数
     * @return list
     */
    List<LastdayMemberStatistics> selectLastdayMemberStatisticsList(LastdayMemberStatistics lastdayMemberStatistics, IPage page);

}
