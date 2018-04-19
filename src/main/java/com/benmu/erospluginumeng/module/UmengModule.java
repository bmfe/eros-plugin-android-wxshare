package com.benmu.erospluginumeng.module;

import com.alibaba.weex.plugin.annotation.WeexModule;
import com.benmu.erospluginumeng.event.EventUmeng;
import com.benmu.framework.constant.WXEventCenter;
import com.benmu.framework.manager.ManagerFactory;
import com.benmu.framework.manager.impl.dispatcher.DispatchEventManager;
import com.benmu.framework.model.WeexEventBean;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.ArrayList;

/**
 * Created by liuyuanxiao on 2018/4/18.
 */
@WeexModule(name = "bmWXShare", lazyLoad = true)
public class UmengModule extends WXModule {

    @JSMethod
    public void initUM(String umengAppKey) {
        EventUmeng umeng = new EventUmeng();
        umeng.initUM(mWXSDKInstance.getContext(), umengAppKey);
    }

    @JSMethod
    public void initWX(String params) {
        EventUmeng umeng = new EventUmeng();
        umeng.initPlatform(mWXSDKInstance.getContext(), params);
    }

    @JSMethod
    public void share(String params, JSCallback success, JSCallback fail) {
        WeexEventBean weexEventBean = new WeexEventBean();
        weexEventBean.setKey(WXEventCenter.EVENT_SHARE);
        weexEventBean.setContext(mWXSDKInstance.getContext());
        weexEventBean.setJsParams(params);
        ArrayList<JSCallback> callbacks = new ArrayList<>();
        callbacks.add(success);
        callbacks.add(fail);
        weexEventBean.setCallbacks(callbacks);
        ManagerFactory.getManagerService(DispatchEventManager.class).getBus().post(weexEventBean);
//        if (params == null) {
//            return;
//        }
//        ShareBean shareBean = BMJsonParsManager.parseObject(params, ShareBean.class);
//        BMShareManager.getInstance().share((Activity) mWXSDKInstance.getContext(), shareBean,
//                mWXSDKInstance.getContainerView(), success, fail);

    }

    @JSMethod
    public void relayToFriend(String params, JSCallback successCallback, JSCallback
            failedCallback) {
        WeexEventBean weexEventBean = new WeexEventBean();
        weexEventBean.setKey(WXEventCenter.EVENT_RELAYTOFRIEND);
        weexEventBean.setContext(mWXSDKInstance.getContext());
        weexEventBean.setJsParams(params);
        ArrayList<JSCallback> callbacks = new ArrayList<>();
        callbacks.add(successCallback);
        callbacks.add(failedCallback);
        weexEventBean.setCallbacks(callbacks);
        ManagerFactory.getManagerService(DispatchEventManager.class).getBus().post(weexEventBean);
    }

    @JSMethod
    public void relayToCricle(String params, JSCallback successCallback, JSCallback
            failedCallback) {
        WeexEventBean weexEventBean = new WeexEventBean();
        weexEventBean.setKey(WXEventCenter.EVENT_RELAYTOCRICLE);
        weexEventBean.setContext(mWXSDKInstance.getContext());
        weexEventBean.setJsParams(params);
        ArrayList<JSCallback> callbacks = new ArrayList<>();
        callbacks.add(successCallback);
        callbacks.add(failedCallback);
        weexEventBean.setCallbacks(callbacks);
        ManagerFactory.getManagerService(DispatchEventManager.class).getBus().post(weexEventBean);
    }

    @JSMethod
    public void authLogin(JSCallback callback) {
        WeexEventBean weexEventBean = new WeexEventBean();
        weexEventBean.setKey(WXEventCenter.EVENT_WECHATLOGIN);
        weexEventBean.setContext(mWXSDKInstance.getContext());
        weexEventBean.setJscallback(callback);
        ManagerFactory.getManagerService(DispatchEventManager.class).getBus().post(weexEventBean);
    }

    /**
     * 获取是否安装WeChat
     */
    @JSMethod
    public void isInstallWXApp(JSCallback callback) {
        WeexEventBean weexEventBean = new WeexEventBean();
        weexEventBean.setKey(WXEventCenter.EVENT_ISINSTALLWXAPP);
        weexEventBean.setContext(mWXSDKInstance.getContext());
        weexEventBean.setJscallback(callback);
        ManagerFactory.getManagerService(DispatchEventManager.class).getBus().post(weexEventBean);

    }
}
