package com.kalvin.kvf.common.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WxMaJscode2Session {
    @SerializedName("session_key")
    private String sessionKey;
    @SerializedName("openid")
    private String openid;
    @SerializedName("unionid")
    private String unionid;
    @SerializedName("errcode")
    private String errcode;
    @SerializedName("errmsg")
    private String errmsg;
}
