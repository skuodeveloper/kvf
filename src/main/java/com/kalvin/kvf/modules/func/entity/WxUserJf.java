package com.kalvin.kvf.modules.func.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.kalvin.kvf.common.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * ji fen li shi biao
 * </p>
 * @since 2020-08-11 13:49:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("func_wx_user_jf")
public class WxUserJf extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 源邀请码
     */
    private String originInvitedCode;

    /**
     * 
     */
    private String orginNickname;

    /**
     * 
     */
    private String orginHeadimgurl;

    /**
     * 目标邀请码
     */
    private String targetInvitedCode;

    /**
     * yuan ji fen
     */
    private Float originJf;

    /**
     * zeng jia ji fen
     */
    private Float addJf;

    /**
     * ceng ji
     */
    private Integer level;

    /**
     * mu biao ji fen
     */
    private Float targetJf;

    /**
     * zhu ce shi jian
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date zcTime;

}
