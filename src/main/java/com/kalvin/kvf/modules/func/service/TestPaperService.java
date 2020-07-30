package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.TestPaper;

/**
 * <p>
 * 答题试卷 服务类
 * </p>
 * @since 2020-07-24 11:00:26
 */
public interface TestPaperService extends IService<TestPaper> {

    /**
     * 获取列表。分页
     * @param testPaper 查询参数
     * @return page
     */
    Page<TestPaper> listTestPaperPage(TestPaper testPaper);

}
