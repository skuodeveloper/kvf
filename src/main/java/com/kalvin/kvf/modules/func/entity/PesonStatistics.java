package com.kalvin.kvf.modules.func.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.kalvin.kvf.common.entity.BaseEntity;

/**
 * <p>
 * 人员积分统计
 * </p>
 * @since 2020-07-27 16:13:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("func_peson_statistics")
public class PesonStatistics extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门
     */
    private String deptName;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 总分
     */
    private Float totalScore;

}
