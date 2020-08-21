package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.LuckDraw;
import com.kalvin.kvf.modules.func.mapper.LuckDrawMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2020-08-19 20:03:39
 */
@Service
public class LuckDrawServiceImpl extends ServiceImpl<LuckDrawMapper, LuckDraw> implements LuckDrawService {

    @Override
    public Page<LuckDraw> listLuckDrawPage(LuckDraw luckDraw) {
        Page<LuckDraw> page = new Page<>(luckDraw.getCurrent(), luckDraw.getSize());
        List<LuckDraw> luckDraws = baseMapper.selectLuckDrawList(luckDraw, page);
        return page.setRecords(luckDraws);
    }

}
