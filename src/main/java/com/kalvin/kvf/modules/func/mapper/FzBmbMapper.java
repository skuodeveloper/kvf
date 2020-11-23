package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.FzBmb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 星火反诈联盟报名表 Mapper 接口
 * </p>
 * @since 2020-10-28 18:26:08
 */
public interface FzBmbMapper extends BaseMapper<FzBmb> {

    /**
     * 查询列表(分页)
     * @param fzBmb 查询参数
     * @param page 分页参数
     * @return list
     */
    List<FzBmb> selectFzBmbList(@Param ("fzBmb") FzBmb fzBmb, IPage page);

}
