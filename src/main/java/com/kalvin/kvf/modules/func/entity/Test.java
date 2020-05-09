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
 * 测试表
 * </p>
 * @since 2020-05-09 13:48:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("func_test")
public class Test extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 电话
     */
    private String tel;

    /**
     * 地址
     */
    private String address;

}
