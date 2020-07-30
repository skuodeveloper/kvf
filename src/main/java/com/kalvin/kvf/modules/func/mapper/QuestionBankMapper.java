package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 题库 Mapper 接口
 * </p>
 * @since 2020-07-20 10:24:50
 */
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {

    /**
     * 查询列表(分页)
     * @param questionBank 查询参数
     * @param page 分页参数
     * @return list
     */
    List<QuestionBank> selectQuestionBankList(@Param("questionBank") QuestionBank questionBank, IPage page);

}
