package com.margin.easypay;

import android.app.Activity;

import com.margin.easypay.alipay.AlipayManager;
import com.margin.easypay.alipay.OnAlipayResult;
import com.margin.easypay.wxpay.OnWXPayResult;
import com.margin.easypay.wxpay.WXChargeEntity;
import com.margin.easypay.wxpay.WXPayManager;

/**
 * Created by margin on 2018/1/15.
 * 支付管理类
 */

public class PaymentManager {
    public static void alipay(Activity activity, String orderInfo, OnAlipayResult onAlipayResult) {
        AlipayManager.getAlipayManager().pay(activity, orderInfo, onAlipayResult);
    }

    public static void initWxpay(String appKey) {
        WXPayManager.getWXPayManager().init(appKey);
    }

    public static void wxpay(Activity activity, WXChargeEntity chargeEntity, OnWXPayResult onWXPayResult) {
        if (WXPayManager.getWXPayManager().isInit()) {
            WXPayManager.getWXPayManager().pay(activity, chargeEntity, onWXPayResult);
        }
    }

    public static void wxpay(Activity activity, String appKey, WXChargeEntity chargeEntity, OnWXPayResult onWXPayResult) {
        if (!WXPayManager.getWXPayManager().isInit()) {
            initWxpay(appKey);
        }
        WXPayManager.getWXPayManager().pay(activity, chargeEntity, onWXPayResult);
    }

    public static void release() {
        AlipayManager.getAlipayManager().release();
        WXPayManager.getWXPayManager().release();
    }
}
