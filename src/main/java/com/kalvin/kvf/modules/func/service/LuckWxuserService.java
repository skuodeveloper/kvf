package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.LuckWxuser;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2020-08-21 14:27:53
 */
public interface LuckWxuserService extends IService<LuckWxuser> {

    /**
     * 获取列表。分页
     * @param luckWxuser 查询参数
     * @return page
     */
    Page<LuckWxuser> listLuckWxuserPage(LuckWxuser luckWxuser);

}
