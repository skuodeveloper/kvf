package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.LuckWxuser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2020-08-21 14:27:53
 */
public interface LuckWxuserMapper extends BaseMapper<LuckWxuser> {

    /**
     * 查询列表(分页)
     * @param luckWxuser 查询参数
     * @param page 分页参数
     * @return list
     */
    List<LuckWxuser> selectLuckWxuserList(@Param ("luckWxuser") LuckWxuser luckWxuser, IPage page);

}
