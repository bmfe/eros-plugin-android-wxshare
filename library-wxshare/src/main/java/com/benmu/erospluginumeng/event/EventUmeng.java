package com.benmu.erospluginumeng.event;


import android.content.Context;
import android.text.TextUtils;

import com.benmu.erospluginumeng.model.UmengPlagformBean;
import com.benmu.framework.manager.ManagerFactory;
import com.benmu.framework.manager.impl.ParseManager;
import com.benmu.framework.utils.DebugableUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by liuyuanxiao on 2018/4/18.
 */

public class EventUmeng {

    public void initUM(Context context, String androidAppKey) {
        if (!TextUtils.isEmpty(androidAppKey)) {
            UMConfigure.init(context, androidAppKey, "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
            MobclickAgent.setDebugMode(DebugableUtil.isDebug());
            MobclickAgent.openActivityDurationTrack(false);
            MobclickAgent.setCatchUncaughtExceptions(!DebugableUtil.isDebug());
            MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
            UMShareAPI.get(context);
        }
    }

    public void initPlatform(Context context, String params) {
        UmengPlagformBean bean = ManagerFactory.getManagerService(ParseManager.class).parseObject
                (params, UmengPlagformBean.class);

        PlatformConfig.setWeixin(bean.getAppKey(), bean.getAppSecret());

    }


}
