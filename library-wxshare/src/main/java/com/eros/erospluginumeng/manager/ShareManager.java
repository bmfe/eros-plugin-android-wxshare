package com.eros.erospluginumeng.manager;

import android.app.Activity;

import com.eros.erospluginumeng.adapter.DefaultShareAdapter;
import com.eros.framework.manager.Manager;
import com.taobao.weex.bridge.JSCallback;

/**
 * Created by Carry on 2017/9/26.
 */

public class ShareManager extends Manager {

    public void share(Activity activity, String params, JSCallback success, JSCallback fail) {
        new DefaultShareAdapter().share(activity, params,success,fail);
    }
}
