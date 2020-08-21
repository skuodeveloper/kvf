package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.J7rZhbiaoStatistics;
import com.kalvin.kvf.modules.func.mapper.J7rZhbiaoStatisticsMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2020-08-18 10:07:13
 */
@Service
public class J7rZhbiaoStatisticsServiceImpl extends ServiceImpl<J7rZhbiaoStatisticsMapper, J7rZhbiaoStatistics> implements J7rZhbiaoStatisticsService {

    @Override
    public Page<J7rZhbiaoStatistics> listJ7rZhbiaoStatisticsPage(J7rZhbiaoStatistics j7rZhbiaoStatistics) {
        Page<J7rZhbiaoStatistics> page = new Page<>(j7rZhbiaoStatistics.getCurrent(), j7rZhbiaoStatistics.getSize());
        List<J7rZhbiaoStatistics> j7rZhbiaoStatisticss = baseMapper.selectJ7rZhbiaoStatisticsList(j7rZhbiaoStatistics, page);
        return page.setRecords(j7rZhbiaoStatisticss);
    }

}
