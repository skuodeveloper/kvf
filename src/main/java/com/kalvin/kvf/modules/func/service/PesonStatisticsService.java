package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.LevelCount;
import com.kalvin.kvf.modules.func.entity.PesonStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 人员积分统计 服务类
 * </p>
 * @since 2020-07-27 16:13:18
 */
public interface PesonStatisticsService extends IService<PesonStatistics> {

    /**
     * 获取列表。分页
     * @param pesonStatistics 查询参数
     * @return page
     */
    Page<PesonStatistics> listPesonStatisticsPage(PesonStatistics pesonStatistics);

    void createTempTable(String inviteCode);

    void createDeptTempTable(String inviteCode);

    List<LevelCount> getLevelCount(String inviteCode);

    List<LevelCount> getDeptLevelCount();

    void deleteTempInvited(String inviteCode);

    List<LevelCount> getLevelCounts(String inviteCode);
}
