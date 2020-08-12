package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.WxUserJf;

/**
 * <p>
 * ji fen li shi biao 服务类
 * </p>
 * @since 2020-08-11 13:49:04
 */
public interface WxUserJfService extends IService<WxUserJf> {

    /**
     * 获取列表。分页
     * @param wxUserJf 查询参数
     * @return page
     */
    Page<WxUserJf> listWxUserJfPage(WxUserJf wxUserJf);

}
