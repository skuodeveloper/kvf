package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.PersonTypeCount;
import com.kalvin.kvf.modules.func.entity.PersonTypeStatistic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @since 2020-08-12 16:31:03
 */
public interface PersonTypeStatisticMapper extends BaseMapper<PersonTypeStatistic> {

    /**
     * 查询列表(分页)
     *
     * @param personTypeStatistic 查询参数
     * @param page                分页参数
     * @return list
     */
    List<PersonTypeStatistic> selectPersonTypeStatisticList(@Param("personTypeStatistic") PersonTypeStatistic personTypeStatistic, IPage page);

    @Select("SELECT person_type as persontype, COUNT(*) count\n" +
            "FROM func_person_type_statistic\n" +
            "GROUP BY person_type\n" +
            "ORDER BY person_type")
    List<PersonTypeCount> getPersonType();

    @Select("SELECT person_type as persontype, COUNT(*) count\n" +
            "FROM func_person_type_statistic\n" +
            "WHERE dept_invited_code in (#{invitedCode})\n" +
            "GROUP BY person_type\n" +
            "ORDER BY person_type")
    List<PersonTypeCount> getPersonTypeByDept(@Param("invitedCode") String invitedCode);
}
