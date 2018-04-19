package com.benmu.erospluginumeng.achiever;

import android.app.Activity;
import android.text.TextUtils;

import com.benmu.erospluginumeng.model.ShareInfoBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Carry on 2018/3/6.
 */

public class ShareTextAchiever implements ShareAchiever {

    @Override
    public void shareAction(Activity context, SHARE_MEDIA platform, ShareInfoBean shareBean,
                            UMShareListener shareListener) {
        if (TextUtils.isEmpty(shareBean.getContent())) {
            shareListener.onError(platform, new Exception("Share Arguments Exception getContent isEmpty"));
            return;
        }
        new ShareAction(context).withText(shareBean.getContent()).setPlatform(platform).setCallback
                (shareListener).share();
    }
}
