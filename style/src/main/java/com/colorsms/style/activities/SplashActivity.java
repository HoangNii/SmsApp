package com.colorsms.style.activities;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.colorsms.style.App;
import com.colorsms.style.R;
import com.colorsms.style.ads.AdsConfigLoader;
import com.colorsms.style.ads.Callback;
import com.colorsms.style.ads.MyAdmobController;
import com.colorsms.style.helper.Style;

public class SplashActivity extends AppCompatActivity {

    private int delay = 1000;
    private boolean isRunning;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.main).setBackgroundColor(Style.Home.getStyleColor());

        if(!MyAdmobController.isInterLoaded()){
            MyAdmobController.initInterstitialAds(this);
            delay = 4000;
        }

        runnable = new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(runnable);
                if(isRunning){
                    MyAdmobController.showAdsFullNow(SplashActivity.this, new Callback() {
                        @Override
                        public void callBack(Object value, int where) {
                            App.get().startMain();
                            overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                            finish();
                        }
                    });
                }else {
                    App.get().startMain();
                    overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                    finish();
                }
            }
        };
        new AdsConfigLoader(new Callback() {
            @Override
            public void callBack(Object value, int where) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,delay);
            }
        }).syn();

    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

}


