package com.kalvin.kvf.common.entity;

public class WeChatTicket {

    /**
     * signature : ab9305e43bd07f96dcdc298cf5ce58fb950e112b
     * appid : wx719c937ea903be65
     * jsapi_ticket : kgt8ON7yVITDhtdwci0qecnUdtwfcW0QFTKf9m0A5p4TbusnbVtAa94ZGSz0gMuIbPr5-NQGdg6AWIhQop41hA
     * url : http://nhmsfz.fanchance.com//api/v1/wechat/getSign?null
     * nonceStr : 9579e3c0-ff16-440b-a605-5deccfb4fd8b
     * timestamp : 1597040174
     */

    private String signature;
    private String jsapi_ticket;
    private String url;
    private String nonceStr;
    private String timestamp;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
