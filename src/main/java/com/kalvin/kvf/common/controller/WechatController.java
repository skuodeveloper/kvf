package com.kalvin.kvf.common.controller;

import com.kalvin.kvf.common.config.WeChatConfig;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.common.entity.WeChatAccessToken;
import com.kalvin.kvf.common.entity.WechatUserinfo;
import com.kalvin.kvf.common.utils.weChatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@RestController
@RequestMapping("/api/v1/wechat")
public class WechatController {
    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 拼装微信扫一扫登录url
     */
    @GetMapping("login_url")
    @ResponseBody
    public R loginUrl(@RequestParam(value = "access_page", required = true) String accessPage) throws UnsupportedEncodingException {
        //官方文档说明需要进行编码
        String callbackUrl = URLEncoder.encode(weChatConfig.getOpenRedirectUrl(), "GBK");

        //格式化，返回拼接后的url，去调微信的二维码
        String qrcodeUrl = String.format(WeChatConfig.OPEN_QRCODE_URL, weChatConfig.getOpenAppid(), callbackUrl, accessPage);
        return R.ok (qrcodeUrl);
    }

    /**
     * 用户授权成功，获取微信回调的code
     */
//    @GetMapping("/user/code")
//    public void wechatUserCallback(@RequestParam(value = "code",required = true) String code, String state, HttpServletResponse response){
//        try {
//            WeChatAccessToken weChatAccessToken = weChatUtils.getAccessToken (code);
//            WechatUserinfo wechatUserinfo = weChatUtils.getWXUserInfoUrl(weChatAccessToken.getOpenid (), weChatAccessToken.getAccess_token ());
//            System.out.println (wechatUserinfo);
//        }catch (Exception ex){
//            ex.printStackTrace ();
//        }
//    }

    /**
     * 用户授权成功，获取微信回调的code
     */
    @GetMapping("/getUserInfo")
    public R getUserInfo(@RequestParam(value = "code") String code){
        try {
            WeChatAccessToken weChatAccessToken = weChatUtils.getAccessToken (code);
            WechatUserinfo wechatUserinfo = weChatUtils.getWXUserInfoUrl(weChatAccessToken.getOpenid (), weChatAccessToken.getAccess_token ());
            return R.ok (wechatUserinfo);
        }catch (Exception ex){
            return R.fail (ex.getMessage ());
        }
    }
}
