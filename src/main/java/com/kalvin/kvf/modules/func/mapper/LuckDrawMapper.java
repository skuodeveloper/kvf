package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.LuckDraw;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2020-08-19 20:03:39
 */
public interface LuckDrawMapper extends BaseMapper<LuckDraw> {

    /**
     * 查询列表(分页)
     * @param luckDraw 查询参数
     * @param page 分页参数
     * @return list
     */
    List<LuckDraw> selectLuckDrawList(@Param ("luckDraw") LuckDraw luckDraw, IPage page);

}
