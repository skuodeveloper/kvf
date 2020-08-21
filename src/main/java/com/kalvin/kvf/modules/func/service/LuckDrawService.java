package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.LuckDraw;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2020-08-19 20:03:39
 */
public interface LuckDrawService extends IService<LuckDraw> {

    /**
     * 获取列表。分页
     * @param luckDraw 查询参数
     * @return page
     */
    Page<LuckDraw> listLuckDrawPage(LuckDraw luckDraw);

}
