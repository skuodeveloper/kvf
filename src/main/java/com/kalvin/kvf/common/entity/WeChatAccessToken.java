package com.kalvin.kvf.common.entity;


import lombok.Data;

/**
 * 此AccessToken不是基础模块的AccessToken，是一个特殊的网页授权access_token，请记得区分
 *
 * @author Shen date : 2018-01-22
 */
@Data
public class WeChatAccessToken {
    /**
     * 直接引用了微信的返回值，不使用驼峰等命名 access_token
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同 expires_in
     * access_token接口调用凭证超时时间，单位（秒） refresh_token 用户刷新access_token openid
     * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID scope
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    // 额外的信息
    private int errcode;
    private String errmsg;
}
