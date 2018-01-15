package com.margin.easypay.wxpay;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by margin on 2018/1/15.
 * 微信支付管理类
 */

public class WXPayManager {
    private static WXPayManager sWXPayManager;
    private String mAppKey;
    private OnWXPayResult mOnWXPayResult;
    private WXChargeEntity mChargeEntity;
    private IWXAPI mWXAPI;

    private WXPayManager() {

    }

    public static WXPayManager getWXPayManager() {
        if (sWXPayManager == null) {
            synchronized (WXPayManager.class) {
                if (sWXPayManager == null) {
                    sWXPayManager = new WXPayManager();
                }
            }
        }
        return sWXPayManager;
    }

    public boolean isInit() {
        return TextUtils.isEmpty(mAppKey);
    }

    public void init(String appKey) {
        mAppKey = appKey;
    }

    public void pay(Activity activity, WXChargeEntity chargeEntity, OnWXPayResult onWXPayResult) {
        mOnWXPayResult = onWXPayResult;
        mChargeEntity = chargeEntity;
        openWXPay(activity, mAppKey);
    }

    private void openWXPay(Activity activity, String key) {
        mWXAPI = WXAPIFactory.createWXAPI(activity, null);
        mWXAPI.registerApp(key);
        if (!mWXAPI.isWXAppInstalled() || !mWXAPI.isWXAppSupportAPI() || mChargeEntity == null) {
            if (mOnWXPayResult != null) {
                mOnWXPayResult.onFail();
            }
            return;
        }
        PayReq payReq = new PayReq();
        payReq.appId = mChargeEntity.getAppId();
        payReq.partnerId = mChargeEntity.getPartnerId();
        payReq.prepayId = mChargeEntity.getPrepayId();
        payReq.packageValue = mChargeEntity.getPackageValue();
        payReq.nonceStr = mChargeEntity.getNonceStr();
        payReq.timeStamp = mChargeEntity.getTimeStamp();
        payReq.sign = mChargeEntity.getSign();
        mWXAPI.sendReq(payReq);
    }

    void handleIntent(Intent intent, EntryActivity entryActivity) {
        if (mWXAPI!= null) {
            boolean success = mWXAPI.handleIntent(intent, entryActivity);
            if (!success) {
                entryActivity.finish();
            }
        }
    }

    public void release() {
        if (mWXAPI != null) {
            mWXAPI.unregisterApp();
            mWXAPI = null;
        }
        mOnWXPayResult = null;
        mChargeEntity = null;
    }

    OnWXPayResult getOnWXPayResult() {
        return mOnWXPayResult;
    }
}
