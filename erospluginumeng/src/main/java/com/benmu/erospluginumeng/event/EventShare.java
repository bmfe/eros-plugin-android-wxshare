package com.benmu.erospluginumeng.event;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.benmu.erospluginumeng.manager.ShareManager;
import com.benmu.erospluginumeng.model.RelayBean;
import com.benmu.erospluginumeng.utils.WeChatRelayUtil;
import com.benmu.framework.constant.WXEventCenter;
import com.benmu.framework.manager.ManagerFactory;
import com.benmu.framework.manager.impl.ParseManager;
import com.benmu.framework.model.BaseResultBean;
import com.benmu.framework.model.WeexEventBean;
import com.benmu.widget.utils.BaseCommonUtil;
import com.benmu.wxbase.EventGate;
import com.taobao.weex.bridge.JSCallback;

import java.util.ArrayList;

/**
 * Created by Carry on 2017/9/26.
 */

public class EventShare extends EventGate {
    private static int ACTION_FRIEND = 0;
    private static int ACTION_CRICLE = 1;
    private JSCallback mSuccessCallback;
    private JSCallback mFailedCallback;

    @Override
    public void perform(Context context, WeexEventBean weexEventBean, String type) {
        String params = weexEventBean.getJsParams();
        if (WXEventCenter.EVENT_SHARE.equals(type)) {
            share(context, params, weexEventBean.getCallbacks().get(0),
                    weexEventBean.getCallbacks().get(1));
        } else if (WXEventCenter.EVENT_RELAYTOFRIEND.equals(type)) {
            relayToFriend(context, params, weexEventBean.getCallbacks());
        } else if (WXEventCenter.EVENT_RELAYTOCRICLE.equals(type)) {
            relayToCricle(context, params, weexEventBean.getCallbacks());
        }
    }

    public void share(Context context, String params, JSCallback success, JSCallback fail) {
        if (context == null || TextUtils.isEmpty(params)) return;
        ShareManager shareManager = ManagerFactory.getManagerService(ShareManager.class);
        shareManager.share((Activity) context, params, success, fail);
    }

    public void relayToFriend(Context context, String params, ArrayList<JSCallback> callbacks) {
        mSuccessCallback = callbacks.get(0);
        mFailedCallback = callbacks.get(1);
        if (context == null || TextUtils.isEmpty(params)) {
            if (mFailedCallback != null) {
                mFailedCallback.invoke(new BaseResultBean(WeChatRelayUtil.ERROR_ILLEGALARGUMENT,
                        "参数有误"));
            }
            return;
        }
        //参数有误
        ParseManager parseManager = ManagerFactory.getManagerService(ParseManager.class);
        RelayBean object = parseManager.parseObject(params, RelayBean.class);
        if (object == null) {
            if (mFailedCallback != null) {
                mFailedCallback.invoke(new BaseResultBean(WeChatRelayUtil.ERROR_ILLEGALARGUMENT,
                        "参数有误"));
            }
            return;
        }

        //参数有误
        if (!WeChatRelayUtil.PLATFORM_WECHAT.equals(object.getPlatform())) {
            if (mFailedCallback != null) {
                mFailedCallback.invoke(new BaseResultBean(WeChatRelayUtil.ERROR_UNSUPPORT_PLATFORM,
                        "不支持的平台"));
            }
            return;
        }
        //不支持的平台

        //粘贴描述到剪切板
        String description = object.getDescription();

        if (!TextUtils.isEmpty(description) && !WeChatRelayUtil.MEDIA_TEXT.equals(object
                .getMediaType())) {
            BaseCommonUtil.copyString(context, object.getDescription());
            if (!TextUtils.isEmpty(object.getClipboardNotice())) {
                Toast.makeText(context, object.getClipboardNotice(), Toast
                        .LENGTH_SHORT).show();
            }
        }

        if (WeChatRelayUtil.MEDIA_TEXT.equals(object.getMediaType())) {
            WeChatRelayUtil.relayToFriend(context, object.getDescription(), null, object
                    .getMediaType(), mSuccessCallback, mFailedCallback);
        } else {

            downLoadResource(context, object, ACTION_FRIEND);
        }

    }

    private void downLoadResource(final Context context, final RelayBean bean, final int type) {

    }

    public void relayToCricle(Context context, String params, ArrayList<JSCallback> callbacks) {
        mSuccessCallback = callbacks.get(0);
        mFailedCallback = callbacks.get(1);
        if (context == null || TextUtils.isEmpty(params)) {
            if (mFailedCallback != null) {
                mFailedCallback.invoke(new BaseResultBean(WeChatRelayUtil.ERROR_ILLEGALARGUMENT,
                        "参数有误"));
            }
            return;
        }
        ParseManager parseManager = ManagerFactory.getManagerService(ParseManager.class);
        RelayBean object = parseManager.parseObject(params, RelayBean.class);
        if (object == null) {
            if (mFailedCallback != null) {
                mFailedCallback.invoke(new BaseResultBean(WeChatRelayUtil.ERROR_ILLEGALARGUMENT,
                        "参数有误"));
            }
            return;
        }
        if (!WeChatRelayUtil.PLATFORM_WECHAT.equals(object.getPlatform())) {
            if (mFailedCallback != null) {
                mFailedCallback.invoke(new BaseResultBean(WeChatRelayUtil.ERROR_UNSUPPORT_PLATFORM,
                        "不支持的平台"));
            }
            return;
        }
        //不支持的平台
        if (WeChatRelayUtil.MEDIA_VIDEO.equals(object.getMediaType())) {
            //粘贴描述到剪切板
            String description = object.getDescription();
            if (!TextUtils.isEmpty(description)) {
                BaseCommonUtil.copyString(context, object.getDescription());
                if (!TextUtils.isEmpty(object.getClipboardNotice())) {
                    Toast.makeText(context, object.getClipboardNotice(), Toast
                            .LENGTH_SHORT).show();
                }
            }
        }
        //下载视频或图片
        downLoadResource(context, object, ACTION_CRICLE);
    }
}
