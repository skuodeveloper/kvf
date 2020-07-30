package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.QuestionBank;

/**
 * <p>
 * 题库 服务类
 * </p>
 * @since 2020-07-20 10:24:50
 */
public interface QuestionBankService extends IService<QuestionBank> {

    /**
     * 获取列表。分页
     * @param questionBank 查询参数
     * @return page
     */
    Page<QuestionBank> listQuestionBankPage(QuestionBank questionBank);

}
