package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.ViewHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 查看分数记录 Mapper 接口
 * </p>
 * @since 2020-08-14 14:13:25
 */
public interface ViewHistoryMapper extends BaseMapper<ViewHistory> {

    /**
     * 查询列表(分页)
     * @param viewHistory 查询参数
     * @param page 分页参数
     * @return list
     */
    List<ViewHistory> selectViewHistoryList(@Param ("viewHistory") ViewHistory viewHistory, IPage page);

}
