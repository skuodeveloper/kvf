package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.mapper.QuestionBankMapper;

import java.util.List;

/**
 * <p>
 * 题库 服务实现类
 * </p>
 * @since 2020-07-20 10:24:50
 */
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {

    @Override
    public Page<QuestionBank> listQuestionBankPage(QuestionBank questionBank) {
        Page<QuestionBank> page = new Page<>(questionBank.getCurrent(), questionBank.getSize());
        List<QuestionBank> questionBanks = baseMapper.selectQuestionBankList(questionBank, page);
        return page.setRecords(questionBanks);
    }
}
