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
import com.colorsms.style.ads.MyAds;
import com.colorsms.style.ads.MyFacebookAdsController;
import com.colorsms.style.helper.Style;

public class SplashActivity extends AppCompatActivity {

    private int delay = 1000;
    private boolean isRunning;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.main).setBackgroundColor(Style.Home.getStyleColor());

        if(!MyAds.isInterLoaded()){
            MyAds.initInterAds(this);
            delay = 4000;
        }

        new AdsConfigLoader(new Callback() {
            @Override
            public void callBack(Object value, int where) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isRunning){
                            MyAds.showInterFull(SplashActivity.this, new Callback() {
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
                },delay);

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


