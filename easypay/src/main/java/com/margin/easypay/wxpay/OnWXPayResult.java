package com.margin.easypay.wxpay;

import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Created by margin on 2018/1/15.
 * 微信支付结果回调
 */

public interface OnWXPayResult {
    boolean onResult(BaseResp resp);
    void onFail();
}
