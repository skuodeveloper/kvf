package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.Test;

/**
 * <p>
 * 测试表 服务类
 * </p>
 * @since 2020-05-09 10:57:42
 */
public interface TestService extends IService<Test> {

    /**
     * 获取列表。分页
     * @param test 查询参数
     * @return page
     */
    Page<Test> listTestPage(Test test);

}
