package com.margin.easypay.ali;

/**
 * Created by Margin on 2018/1/14.
 * 支付宝支付结果回调
 */

public interface OnAlipayResult {
    /**
     * 支付结果
     * @param result
     */
    void onResult(String result);

    /**
     * 用户手机没有安装支付宝的回调
     */
    void onFail();
}
