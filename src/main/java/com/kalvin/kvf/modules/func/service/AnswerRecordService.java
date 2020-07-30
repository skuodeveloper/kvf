package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.AnswerRecord;

/**
 * <p>
 * 答题得分记录 服务类
 * </p>
 * @since 2020-07-23 19:10:35
 */
public interface AnswerRecordService extends IService<AnswerRecord> {

    /**
     * 获取列表。分页
     * @param answerRecord 查询参数
     * @return page
     */
    Page<AnswerRecord> listAnswerRecordPage(AnswerRecord answerRecord);

}
