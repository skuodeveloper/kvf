package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.TestPaper;
import com.kalvin.kvf.modules.func.mapper.TestPaperMapper;

import java.util.List;

/**
 * <p>
 * 答题试卷 服务实现类
 * </p>
 * @since 2020-07-24 11:00:26
 */
@Service
public class TestPaperServiceImpl extends ServiceImpl<TestPaperMapper, TestPaper> implements TestPaperService {

    @Override
    public Page<TestPaper> listTestPaperPage(TestPaper testPaper) {
        Page<TestPaper> page = new Page<>(testPaper.getCurrent(), testPaper.getSize());
        List<TestPaper> testPapers = baseMapper.selectTestPaperList(testPaper, page);
        return page.setRecords(testPapers);
    }

}
