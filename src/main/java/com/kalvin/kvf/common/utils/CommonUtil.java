package com.kalvin.kvf.common.utils;

import com.kalvin.kvf.common.entity.MyX509TrustManager;
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

public class CommonUtil {
//    /**
//     * 获取接口访问凭证
//     *
//     * @param appid 凭证
//     * @param appsecret 密钥
//     * @return
//     */
//    public  static  String getToken(String appid, String appsecret) {
//        //凭证获取(GET)
//        String token_url =  "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET" ;
//        String requestUrl = token_url.replace( "APPID" , appid).replace( "APPSECRET" , appsecret);
//        // 发起GET请求获取凭证
//        JSONObject jsonObject = JSONObject.fromObject (httpsRequest(requestUrl,  "GET" ,  null ));
//        String access_token =  null ;
//        if  ( null  != jsonObject) {
//            try  {
//                access_token = jsonObject.getString( "access_token" );
//            }  catch  (JSONException e) {
//                // 获取token失败
//            }
//        }
//        return  access_token;
//    }
//
//    /**
//     * 调用微信JS接口的临时票据
//     *
//     * @param access_token 接口访问凭证
//     * @return
//     */
//    public  static  String getJsApiTicket(String access_token) {
//        String url =  "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi" ;
//        String requestUrl = url.replace( "ACCESS_TOKEN" , access_token);
//        // 发起GET请求获取凭证
//        JSONObject jsonObject = JSONObject.fromObject (httpsRequest(requestUrl,  "GET" ,  null ));
//        String ticket =  null ;
//        if  ( null  != jsonObject) {
//            try  {
//                ticket = jsonObject.getString( "ticket" );
//            }  catch  (JSONException e) {
//                // 获取token失败
//            }
//        }
//        return  ticket;
//    }
//
//    /**
//     * get或者post请求
//     *
//     * @param requestUrl
//     * @param requestMethod GET or POST 需要大写*
//     * @param outputStr
//     * @return
//     * @author Shen
//     */
//    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
//        StringBuffer buffer = new StringBuffer ();
//        try {
//            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
//            TrustManager[] tm = {new MyX509TrustManager ()};
//            SSLContext sslContext = SSLContext.getInstance ("SSL", "SunJSSE");
//            sslContext.init (null, tm, new java.security.SecureRandom ());
//            // 从上述SSLContext对象中得到SSLSocketFactory对象
//            SSLSocketFactory ssf = sslContext.getSocketFactory ();
//            URL url = new URL (requestUrl);
//            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection ();
//            httpUrlConn.setSSLSocketFactory (ssf);
//            httpUrlConn.setDoOutput (true);
//            httpUrlConn.setDoInput (true);
//            httpUrlConn.setUseCaches (false);
//            // 设置请求方式（GET/POST）
//            httpUrlConn.setRequestMethod (requestMethod);
//            if ("GET".equalsIgnoreCase (requestMethod)) {
//                httpUrlConn.connect ();
//            }
//            // 当有数据需要提交时
//            if (null != outputStr) {
//                OutputStream outputStream = httpUrlConn.getOutputStream ();
//                // 注意编码格式，防止中文乱码
//                outputStream.write (outputStr.getBytes ("UTF-8"));
//                outputStream.close ();
//            }
//            // 将返回的输入流转换成字符串
//            InputStream inputStream = httpUrlConn.getInputStream ();
//            InputStreamReader inputStreamReader = new InputStreamReader (inputStream, "utf-8");
//            BufferedReader bufferedReader = new BufferedReader (inputStreamReader);
//            String str = null;
//            while ((str = bufferedReader.readLine ()) != null) {
//                buffer.append (str);
//            }
//            bufferedReader.close ();
//            inputStreamReader.close ();
//            // 释放资源
//            inputStream.close ();
//            inputStream = null;
//            httpUrlConn.disconnect ();
//        } catch (ConnectException ce) {
//            System.out.println ("Weixin server connection timed out." + ce.getMessage ());
//        } catch (Exception e) {
//            System.out.println ("https request error:{}" + e.getMessage ());
//        }
//        return buffer.toString ();
//    }
}
