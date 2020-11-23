package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.FzBmb;

/**
 * <p>
 * 星火反诈联盟报名表 服务类
 * </p>
 * @since 2020-10-28 18:26:08
 */
public interface FzBmbService extends IService<FzBmb> {

    /**
     * 获取列表。分页
     * @param fzBmb 查询参数
     * @return page
     */
    Page<FzBmb> listFzBmbPage(FzBmb fzBmb);

}
