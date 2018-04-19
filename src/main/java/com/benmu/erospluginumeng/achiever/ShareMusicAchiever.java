package com.benmu.erospluginumeng.achiever;

import android.app.Activity;
import android.text.TextUtils;

import com.benmu.erospluginumeng.R;
import com.benmu.erospluginumeng.model.ShareInfoBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMusic;

/**
 * Created by Carry on 2018/3/6.
 */

public class ShareMusicAchiever implements ShareAchiever {

    @Override
    public void shareAction(Activity context, SHARE_MEDIA platform, ShareInfoBean shareBean,
                            UMShareListener shareListener) {
        if (TextUtils.isEmpty(shareBean.getUrl())) {
           shareListener.onError(platform, new Exception("Share Arguments Exception getUrl isEmpty"));
            return;
        }
        UMusic uMusic = new UMusic(shareBean.getUrl());
        uMusic.setTitle(shareBean.getTitle());
        uMusic.setDescription(shareBean.getContent());
        uMusic.setmTargetUrl(shareBean.getUrl());
        UMImage thumb;
        if (TextUtils.isEmpty(shareBean.getImage())) {
            thumb = new UMImage(context, R.drawable.ic_launcher);
        } else {
            thumb = new UMImage(context, shareBean.getImage());
        }
        uMusic.setThumb(thumb);
        new ShareAction(context).withMedia(uMusic).setPlatform(platform).setCallback
                (shareListener).share();
    }
}
