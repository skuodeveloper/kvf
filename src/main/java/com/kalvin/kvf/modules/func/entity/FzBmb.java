package com.kalvin.kvf.modules.func.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.kalvin.kvf.common.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

/**
 * <p>
 * 星火反诈联盟报名表
 * </p>
 * @since 2020-10-28 18:26:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("func_fz_bmb")
public class FzBmb extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * openid
     */
    private String openid;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 电话
     */
    private String tel;

    /**
     * 所在镇(街道)
     */
    private String zjd;

    /**
     * 注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 反诈活动
     */
    private String fztype;

}
