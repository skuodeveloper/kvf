package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.WxUser;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 * @since 2020-07-23 16:06:02
 */
public interface WxUserService extends IService<WxUser> {

    /**
     * 获取列表。分页
     * @param wxUser 查询参数
     * @return page
     */
    Page<WxUser> listWxUserPage(WxUser wxUser);

    /**
     * 获取列表。分页
     * @param wxUser 查询参数
     * @return page
     */
    Page<WxUser> luckWxUserList(WxUser wxUser);

    int updWxInfo(WxUser wxUser);
}
