package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.ViewHistory;

/**
 * <p>
 * 查看分数记录 服务类
 * </p>
 * @since 2020-08-14 14:13:25
 */
public interface ViewHistoryService extends IService<ViewHistory> {

    /**
     * 获取列表。分页
     * @param viewHistory 查询参数
     * @return page
     */
    Page<ViewHistory> listViewHistoryPage(ViewHistory viewHistory);

}
