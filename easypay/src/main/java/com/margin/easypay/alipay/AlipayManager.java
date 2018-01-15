package com.margin.easypay.alipay;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import java.lang.ref.WeakReference;

/**
 * Created by Margin on 2018/1/14.
 * 支付宝支付管理类
 */

public class AlipayManager {
    private static AlipayManager sAlipayManager;
    private ResultHandler mHandler;

    private AlipayManager() {

    }

    public static AlipayManager getAlipayManager() {
        if (sAlipayManager == null) {
            synchronized (AlipayManager.class) {
                if (sAlipayManager == null) {
                    sAlipayManager = new AlipayManager();
                    sAlipayManager.mHandler = new ResultHandler();
                }
            }
        }
        return sAlipayManager;
    }

    public void pay(Activity activity, String orderInfo, OnAlipayResult onAlipayResult) {
        mHandler.setOnAlipayResult(onAlipayResult);
        new AlipayThread(activity, orderInfo, mHandler).start();
    }

    public void release() {
        mHandler.setOnAlipayResult(null);
    }

    private static class ResultHandler extends Handler {
        private WeakReference<OnAlipayResult> mOnAlipayResult;

        ResultHandler() {

        }

        void setOnAlipayResult(OnAlipayResult onAlipayResult) {
            mOnAlipayResult = new WeakReference<>(onAlipayResult);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mOnAlipayResult == null) {
                return;
            }
            OnAlipayResult onAlipayResult = mOnAlipayResult.get();
            if (onAlipayResult == null) {
                return;
            }
            String result = (String) msg.obj;
            if (!TextUtils.isEmpty(result)) {
                onAlipayResult.onResult(result);
            }
        }
    }

    private static class AlipayThread extends Thread {
        private String mOrderInfo;
        private WeakReference<Activity> mActivity;
        private ResultHandler mResultHandler;

        AlipayThread(Activity activity, String orderInfo, ResultHandler handler) {
            mOrderInfo = orderInfo;
            mActivity= new WeakReference<>(activity);
            mResultHandler = handler;
        }

        @Override
        public void run() {
            super.run();
            Activity activity = mActivity.get();
            if (TextUtils.isEmpty(mOrderInfo) || activity == null) {
                return;
            }
            PayTask payTask = new PayTask(activity);
            String result = payTask.pay(mOrderInfo, true);
            Message message = mResultHandler.obtainMessage();
            message.obj = result;
            mResultHandler.sendMessage(message);
        }
    }
}
