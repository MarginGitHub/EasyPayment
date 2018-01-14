package com.margin.easypay.ali;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by Margin on 2018/1/14.
 * 支付宝支付管理类
 */

public class AlipayManager {
    private static class ResultHandler extends Handler {
        private WeakReference<OnAlipayResult> mOnAlipayResult;

        public ResultHandler(OnAlipayResult mOnAlipayResult) {
            this.mOnAlipayResult = new WeakReference<>(mOnAlipayResult);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            OnAlipayResult onAlipayResult = mOnAlipayResult.get();
            if (result != null && onAlipayResult != null) {
                onAlipayResult.onResult(result);
            }
        }
    }

    private static class AlipayThread extends Thread {
        private String orderInfo;

        public AlipayThread(String orderInfo) {
            this.orderInfo = orderInfo;
        }

        @Override
        public void run() {
            super.run();
        }
    }
}
