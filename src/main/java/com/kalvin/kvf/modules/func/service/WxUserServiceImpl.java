package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.WxUser;
import com.kalvin.kvf.modules.func.mapper.WxUserMapper;

import java.util.List;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 * @since 2020-07-23 16:06:02
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

    @Override
    public Page<WxUser> listWxUserPage(WxUser wxUser) {
        Page<WxUser> page = new Page<>(wxUser.getCurrent(), wxUser.getSize());
        List<WxUser> wxUsers = baseMapper.selectWxUserList(wxUser, page);
        return page.setRecords(wxUsers);
    }

    @Override
    public int updWxInfo(WxUser wxUser){
        return baseMapper.updWxInfo (wxUser);
    }
}
