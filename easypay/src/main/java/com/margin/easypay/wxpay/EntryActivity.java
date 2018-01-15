package com.margin.easypay.wxpay;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 微信支付回调界面
 * 注意,必须设置该界面为透明模式
 */
public class EntryActivity extends Activity implements IWXAPIEventHandler {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WXPayManager.getWXPayManager().handleIntent(getIntent(), this);
    }

    @Override
    protected void onDestroy() {
        WXPayManager.getWXPayManager().release();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WXPayManager.getWXPayManager().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        if(resp.getType() != ConstantsAPI.COMMAND_PAY_BY_WX){
            return;
        }
        OnWXPayResult onWXPayResult = WXPayManager.getWXPayManager().getOnWXPayResult();
        if (onWXPayResult == null || !onWXPayResult.onResult(resp)) {
            String result;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "支付成功";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "取消支付";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "没有权限";
                    break;
                case BaseResp.ErrCode.ERR_UNSUPPORT:
                    result = "不支持";
                    break;
                default:
                    result = "未知错误";
                    break;
            }
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
