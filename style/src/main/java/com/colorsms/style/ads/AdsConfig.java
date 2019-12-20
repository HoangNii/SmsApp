package com.colorsms.style.ads;


import com.colorsms.style.BuildConfig;

public class AdsConfig {

    private static final String ADMOB_ADS_BANNER_ID_TEST = "ca-app-pub-3940256099942544/6300978111";

    private static final String ADMOB_ADS_INTER_ID_TEST = "ca-app-pub-3940256099942544/1033173712";

    private static final String ADS_NATIVE_ID_TEST = "ca-app-pub-3940256099942544/2247696110";

    public static String getBannerId(){
        return BuildConfig.DEBUG?ADMOB_ADS_BANNER_ID_TEST: AdsConfigLoaded.get().getInAppBannerId();
    }

    public static String getNativeId(){
        return BuildConfig.DEBUG?ADS_NATIVE_ID_TEST: AdsConfigLoaded.get().getInAppNativeId();
    }

    public static String getInterId(){
        return BuildConfig.DEBUG?ADMOB_ADS_INTER_ID_TEST: AdsConfigLoaded.get().getInAppInterId();
    }

    public static int getTimeDelay(){
        return BuildConfig.DEBUG?5000:Integer.parseInt(AdsConfigLoaded.get().getInterDelay())*1000;
    }

}
