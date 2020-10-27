package com.kalvin.kvf.common.utils;

import com.alibaba.fastjson.JSON;
import com.kalvin.kvf.common.entity.MyX509TrustManager;
import com.kalvin.kvf.common.entity.WeChatAccessToken;
import com.kalvin.kvf.common.entity.WechatUserinfo;
import com.kalvin.kvf.common.entity.WxMaJscode2Session;
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

public class MiniWechatUtils {
    // 微信公众号的appId以及secret
//    private static String appId = "wx719c937ea903be65"; //test
//    private static String secret = "b22eabdd155cd8174e2791c95d513a7e"; //test

    private static String appId = "wxb4696a398e874f0a";
    private static String secret = "7a38780c7203cb6743b57018cd7bc780";

    /* 登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程 */
    private static String Code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    /**
     * 根据code获取到网页授权access_token
     *
     * @param code 微信回调后带有的参数code值
     * @author Shen
     */
    public static WxMaJscode2Session getSession(String code) {
        String url = Code2SessionUrl.replace ("APPID", appId).replace ("SECRET", secret)
                .replace ("JSCODE", code);

        String str = httpRequest (url, "POST", null);
        return JSON.parseObject (str, WxMaJscode2Session.class);
    }

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
