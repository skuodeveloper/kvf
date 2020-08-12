package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.WxUserJf;
import com.kalvin.kvf.modules.func.mapper.WxUserJfMapper;

import java.util.List;

/**
 * <p>
 * ji fen li shi biao 服务实现类
 * </p>
 * @since 2020-08-11 13:49:04
 */
@Service
public class WxUserJfServiceImpl extends ServiceImpl<WxUserJfMapper, WxUserJf> implements WxUserJfService {

    @Override
    public Page<WxUserJf> listWxUserJfPage(WxUserJf wxUserJf) {
        Page<WxUserJf> page = new Page<>(wxUserJf.getCurrent(), wxUserJf.getSize());
        List<WxUserJf> wxUserJfs = baseMapper.selectWxUserJfList(wxUserJf, page);
        return page.setRecords(wxUserJfs);
    }

}
