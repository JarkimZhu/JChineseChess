package cn.winapk.chinese.jchess.activity;


import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ActivityUtils;

import java.io.InputStream;

import cn.winapk.chinese.jchess.R;
import cn.winapk.chinese.jchess.game.GameConfig;
import cn.winapk.chinese.jchess.xqwlight.Position;

/**
 * Created by HZY on 2018/3/6.
 */

public class SplashActivity extends FragmentActivity {

    private static boolean mDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (mDataLoaded) {
            startGame();
        } else {
            loadBookAndStartGame();
        }
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
                runOnUiThread(() -> startGame());
            }
        }.start();
    }

    private void startGame() {
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }
}
