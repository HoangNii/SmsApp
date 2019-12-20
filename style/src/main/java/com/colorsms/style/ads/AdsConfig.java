package com.colorsms.style.ads;


import com.colorsms.style.BuildConfig;

public class AdsConfig {

    private static final String ADMOB_ADS_BANNER_ID_TEST = "ca-app-pub-3940256099942544/6300978111";

    public static final String FACEBOOK_ADS_BANNER_ID_TEST = "435629607326487_435630413993073";

    private static final String ADMOB_ADS_INTER_ID_TEST = "ca-app-pub-3940256099942544/1033173712";

    public static final String FACEBOOK_ADS_INTER_ID_TEST = "435629607326487_435633877326060";

    private static final String ADS_NATIVE_ID_TEST = "ca-app-pub-3940256099942544/1033173712";

    public static String getBannerId(){
        return BuildConfig.DEBUG?ADMOB_ADS_BANNER_ID_TEST: AdsConfigLoaded.get().getInAppBannerId();
    }

    public static String getNativeId(){
        return BuildConfig.DEBUG?ADMOB_ADS_BANNER_ID_TEST: AdsConfigLoaded.get().getInAppNativeId();
    }

    public static String getInterId(){
        return BuildConfig.DEBUG?ADMOB_ADS_INTER_ID_TEST: AdsConfigLoaded.get().getInAppInterId();
    }

    public static int getTimeDelay(){
        return BuildConfig.DEBUG?5000:Integer.parseInt(AdsConfigLoaded.get().getInterDelay())*1000;
    }

}
