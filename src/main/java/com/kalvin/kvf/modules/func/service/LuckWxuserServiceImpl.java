package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.LuckWxuser;
import com.kalvin.kvf.modules.func.mapper.LuckWxuserMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2020-08-21 14:27:53
 */
@Service
public class LuckWxuserServiceImpl extends ServiceImpl<LuckWxuserMapper, LuckWxuser> implements LuckWxuserService {

    @Override
    public Page<LuckWxuser> listLuckWxuserPage(LuckWxuser luckWxuser) {
        Page<LuckWxuser> page = new Page<>(luckWxuser.getCurrent(), luckWxuser.getSize());
        List<LuckWxuser> luckWxusers = baseMapper.selectLuckWxuserList(luckWxuser, page);
        return page.setRecords(luckWxusers);
    }

}
