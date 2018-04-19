package com.benmu.erospluginumeng.utils;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Carry on 2018/3/5.
 */

public class ShareUtil {

    public static SHARE_MEDIA getShareMedia(String platform) {
        switch (platform) {
            case SharePlatformCatalog.P_WECHATSESSION:
                return SHARE_MEDIA.WEIXIN;
            case SharePlatformCatalog.P_WECHATTIMELINE:
                return SHARE_MEDIA.WEIXIN_CIRCLE;

        }
        return null;
    }

}
