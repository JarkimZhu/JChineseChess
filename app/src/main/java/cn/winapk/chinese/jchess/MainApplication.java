package cn.winapk.chinese.jchess;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;

import cn.winapk.sdk.WinApk;

/**
 * Created by HZY on 2018/3/6.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG)
                .setLogHeadSwitch(false).setBorderSwitch(false);
        WinApk.Options options = new WinApk.Options("p-demo");
        options.getSplashOptions().setSplashSlotId("demo-splash");
        WinApk.INSTANCE.init(this, options, null);
    }
}
