package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.WxUserJf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ji fen li shi biao Mapper 接口
 * </p>
 * @since 2020-08-11 13:49:04
 */
public interface WxUserJfMapper extends BaseMapper<WxUserJf> {

    /**
     * 查询列表(分页)
     * @param wxUserJf 查询参数
     * @param page 分页参数
     * @return list
     */
    List<WxUserJf> selectWxUserJfList(@Param ("wxUserJf") WxUserJf wxUserJf, IPage page);

}
