package com.colorsms.style.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

public class MyAds {
    public static void initInterAds(Context context){
        if(AdsConfigLoaded.get().getInShowInter().equals("0")){
            return;
        }
        if(AdsConfigLoaded.get().getInAppPlatForm().contains("facebook")){
            MyFacebookAdsController.initInterstitialAds(context);
        }else {
            MyAdmobController.initInterstitialAds(context);
        }
    }

    public static boolean isInterLoaded(){
        if(AdsConfigLoaded.get().getInAppPlatForm().contains("facebook")){
            return MyFacebookAdsController.isInterLoaded();
        }else {
            return MyAdmobController.isInterLoaded();
        }
    }

    public static void initBannerIds(Activity context){

        if(AdsConfigLoaded.get().getInShowBanner().equals("0")){
            return;
        }

        if(AdsConfigLoaded.get().getInAppPlatForm().contains("facebook")){
            MyFacebookAdsController.initBannerAds(context);
        }else {
            MyAdmobController.initBannerAds(context);
        }
    }

    public static void initBannerChatIds(Activity context){

        if(AdsConfigLoaded.get().getInShowBanner().equals("0")){
            return;
        }

        if(AdsConfigLoaded.get().getShowBannerChat().equals("0")){
            return;
        }

        if(AdsConfigLoaded.get().getInAppPlatForm().contains("facebook")){
            MyFacebookAdsController.initBannerAds(context);
        }else {
            MyAdmobController.initBannerAds(context);
        }
    }
    public static void initBannerReport(Activity context){

        if(AdsConfigLoaded.get().getInShowBanner().equals("0")){
            return;
        }

        if(AdsConfigLoaded.get().getInAppPlatForm().contains("facebook")){
            MyFacebookAdsController.initBannerReport(context);
        }else {
            MyAdmobController.initBannerReport(context);
        }
    }

    public static void initBannerReport(Dialog dialog){

        if(AdsConfigLoaded.get().getInShowBanner().equals("0")){
            return;
        }

        if(AdsConfigLoaded.get().getInAppPlatForm().contains("facebook")){
            MyFacebookAdsController.initBannerReport(dialog);
        }else {
            MyAdmobController.initBannerReport(dialog);
        }
    }

    public static void showInterFull(Context context,Callback callback){
        if(AdsConfigLoaded.get().getInAppPlatForm().contains("facebook")){
            MyFacebookAdsController.showAdsFullBeforeDoAction(context,callback);
        }else {
            MyAdmobController.showAdsFullBeforeDoAction(context,callback);
        }
    }

    public static void showInterFullNow(Context context,Callback callback){
        if(AdsConfigLoaded.get().getInAppPlatForm().contains("facebook")){
            MyFacebookAdsController.showAdsFullNow(context,callback);
        }else {
            MyAdmobController.showAdsFullNow(context,callback);
        }
    }
}
