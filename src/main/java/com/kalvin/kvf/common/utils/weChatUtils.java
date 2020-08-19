package com.kalvin.kvf.common.utils;

import com.alibaba.fastjson.JSON;
import com.kalvin.kvf.common.entity.MyX509TrustManager;
import com.kalvin.kvf.common.entity.WeChatAccessToken;
import com.kalvin.kvf.common.entity.WechatUserinfo;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

public class weChatUtils {
    // 微信公众号的appId以及secret
//    private static String appId = "wx719c937ea903be65"; //test
//    private static String secret = "b22eabdd155cd8174e2791c95d513a7e"; //test

    private static String appId = "wx5b66c0f1cfaae239";
    private static String secret = "856f2467cc71f138586c5a44b668c377";

    // 获取网页授权access_token的Url，和基础服务access_token不同，记得区分
    private static String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 刷新网页授权access_token的Url，和基础服务access_token不同，记得区分
    private static String getRefreshAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    // 检验授权凭证access_token是否有效,和基础服务access_token不同，记得区分
    private static String checkAccessTokenUrl = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
    // 获取用户信息的Url
    private static String getWXUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    // 调用微信JS接口的临时Token
    private static String getJsApiToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 调用微信JS接口的临时票据
    private static String getJsApiTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    /**
     * 根据code获取到网页授权access_token
     *
     * @param code 微信回调后带有的参数code值
     * @author Shen
     */
    public static WeChatAccessToken getAccessToken(String code) {
        String url = weChatUtils.getAccessTokenUrl.replace ("APPID", weChatUtils.appId).replace ("SECRET", secret)
                .replace ("CODE", code);
        JSONObject jsonObj = JSONObject.fromObject (httpRequest (url, "POST", null));
        return (WeChatAccessToken) JSONObject.toBean (jsonObj, WeChatAccessToken.class);
//        return JSON.parseObject (httpRequest (url, "POST", null), WeChatAccessToken.class);
    }

    /**
     * 获取接口访问Token
     */
    public static String getJsApiToken() {
        //凭证获取(GET)
        String url = weChatUtils.getJsApiToken.replace ("APPID", appId).replace ("APPSECRET", secret);
        // 发起GET请求获取凭证
        JSONObject jsonObject = JSONObject.fromObject (httpRequest (url, "GET", null));
        String access_token = null;
        if (null != jsonObject) {
            try {
                access_token = jsonObject.getString ("access_token");
            } catch (JSONException e) {
                // 获取token失败
            }
        }
        return access_token;
    }

    /**
     * 调用微信JS接口的临时票据
     *
     * @param jsApiToken 接口访问凭证
     * @return
     */
    public static String getJsApiTicket(String jsApiToken) {
        String url = weChatUtils.getJsApiTicket.replace ("ACCESS_TOKEN", jsApiToken);
        // 发起GET请求获取凭证
        JSONObject jsonObject = JSONObject.fromObject (httpRequest (url, "GET", null));
        String ticket = null;
        if (null != jsonObject) {
            try {
                ticket = jsonObject.getString ("ticket");
            } catch (JSONException e) {
                // 获取token失败
//                e.printStackTrace (e.getMessage ());
            }
        }
        return ticket;
    }

    /**
     * 根据在获取accessToken时返回的refreshToken刷新accessToken
     *
     * @param refreshToken
     * @author Shen
     */
    public static WeChatAccessToken getRefreshAccessToken(String refreshToken) {
        String url = weChatUtils.getRefreshAccessTokenUrl.replace ("APPID", weChatUtils.appId).replace ("REFRESH_TOKEN",
                refreshToken);
        JSONObject jsonObj = JSONObject.fromObject (httpRequest (url, "POST", null));
        return (WeChatAccessToken) JSONObject.toBean (jsonObj, WeChatAccessToken.class);

//        return JSON.parseObject (httpRequest (url, "POST", null), WeChatAccessToken.class);
    }

    /**
     * 获取微信用户信息
     *
     * @param openId      微信标识openId
     * @param accessToken 微信网页授权accessToken
     * @author Shen
     */
    public static WechatUserinfo getWXUserInfoUrl(String openId, String accessToken) {
        String url = weChatUtils.getWXUserInfoUrl.replace ("OPENID", openId).replace ("ACCESS_TOKEN", accessToken);
        JSONObject jsonObj = JSONObject.fromObject (httpRequest (url, "POST", null));
        return (WechatUserinfo) JSONObject.toBean (jsonObj, WechatUserinfo.class);
//        return JSON.parseObject (httpRequest (url, "POST", null), WechatUserinfo.class);
    }

    /**
     * 检验授权凭证accessToken是否有效
     *
     * @param openId
     * @param accessToken
     * @author Shen
     */
//    public WechatAccessTokenCheck checkAccessToken(String openId, String accessToken) {
//        String url = weChatUtils.checkAccessTokenUrl.replace ("OPENID", openId).replace ("ACCESS_TOKEN", accessToken);
//        JSONObject jsonObj = JSONObject.fromObject (httpRequest (url, "POST", null));
//        return (WechatAccessTokenCheck) JSONObject.toBean (jsonObj, WechatAccessTokenCheck.class);
//    }

    /**
     * get或者post请求
     *
     * @param requestUrl
     * @param requestMethod GET or POST 需要大写*
     * @param outputStr
     * @return
     * @author Shen
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer ();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager ()};
            SSLContext sslContext = SSLContext.getInstance ("SSL", "SunJSSE");
            sslContext.init (null, tm, new java.security.SecureRandom ());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory ();
            URL url = new URL (requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection ();
            httpUrlConn.setSSLSocketFactory (ssf);
            httpUrlConn.setDoOutput (true);
            httpUrlConn.setDoInput (true);
            httpUrlConn.setUseCaches (false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod (requestMethod);
            if ("GET".equalsIgnoreCase (requestMethod)) {
                httpUrlConn.connect ();
            }
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream ();
                // 注意编码格式，防止中文乱码
                outputStream.write (outputStr.getBytes ("UTF-8"));
                outputStream.close ();
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream ();
            InputStreamReader inputStreamReader = new InputStreamReader (inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader (inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine ()) != null) {
                buffer.append (str);
            }
            bufferedReader.close ();
            inputStreamReader.close ();
            // 释放资源
            inputStream.close ();
            inputStream = null;
            httpUrlConn.disconnect ();
        } catch (ConnectException ce) {
            System.out.println ("Weixin server connection timed out." + ce.getMessage ());
        } catch (Exception e) {
            System.out.println ("https request error:{}" + e.getMessage ());
        }
        return buffer.toString ();
    }
}
