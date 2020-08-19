package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.ViewHistory;
import com.kalvin.kvf.modules.func.mapper.ViewHistoryMapper;

import java.util.List;

/**
 * <p>
 * 查看分数记录 服务实现类
 * </p>
 * @since 2020-08-14 14:13:25
 */
@Service
public class ViewHistoryServiceImpl extends ServiceImpl<ViewHistoryMapper, ViewHistory> implements ViewHistoryService {

    @Override
    public Page<ViewHistory> listViewHistoryPage(ViewHistory viewHistory) {
        Page<ViewHistory> page = new Page<>(viewHistory.getCurrent(), viewHistory.getSize());
        List<ViewHistory> viewHistorys = baseMapper.selectViewHistoryList(viewHistory, page);
        return page.setRecords(viewHistorys);
    }

}
