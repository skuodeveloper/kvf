package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.AnswerRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 答题得分记录 Mapper 接口
 * </p>
 * @since 2020-07-23 19:10:35
 */
public interface AnswerRecordMapper extends BaseMapper<AnswerRecord> {

    /**
     * 查询列表(分页)
     * @param answerRecord 查询参数
     * @param page 分页参数
     * @return list
     */
    List<AnswerRecord> selectAnswerRecordList(@Param ("answerRecord") AnswerRecord answerRecord, IPage page);

}
