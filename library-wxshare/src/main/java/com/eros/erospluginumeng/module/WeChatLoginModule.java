package com.eros.erospluginumeng.module;

import com.alibaba.weex.plugin.annotation.WeexModule;
import com.eros.framework.constant.WXEventCenter;
import com.eros.framework.manager.ManagerFactory;
import com.eros.framework.manager.impl.dispatcher.DispatchEventManager;
import com.eros.framework.model.WeexEventBean;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * WeChat 登录 交互Module
 */
@WeexModule(name = "bmAuth", lazyLoad = true)
public class WeChatLoginModule extends WXModule {

    @JSMethod
    public void wechat(String parameterString, JSCallback callback) {
        WeexEventBean weexEventBean = new WeexEventBean();
        weexEventBean.setKey(WXEventCenter.EVENT_WECHATLOGIN);
        weexEventBean.setContext(mWXSDKInstance.getContext());
        weexEventBean.setJscallback(callback);
        weexEventBean.setJsParams(parameterString);
        ManagerFactory.getManagerService(DispatchEventManager.class).getBus().post(weexEventBean);
    }
}
