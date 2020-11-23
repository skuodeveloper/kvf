package com.kalvin.kvf.common.controller;

import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.common.entity.WeChatAccessToken;
import com.kalvin.kvf.common.entity.WeChatTicket;
import com.kalvin.kvf.common.entity.WechatUserinfo;
import com.kalvin.kvf.common.entity.WxMaJscode2Session;
import com.kalvin.kvf.common.utils.MiniWechatUtils;
import com.kalvin.kvf.common.utils.PastUtil;
import com.kalvin.kvf.common.utils.weChatUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v2/wechat")
public class MiniWechatController {

    /**
     * 用户授权成功，获取微信回调的code
     */
    @GetMapping("/getUserInfo")
    public R getUserInfo(@RequestParam(value = "code") String code){
        try {
            WxMaJscode2Session wxMaJscode2Session = MiniWechatUtils.getSession (code);
            return R.ok (wxMaJscode2Session);
        }catch (Exception ex){
            return R.fail (ex.getMessage ());
        }
    }

    /**
     * 用户授权成功，获取微信回调的code
     */
    @GetMapping("/getQRCode")
    public R getQRCode(@RequestParam(value = "inviteCode") String inviteCode){
        try {
            String qrcode = MiniWechatUtils.downloadMiniCode(inviteCode);
            return R.ok (qrcode);
        }catch (Exception ex){
            return R.fail (ex.getMessage ());
        }
    }


}
