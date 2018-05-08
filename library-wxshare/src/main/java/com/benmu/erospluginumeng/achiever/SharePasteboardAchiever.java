package com.benmu.erospluginumeng.achiever;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.benmu.erospluginumeng.R;
import com.benmu.erospluginumeng.model.ShareInfoBean;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Carry on 2018/3/6.
 * 复制到粘贴板
 */

public class SharePasteboardAchiever implements ShareAchiever {

    @Override
    public void shareAction(Activity context, SHARE_MEDIA platform, ShareInfoBean shareBean,
                            UMShareListener shareListener) {
        if (TextUtils.isEmpty(shareBean.getContent())) {
            shareListener.onError(platform, new Exception("Share Arguments Exception getContent isEmpty"));
            return;
        }
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context
                .CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("text", shareBean.getContent()));
        Toast.makeText(context, context.getResources().getString(R.string.str_paste_board), Toast
                .LENGTH_SHORT).show();
        shareListener.onResult(null);
    }

}
