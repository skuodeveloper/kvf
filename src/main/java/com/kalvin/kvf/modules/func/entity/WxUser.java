package com.kalvin.kvf.modules.func.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kalvin.kvf.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * <p>
 * 用户管理
 * </p>
 *
 * @since 2020-07-23 18:07:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("func_wx_user")
public class WxUser extends BaseEntity {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String openid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 乡
     */
    private String country;

    /**
     * 头像
     */
    private String headimgurl;

    /**
     *
     */
    private String privilege;

    /**
     *
     */
    private String unionid;

    /**
     * 人员类别
     */
    private String personType;

    /**
     * 公司单位
     */
    private String companyName;

    /**
     * 电话
     */
    private String tel;

    /**
     * 二维码
     */
    private String qrcode;

    /**
     * 父邀请码
     */
    private String parentInvitedCode;

    /**
     * 自己邀请码
     */
    private String invitedCode;

    /**
     * 是否计算过
     */
    private String calculate;

    /**
     * 推广人数
     */
    private Integer tgrs;

    /**
     * 积分
     */
    private Float jf;

    /**
     * 根邀请码
     */
    private String rootInvitedCode;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startdate;

    @TableField(exist = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enddate;

    @TableField(exist = false)
    private Integer telnull;

    @TableField(exist = false)
    private Integer tgrsMin = 0;

    @TableField(exist = false)
    private Integer tgrsMax = Integer.MAX_VALUE;

    @TableField(exist = false)
    private String title;
}
