package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.Test;
import com.kalvin.kvf.modules.func.mapper.TestMapper;

import java.util.List;

/**
 * <p>
 * 测试表 服务实现类
 * </p>
 * @since 2020-05-09 10:57:42
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Override
    public Page<Test> listTestPage(Test test) {
        Page<Test> page = new Page<>(test.getCurrent(), test.getSize());
        List<Test> tests = baseMapper.selectTestList(test, page);
        return page.setRecords(tests);
    }
}
