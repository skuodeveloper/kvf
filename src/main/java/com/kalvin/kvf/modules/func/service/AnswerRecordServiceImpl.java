package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.AnswerRecord;
import com.kalvin.kvf.modules.func.mapper.AnswerRecordMapper;

import java.util.List;

/**
 * <p>
 * 答题得分记录 服务实现类
 * </p>
 * @since 2020-07-23 19:10:35
 */
@Service
public class AnswerRecordServiceImpl extends ServiceImpl<AnswerRecordMapper, AnswerRecord> implements AnswerRecordService {

    @Override
    public Page<AnswerRecord> listAnswerRecordPage(AnswerRecord answerRecord) {
        Page<AnswerRecord> page = new Page<>(answerRecord.getCurrent(), answerRecord.getSize());
        List<AnswerRecord> answerRecords = baseMapper.selectAnswerRecordList(answerRecord, page);
        return page.setRecords(answerRecords);
    }

}
