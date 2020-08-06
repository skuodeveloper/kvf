package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.TempDeptStatistics;
import com.kalvin.kvf.modules.func.mapper.TempDeptStatisticsMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2020-08-05 14:10:48
 */
@Service
public class TempDeptStatisticsServiceImpl extends ServiceImpl<TempDeptStatisticsMapper, TempDeptStatistics> implements TempDeptStatisticsService {

    @Override
    public Page<TempDeptStatistics> listTempDeptStatisticsPage(TempDeptStatistics tempDeptStatistics) {
        Page<TempDeptStatistics> page = new Page<>(tempDeptStatistics.getCurrent(), tempDeptStatistics.getSize());
        List<TempDeptStatistics> tempDeptStatisticss = baseMapper.selectTempDeptStatisticsList(tempDeptStatistics, page);
        return page.setRecords(tempDeptStatisticss);
    }

}
