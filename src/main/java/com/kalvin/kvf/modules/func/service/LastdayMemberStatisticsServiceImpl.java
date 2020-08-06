package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.LastdayMemberStatistics;
import com.kalvin.kvf.modules.func.mapper.LastdayMemberStatisticsMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2020-08-06 13:28:40
 */
@Service
public class LastdayMemberStatisticsServiceImpl extends ServiceImpl<LastdayMemberStatisticsMapper, LastdayMemberStatistics> implements LastdayMemberStatisticsService {

    @Override
    public Page<LastdayMemberStatistics> listLastdayMemberStatisticsPage(LastdayMemberStatistics lastdayMemberStatistics) {
        Page<LastdayMemberStatistics> page = new Page<>(lastdayMemberStatistics.getCurrent(), lastdayMemberStatistics.getSize());
        List<LastdayMemberStatistics> lastdayMemberStatisticss = baseMapper.selectLastdayMemberStatisticsList(lastdayMemberStatistics, page);
        return page.setRecords(lastdayMemberStatisticss);
    }

}
