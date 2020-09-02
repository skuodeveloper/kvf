package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.WxUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 用户管理 Mapper 接口
 * </p>
 * @since 2020-07-23 16:06:02
 */
public interface WxUserMapper extends BaseMapper<WxUser> {

    /**
     * 查询列表(分页)
     * @param wxUser 查询参数
     * @param page 分页参数
     * @return list
     */
    List<WxUser> selectWxUserList(@Param ("wxUser") WxUser wxUser, IPage page);

    /**
     * 查询列表(分页)
     * @param wxUser 查询参数
     * @return list
     */
    List<WxUser> drawWxUserList(@Param ("wxUser") WxUser wxUser);

    /**
     * 查询列表(分页)
     * @param wxUser 查询参数
     * @return list
     */
    List<WxUser> luckWxUserList(@Param ("wxUser") WxUser wxUser, IPage page);

    int updWxInfo(@Param ("wxUser") WxUser wxUser);

    /**
     *
     * @return
     */
    @Update ("update func_wx_user a,func_luck_wxuser b \n" +
            "set a.is_price = 0 \n" +
            "where a.id = b.wx_id and b.luck_id = #{luckId}")
    int updisPrice(@Param ("luckId") Long luckId);
}
