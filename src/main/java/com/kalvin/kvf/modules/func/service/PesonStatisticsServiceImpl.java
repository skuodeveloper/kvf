package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kalvin.kvf.modules.func.entity.LevelCount;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.PesonStatistics;
import com.kalvin.kvf.modules.func.mapper.PesonStatisticsMapper;

import java.util.List;

/**
 * <p>
 * 人员积分统计 服务实现类
 * </p>
 * @since 2020-07-27 16:13:18
 */
@Service
public class PesonStatisticsServiceImpl extends ServiceImpl<PesonStatisticsMapper, PesonStatistics> implements PesonStatisticsService {

    @Override
    public Page<PesonStatistics> listPesonStatisticsPage(PesonStatistics pesonStatistics) {
        Page<PesonStatistics> page = new Page<>(pesonStatistics.getCurrent(), pesonStatistics.getSize());
        List<PesonStatistics> pesonStatisticss = baseMapper.selectPesonStatisticsList(pesonStatistics, page);
        return page.setRecords(pesonStatisticss);
    }

    @Override
    public void createTempTable(String inviteCode) {
        baseMapper.createTempTable (inviteCode);
    }

    @Override
    public List<LevelCount> getLevelCount() {
        return baseMapper.getLevelCount ();
    }
}
