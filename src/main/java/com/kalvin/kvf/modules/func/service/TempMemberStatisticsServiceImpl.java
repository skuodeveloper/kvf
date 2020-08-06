package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.TempMemberStatistics;
import com.kalvin.kvf.modules.func.mapper.TempMemberStatisticsMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2020-08-06 13:28:42
 */
@Service
public class TempMemberStatisticsServiceImpl extends ServiceImpl<TempMemberStatisticsMapper, TempMemberStatistics> implements TempMemberStatisticsService {

    @Override
    public Page<TempMemberStatistics> listTempMemberStatisticsPage(TempMemberStatistics tempMemberStatistics) {
        Page<TempMemberStatistics> page = new Page<>(tempMemberStatistics.getCurrent(), tempMemberStatistics.getSize());
        List<TempMemberStatistics> tempMemberStatisticss = baseMapper.selectTempMemberStatisticsList(tempMemberStatistics, page);
        return page.setRecords(tempMemberStatisticss);
    }

}
