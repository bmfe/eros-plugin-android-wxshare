package com.benmu.erospluginumeng.achiever;

import android.app.Activity;
import android.text.TextUtils;

import com.benmu.erospluginumeng.R;
import com.benmu.erospluginumeng.model.ShareInfoBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by Carry on 2018/3/6.
 */

public class ShareTextImageAchiever implements ShareAchiever {

    @Override
    public void shareAction(Activity context, SHARE_MEDIA platform, ShareInfoBean shareBean,
                            UMShareListener shareListener) {
        if (TextUtils.isEmpty(shareBean.getContent()) || TextUtils.isEmpty(shareBean.getImage())) {
             shareListener.onError(platform, new Exception("Share Arguments Exception getContent || getImage isEmpty"));
            return;
        }
        UMImage imageUrl = new UMImage(context, shareBean.getImage());
        imageUrl.setThumb(new UMImage(context, R.drawable.ic_launcher));
        new ShareAction(context).withText(shareBean.getContent()).withMedia(imageUrl).setPlatform
                (platform).setCallback(shareListener).share();

    }
}
