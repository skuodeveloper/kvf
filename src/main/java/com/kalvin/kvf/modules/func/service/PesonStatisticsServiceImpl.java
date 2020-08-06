package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kalvin.kvf.modules.func.entity.LevelCount;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.PesonStatistics;
import com.kalvin.kvf.modules.func.mapper.PesonStatisticsMapper;
import org.springframework.transaction.annotation.Transactional;

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
    public void createDeptTempTable(String inviteCode) {
        baseMapper.createDeptTempTable (inviteCode);
    }

    @Override
    public List<LevelCount> getLevelCount(String inviteCode) {
        return baseMapper.getLevelCount (inviteCode);
    }

    @Override
    public List<LevelCount> getDeptLevelCount() {
        return baseMapper.getDeptLevelCount ();
    }

    @Override
    public void deleteTempInvited(String inviteCode) {
        baseMapper.deleteTempInvited (inviteCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<LevelCount> getLevelCounts(String inviteCode){
        createTempTable (inviteCode);
        List<LevelCount> levelCounts = getLevelCount (inviteCode);
        deleteTempInvited (inviteCode);

        return  levelCounts;
    }
}
