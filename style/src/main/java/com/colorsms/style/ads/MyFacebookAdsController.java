package com.colorsms.style.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.colorsms.style.R;
import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
public class MyFacebookAdsController {

    private static InterstitialAd mInterstitialAd;

    private static int flagQC = 1;

    public static void initInterstitialAds(final Context ac) {
        // hiển thị quảng cáo
        // quảng cáo full màn hình
        if (mInterstitialAd == null)
            mInterstitialAd = new InterstitialAd(ac,AdsConfig.getInterId());


        mInterstitialAd.setAdListener(new AbstractAdListener() {
            @Override
            public void onInterstitialDismissed(Ad ad) {
                requestNewInterstitial();
            }
            @Override
            public void onError(Ad ad, AdError error) {
                super.onError(ad, error);
            }

        });

        if(!isInterLoaded())
            requestNewInterstitial();

    }

    private static void requestNewInterstitial() {
        mInterstitialAd.loadAd();
    }


    public static boolean isInterLoaded(){
        return mInterstitialAd!=null&&mInterstitialAd.isAdLoaded();
    }

    public static void showAdsFullBeforeDoAction(final Context context, final Callback callback) {

        if (mInterstitialAd == null) {
            callback.callBack(0, 0);
            return;
        }

        if (flagQC == 1) {
            if (mInterstitialAd.isAdLoaded()) {
                mInterstitialAd.setAdListener(new AbstractAdListener() {
                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        requestNewInterstitial();
                        flagQC = 0;
                        handler.removeCallbacks(runnable);
                        handler.postDelayed(runnable,AdsConfig.getTimeDelay());
                        callback.callBack(0, 0);
                    }
                    @Override
                    public void onError(Ad ad, AdError error) {
                        super.onError(ad, error);
                    }

                });
                mInterstitialAd.show();


            } else {
                callback.callBack(0, 0);

                requestNewInterstitial();
            }

        } else {
            callback.callBack(0, 0);

            requestNewInterstitial();
        }
    }

    public static void showAdsFullNow(final Context context, final Callback callback) {

        if (mInterstitialAd == null) {
            callback.callBack(0, 0);
            return;
        }
        if (mInterstitialAd.isAdLoaded()) {
            mInterstitialAd.setAdListener(new AbstractAdListener() {
                @Override
                public void onInterstitialDismissed(Ad ad) {
                    super.onInterstitialDismissed(ad);
                    requestNewInterstitial();
                    flagQC = 0;
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable,AdsConfig.getTimeDelay());
                    callback.callBack(0, 0);
                }

                @Override
                public void onError(Ad ad, AdError error) {
                    super.onError(ad, error);
                }

            });

            mInterstitialAd.show();


        } else {
            callback.callBack(0, 0);

            requestNewInterstitial();
        }
    }

    public static void initBannerAds(final Activity ctx) {

        final AdView adView = new AdView(ctx,AdsConfig.getBannerId(), AdSize.BANNER_HEIGHT_50);


        final RelativeLayout adViewContainer = ctx.findViewById(R.id.adView_container);
        adViewContainer.removeAllViews();

        try {
            adViewContainer.addView(adView);
        } catch (Exception e) {
            e.getMessage();
        }

        adView.setAdListener(new AbstractAdListener() {
            @Override
            public void onAdLoaded(Ad ad) {
                super.onAdLoaded(ad);
                adViewContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Ad ad, AdError error) {
                super.onError(ad, error);
                adViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                super.onInterstitialDismissed(ad);
                adView.loadAd();
            }
        });
        adView.loadAd();

    }

    public static void initBannerReport(final Dialog ctx) {

        final AdView adView = new AdView(ctx.getContext(),AdsConfig.getBannerId(), AdSize.BANNER_HEIGHT_90);


        final RelativeLayout adViewContainer = ctx.findViewById(R.id.adView_container);
        adViewContainer.removeAllViews();

        try {
            adViewContainer.addView(adView);
        } catch (Exception e) {
            e.getMessage();
        }

        adView.setAdListener(new AbstractAdListener() {
            @Override
            public void onAdLoaded(Ad ad) {
                super.onAdLoaded(ad);
                adViewContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Ad ad, AdError error) {
                super.onError(ad, error);
                adViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                super.onInterstitialDismissed(ad);
                adView.loadAd();
            }
        });
        adView.loadAd();

    }

    public static void initBannerReport(final Activity ctx) {

        final AdView adView = new AdView(ctx,AdsConfig.getBannerId(), AdSize.BANNER_HEIGHT_90);


        final RelativeLayout adViewContainer = ctx.findViewById(R.id.adView_container);
        adViewContainer.removeAllViews();

        try {
            adViewContainer.addView(adView);
        } catch (Exception e) {
            e.getMessage();
        }

        adView.setAdListener(new AbstractAdListener() {
            @Override
            public void onAdLoaded(Ad ad) {
                super.onAdLoaded(ad);
                adViewContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Ad ad, AdError error) {
                super.onError(ad, error);
                adViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                super.onInterstitialDismissed(ad);
                adView.loadAd();
            }
        });
        adView.loadAd();

    }

    private static Handler handler = new Handler();
    private  static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            flagQC = 1;
        }
    };
}
