package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.LevelCount;
import com.kalvin.kvf.modules.func.entity.PesonStatistics;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 人员积分统计 Mapper 接口
 * </p>
 * @since 2020-07-27 16:13:18
 */
public interface PesonStatisticsMapper extends BaseMapper<PesonStatistics> {

    /**
     * 查询列表(分页)
     * @param pesonStatistics 查询参数
     * @param page 分页参数
     * @return list
     */
    List<PesonStatistics> selectPesonStatisticsList(@Param ("pesonStatistics") PesonStatistics pesonStatistics, IPage page);

    /**
     * 生成邀请码统计的临时表
     * @param inviteCode
     */
    @Select ("call showChildNodes(#{inviteCode})")
    void createTempTable(@Param ("inviteCode") String inviteCode);

    /**
     * 生成邀请码统计的临时表
     * @param inviteCode
     */
    @Select ("call showDeptNodes(#{inviteCode})")
    void createDeptTempTable(@Param ("inviteCode") String inviteCode);

    /**
     * 获取几代的统计数据
     */
    @Select ("SELECT nLevel, COUNT(nLevel) count FROM tempinvitecode\n" +
            "WHERE parent_id = #{inviteCode}\n" +
            "GROUP BY nLevel\n" +
            "ORDER BY nLevel")
    List<LevelCount> getLevelCount(@Param ("inviteCode") String inviteCode);

    /**
     * 获取部门统计的几代数据
     */
    @Select ("SELECT nLevel, COUNT(nLevel) count FROM tempdeptnodes\n" +
            "GROUP BY nLevel\n" +
            "ORDER BY nLevel")
    List<LevelCount> getDeptLevelCount();

    @Delete ("DELETE FROM tempinvitecode WHERE parent_id = #{inviteCode}")
    void deleteTempInvited(@Param ("inviteCode") String inviteCode);
}
