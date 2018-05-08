package com.benmu.erospluginumeng.achiever;

import android.app.Activity;
import android.text.TextUtils;

import com.benmu.erospluginumeng.R;
import com.benmu.erospluginumeng.model.ShareInfoBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;

/**
 * Created by Carry on 2018/3/6.
 * 分享小程序
 */

public class ShareMiniProgramAchiever implements ShareAchiever {
    @Override
    public void shareAction(Activity context, SHARE_MEDIA platform, ShareInfoBean shareBean,
                            UMShareListener shareListener) {
        if (TextUtils.isEmpty(shareBean.getUrl())) {
            shareListener.onError(platform, new Exception("Share Arguments Exception getUrl isEmpty"));
            return;
        }
        UMMin umMin = new UMMin(shareBean.getUrl());
        umMin.setTitle(shareBean.getTitle());
        umMin.setDescription(shareBean.getContent());
        umMin.setPath(shareBean.getPath());
        umMin.setUserName(shareBean.getUserName());
        UMImage umImage;
        if (TextUtils.isEmpty(shareBean.getImage())) {
            umImage = new UMImage(context, R.drawable.ic_launcher);
        } else {
            umImage = new UMImage(context, shareBean.getImage());
        }
        umMin.setThumb(umImage);
        new ShareAction(context).withMedia(umMin).setPlatform(platform).setCallback
                (shareListener).share();
    }
}
