package cn.winapk.chinese.jchess.activity;


import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ActivityUtils;

import java.io.InputStream;
import java.util.Objects;

import cn.winapk.chinese.jchess.R;
import cn.winapk.chinese.jchess.game.GameConfig;
import cn.winapk.chinese.jchess.xqwlight.Position;
import cn.winapk.sdk.WinApk;
import cn.winapk.sdk.views.splash.WinApkSplashView;
import me.jarkimzhu.advertisement.Event;

/**
 * Created by HZY on 2018/3/6.
 */

public class SplashActivity extends FragmentActivity {

    volatile private static boolean mDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        WinApk.INSTANCE.requestAllSdkPermissions(this, null);

        String slotId = Objects.requireNonNull(WinApk.INSTANCE.getOptions()).getSplashOptions().getSplashSlotId();
        WinApk.INSTANCE.showSplash(this, Objects.requireNonNull(slotId), R.id.splash_frame_container, (s, event, o) -> {
            if (event == Event.AD_CLOSE || event == Event.AD_ERROR) {
                SplashActivity.this.runOnUiThread(() -> {
                    if (mDataLoaded) {
                        startGame();
                    }
                });
            }
        });

        loadBookAndStartGame();
    }

    private void loadBookAndStartGame() {
        new Thread() {
            @Override
            public void run() {
                try {
                    // do some loading job
                    InputStream is = getAssets().open(GameConfig.DAT_ASSETS_PATH);
                    Position.loadBook(is);
                    mDataLoaded = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        WinApkSplashView winApkSplashView = findViewById(R.id.winapk_splash);
        if (winApkSplashView.getState() == WinApkSplashView.State.CLICKED) {
            startGame();
            super.onBackPressed();
        }
    }

    private void startGame() {
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }
}
