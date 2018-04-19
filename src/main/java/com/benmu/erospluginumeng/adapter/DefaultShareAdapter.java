package com.benmu.erospluginumeng.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.benmu.erospluginumeng.R;
import com.benmu.erospluginumeng.achiever.ShareImageAchiever;
import com.benmu.erospluginumeng.achiever.ShareMiniProgramAchiever;
import com.benmu.erospluginumeng.achiever.ShareMusicAchiever;
import com.benmu.erospluginumeng.achiever.SharePasteboardAchiever;
import com.benmu.erospluginumeng.achiever.ShareTextAchiever;
import com.benmu.erospluginumeng.achiever.ShareTextImageAchiever;
import com.benmu.erospluginumeng.achiever.ShareWebAchiever;
import com.benmu.erospluginumeng.model.ShareInfoBean;
import com.benmu.erospluginumeng.utils.ShareMediaType;
import com.benmu.erospluginumeng.utils.SharePlatformCatalog;
import com.benmu.erospluginumeng.utils.ShareUtil;
import com.benmu.framework.BMWXEnvironment;
import com.benmu.framework.manager.ManagerFactory;
import com.benmu.framework.manager.impl.ParseManager;
import com.benmu.framework.model.BaseResultBean;
import com.benmu.framework.utils.JsPoster;
import com.benmu.widget.utils.BaseCommonUtil;
import com.benmu.widget.view.BMGridDialog;
import com.taobao.weex.bridge.JSCallback;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carry on 2017/9/26.
 */

public class DefaultShareAdapter {
    private Activity mAct;
    private UMWeb mUMWeb;
    private SHARE_MEDIA[] mPlatforms = new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN.toSnsPlatform()
            .mPlatform,
            SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform().mPlatform};
    private JSCallback mSuccess;
    private JSCallback mFailed;
    private ProgressDialog mProgressDialog;

    public void share(Activity activity, String params, JSCallback success, JSCallback fail) {
        if (activity == null || TextUtils.isEmpty(params)) return;
        this.mAct = activity;
        this.mSuccess = success;
        this.mFailed = fail;

        ParseManager parseManager = ManagerFactory.getManagerService(ParseManager.class);

        ShareInfoBean shareBean = null;
        try {
            shareBean = parseManager.parseObject(params, ShareInfoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (shareBean == null) return;

        String platform = (String) shareBean.getPlatform();
        String type = shareBean.getShareType();

        if (SharePlatformCatalog.P_PASTEBOARD.equals(platform)) {
            //剪切板 直接剪切
            new SharePasteboardAchiever().shareAction(activity, null, shareBean, shareListener);
        } else {
            //其他平台 以分享方式判断
            if (ShareMediaType.TEXT.getKey().equals(type)) {
                //文本分享
                new ShareTextAchiever().shareAction(activity, ShareUtil.getShareMedia(platform),
                        shareBean, shareListener);
            } else if (ShareMediaType.IMAGE.getKey().equals(type)) {
                new ShareImageAchiever().shareAction(activity, ShareUtil.getShareMedia(platform),
                        shareBean, shareListener);
                //图片分享
            } else if (ShareMediaType.TEXTIMAGE.getKey().equals(type)) {
                new ShareTextImageAchiever().shareAction(activity, ShareUtil.getShareMedia
                        (platform), shareBean, shareListener);
                //图文分享
            } else if (ShareMediaType.MUSIC.getKey().equals(type)) {
                //音乐
                new ShareMusicAchiever().shareAction(activity, ShareUtil.getShareMedia(platform),
                        shareBean, shareListener);
            } else if (ShareMediaType.MINIPROGRAM.getKey().equals(type)) {
                //小程序
                new ShareMiniProgramAchiever().shareAction(activity, ShareUtil.getShareMedia
                        (platform), shareBean,shareListener);
            } else {
                //网页
                new ShareWebAchiever().shareAction(activity, ShareUtil.getShareMedia(platform),
                        shareBean, shareListener);
            }
        }

    }

    private SHARE_MEDIA getShareMedia(String platform) {
        if ("WechatSession".equals(platform)) {
            return SHARE_MEDIA.WEIXIN;
        }
        return SHARE_MEDIA.WEIXIN_CIRCLE;
    }

    private void startUmweb(ShareInfoBean shareInfo, SHARE_MEDIA mPlatform, UMShareListener
            shareListener) {
        if (!BMWXEnvironment.mPlatformConfig.getUmeng().isUmengAvailable()) {
            shareListener.onError(mPlatform, new Exception("未设置umeng三方appKey"));
            return;
        }
        mUMWeb = new UMWeb(shareInfo.getUrl());
        mUMWeb.setTitle(shareInfo.getTitle());
        mUMWeb.setDescription(shareInfo.getContent());
        String image = shareInfo.getImage();
        if (TextUtils.isEmpty(image)) {
            mUMWeb.setThumb(new UMImage(mAct, R.drawable.place_holder));
        } else {
            mUMWeb.setThumb(new UMImage(mAct, image));
        }

        new ShareAction(mAct).setPlatform(mPlatform).withMedia(mUMWeb).setCallback(shareListener)
                .share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }

            if (mSuccess != null) {
                JsPoster.postSuccess(null, mAct.getResources().getString(R.string
                        .str_share_success), mSuccess);
//                mSuccess.invoke(new BaseResultBean(0, mAct.getResources().getString(R.string
//                        .str_share_success)));
            } else {
                Toast.makeText(mAct, mAct.getResources().getString
                        (R.string.str_share_success), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            if (mFailed != null) {
                JsPoster.postFailed(mAct.getResources().getString
                        (R.string.str_share_failed), mFailed);
            } else {
                Toast.makeText(mAct, mAct.getResources().getString
                        (R.string.str_share_failed), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    };

}
