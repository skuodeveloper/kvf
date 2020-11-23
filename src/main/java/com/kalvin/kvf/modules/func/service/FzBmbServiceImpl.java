package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.FzBmb;
import com.kalvin.kvf.modules.func.mapper.FzBmbMapper;

import java.util.List;

/**
 * <p>
 * 星火反诈联盟报名表 服务实现类
 * </p>
 * @since 2020-10-28 18:26:08
 */
@Service
public class FzBmbServiceImpl extends ServiceImpl<FzBmbMapper, FzBmb> implements FzBmbService {

    @Override
    public Page<FzBmb> listFzBmbPage(FzBmb fzBmb) {
        Page<FzBmb> page = new Page<>(fzBmb.getCurrent(), fzBmb.getSize());
        List<FzBmb> fzBmbs = baseMapper.selectFzBmbList(fzBmb, page);
        return page.setRecords(fzBmbs);
    }

}
