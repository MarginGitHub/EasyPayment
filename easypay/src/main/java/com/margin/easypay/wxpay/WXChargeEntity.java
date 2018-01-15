package com.margin.easypay.wxpay;


import com.google.gson.annotations.SerializedName;

/**
 * Created by margin on 2018/1/3.
 * 微信支付需要的信息
 */

public class WXChargeEntity {

    /**
     * appId : wxf5wdsoepkulo9mnj
     * partnerId : 9320186655
     * prepayId : 1101000000171107fpk0yhin14adxrbl
     * nonceStr : bcdbea7453ba4b41a95e1313af34f81c
     * timeStamp : 1510021531
     * packageValue : Sign=WXPay
     * sign : FA255D826E6F8BF7AF2A5FD6EAE0CCA0
     */

    @SerializedName("appid")
    private String appId;
    @SerializedName("partnerid")
    private String partnerId;
    @SerializedName("prepayid")
    private String prepayId;
    @SerializedName("noncestr")
    private String nonceStr;
    @SerializedName("timestamp")
    private String timeStamp;
    @SerializedName("package")
    private String packageValue;
    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
