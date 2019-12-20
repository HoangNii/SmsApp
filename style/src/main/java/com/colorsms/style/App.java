package com.colorsms.style;

import android.app.Application;
import android.util.Log;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryConfig;

public abstract class App extends Application {

    private static App app;

    public static App get(){
        return app;
    }

    public static void setApp(App app) {
        App.app = app;
    }

    public abstract void restart();


    public abstract void start(String messId);

    @Override
    public void onCreate() {
        super.onCreate();


        new FlurryAgent.Builder().build(this, "TFXXRV4PWKQT4KRMG8S2");

        AudienceNetworkAds.initialize(this);

        if (BuildConfig.DEBUG) {
            AdSettings.setDebugBuild(true);
        }
    }

    public abstract void startMain();
}
