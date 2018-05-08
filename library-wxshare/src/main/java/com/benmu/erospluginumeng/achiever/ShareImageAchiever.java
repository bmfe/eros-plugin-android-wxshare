package com.benmu.erospluginumeng.achiever;

import android.app.Activity;
import android.text.TextUtils;

import com.benmu.erospluginumeng.model.ShareInfoBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by Carry on 2018/3/6.
 */

public class ShareImageAchiever implements ShareAchiever {
    @Override
    public void shareAction(Activity context, SHARE_MEDIA platform, ShareInfoBean shareBean,
                            UMShareListener shareListener) {
        if (TextUtils.isEmpty(shareBean.getImage())) {
            shareListener.onError(platform, new Exception("Share Arguments Exception Image isEmpty"));
            return;
        }
        UMImage imageUrl = new UMImage(context, shareBean.getImage());
        imageUrl.setThumb(new UMImage(context,shareBean.getImage()));
        new ShareAction(context).withMedia(imageUrl).setPlatform(platform).setCallback
                (shareListener).share();
    }
}
