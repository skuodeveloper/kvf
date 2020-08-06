package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.LastdayDeptStatistics;
import com.kalvin.kvf.modules.func.mapper.LastdayDeptStatisticsMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2020-08-06 08:29:07
 */
@Service
public class LastdayDeptStatisticsServiceImpl extends ServiceImpl<LastdayDeptStatisticsMapper, LastdayDeptStatistics> implements LastdayDeptStatisticsService {

    @Override
    public Page<LastdayDeptStatistics> listLastdayDeptStatisticsPage(LastdayDeptStatistics lastdayDeptStatistics) {
        Page<LastdayDeptStatistics> page = new Page<>(lastdayDeptStatistics.getCurrent(), lastdayDeptStatistics.getSize());
        List<LastdayDeptStatistics> lastdayDeptStatisticss = baseMapper.selectLastdayDeptStatisticsList(lastdayDeptStatistics, page);
        return page.setRecords(lastdayDeptStatisticss);
    }

}
