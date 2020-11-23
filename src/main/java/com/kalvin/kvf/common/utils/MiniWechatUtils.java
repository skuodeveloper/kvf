package com.kalvin.kvf.common.utils;

import com.alibaba.fastjson.JSON;
import com.kalvin.kvf.common.entity.MyX509TrustManager;
import com.kalvin.kvf.common.entity.WxMaJscode2Session;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MiniWechatUtils {
    // 微信公众号的appId以及secret
//    private static String appId = "wx719c937ea903be65"; //test
//    private static String secret = "b22eabdd155cd8174e2791c95d513a7e"; //test

    private static String appId = "wx7cf7a583d1a19a7d";
    private static String secret = "aa34c9cfe491f704b3d76c1fd0524626";

    /* 登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程 */
    private static String Code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    /*获取AccessToken*/
    private static String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /*获取二维码图片*/
    private static String getQRCodeUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";

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
     * 获取接口访问Token
     */
    public static String getJsApiToken() {
        //凭证获取(GET)
        String url = getAccessTokenUrl.replace ("APPID", appId).replace ("APPSECRET", secret);
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
     * 下载带参数的小程序二维码
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.getUnlimited.html
     * by zhengkai.blog.csdn.net
     *
     * @param inviteCode 带过去小程序的参数，一般为你的业务参数，建议是id或者number
     */
    public static String downloadMiniCode(String inviteCode) {
        Map<String, Object> paramMap = new HashMap<> ();
        paramMap.put ("scene", inviteCode);
//        paramMap.put ("page", "pages/index/index");
        paramMap.put ("auto_color", true);
//        paramMap.put ("is_hyaline", true);

        // 新生成的图片路径
        String imgFilePath = "D:\\QRCode\\miniQRCode\\" + inviteCode + ".png";
        try {
            String access_token = getJsApiToken ();
            String u = getQRCodeUrl.replace ("ACCESS_TOKEN", access_token);
            URL url = new URL (u);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection ();
            httpURLConnection.setRequestMethod ("POST");// 提交模式
            httpURLConnection.setConnectTimeout (10000);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout (10000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput (true);
            httpURLConnection.setDoInput (true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter (httpURLConnection.getOutputStream ());
            printWriter.write (JSON.toJSONString (paramMap));
            // flush输出流的缓冲
            printWriter.flush ();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream (httpURLConnection.getInputStream ());
            OutputStream os = new FileOutputStream (new File (imgFilePath));
            int len;
            //设置缓冲写入
            byte[] arr = new byte[2048];
            while ((len = bis.read (arr)) != -1) {
                os.write (arr, 0, len);
                os.flush ();
            }
            os.close ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return "/qrcode/miniQRCode/" + inviteCode + ".png";
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
