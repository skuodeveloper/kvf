package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.PersonTypeStatistic;
import com.kalvin.kvf.modules.func.mapper.PersonTypeStatisticMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2020-08-12 16:31:03
 */
@Service
public class PersonTypeStatisticServiceImpl extends ServiceImpl<PersonTypeStatisticMapper, PersonTypeStatistic> implements PersonTypeStatisticService {

    @Override
    public Page<PersonTypeStatistic> listPersonTypeStatisticPage(PersonTypeStatistic personTypeStatistic) {
        Page<PersonTypeStatistic> page = new Page<>(personTypeStatistic.getCurrent(), personTypeStatistic.getSize());
        List<PersonTypeStatistic> personTypeStatistics = baseMapper.selectPersonTypeStatisticList(personTypeStatistic, page);
        return page.setRecords(personTypeStatistics);
    }

}
