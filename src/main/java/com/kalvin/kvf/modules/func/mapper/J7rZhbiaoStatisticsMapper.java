package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.J7rZhbiaoStatistics;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2020-08-18 10:07:13
 */
public interface J7rZhbiaoStatisticsMapper extends BaseMapper<J7rZhbiaoStatistics> {

    /**
     * 查询列表(分页)
     * @param j7rZhbiaoStatistics 查询参数
     * @param page 分页参数
     * @return list
     */
    List<J7rZhbiaoStatistics> selectJ7rZhbiaoStatisticsList(J7rZhbiaoStatistics j7rZhbiaoStatistics, IPage page);

}
