package com.colorsms.style.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colorsms.style.R;
import com.colorsms.style.helper.Style;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class MyAdmobController {

    private static InterstitialAd mInterstitialAd;

    private static int flagQC = 1;

    public static void initInterstitialAds(final Context ac) {
        // hiển thị quảng cáo
        // quảng cáo full màn hình
        if (mInterstitialAd == null)
            mInterstitialAd = new InterstitialAd(ac);

        String adUnitId = mInterstitialAd.getAdUnitId();

        if (adUnitId == null || adUnitId.equals("")) {

            mInterstitialAd.setAdUnitId(AdsConfig.getInterId());

        }

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                // abc
            }

        });

        if(!isInterLoaded())
            requestNewInterstitial();

    }

    public static void clearInter(){
        mInterstitialAd = null;
    }

    public static boolean isInterLoaded(){
        return mInterstitialAd!=null&&mInterstitialAd.isLoaded();
    }

    private static void requestNewInterstitial() {
        AdRequest adRequest = MyAdmobController.getAdRequest();
        if (mInterstitialAd != null && !mInterstitialAd.isLoaded())
            mInterstitialAd.loadAd(adRequest);

    }

    private static AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }


    public static void showAdsFullBeforeDoAction(final Context context, final Callback callback) {

        if (mInterstitialAd == null) {
            callback.callBack(0, 0);
            return;
        }

        if (flagQC == 1) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        requestNewInterstitial();
                        flagQC = 0;
                        handler.removeCallbacks(runnable);
                        handler.postDelayed(runnable,AdsConfig.getTimeDelay());
                        callback.callBack(0, 0);
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();

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
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    flagQC = 0;
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable,AdsConfig.getTimeDelay());
                    callback.callBack(0, 0);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();

                }
            });

            mInterstitialAd.show();


        } else {
            callback.callBack(0, 0);

            requestNewInterstitial();
        }
    }

    private static AdSize getAdSize(Activity context) {
        // Step 3 - Determine the screen width (less decorations) to use for the ad width.
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        //you can also pass your selected width here in dp
        int adWidth = (int) (widthPixels / density);
        //return the optimal size depends on your orientation (landscape or portrait)
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }
    public static void initBannerAds(final Activity ctx) {

        final AdView mAdViewBanner = new AdView(ctx);
        String adUnitId = mAdViewBanner.getAdUnitId();

        if (adUnitId == null || adUnitId.equals("")) {
            mAdViewBanner.setAdSize(getAdSize(ctx));
            mAdViewBanner.setAdUnitId(AdsConfig.getBannerId());
        }


        final RelativeLayout adViewContainer = ctx.findViewById(R.id.adView_container);
        adViewContainer.removeAllViews();

        try {
            adViewContainer.addView(mAdViewBanner);
        } catch (Exception e) {
            e.getMessage();
        }


        final AdRequest adRequest = getAdRequest();

        mAdViewBanner.loadAd(adRequest);
        mAdViewBanner.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                adViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adViewContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adViewContainer.setVisibility(View.GONE);
                mAdViewBanner.loadAd(adRequest);
            }
        });
    }

    public static void initBannerReport(final Dialog ctx) {

        final AdView mAdViewBanner = new AdView(ctx.getContext());
        String adUnitId = mAdViewBanner.getAdUnitId();

        if (adUnitId == null || adUnitId.equals("")) {
            mAdViewBanner.setAdSize(com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE);
            mAdViewBanner.setAdUnitId(AdsConfig.getBannerId());
        }


        final RelativeLayout adViewContainer = ctx.findViewById(R.id.adView_container);
        adViewContainer.removeAllViews();

        try {
            adViewContainer.addView(mAdViewBanner);
        } catch (Exception e) {
            e.getMessage();
        }


        final AdRequest adRequest = getAdRequest();

        mAdViewBanner.loadAd(adRequest);
        mAdViewBanner.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                adViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adViewContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adViewContainer.setVisibility(View.GONE);
                mAdViewBanner.loadAd(adRequest);
            }
        });
    }
    public static void initBannerReport(final Activity ctx) {

        final AdView mAdViewBanner = new AdView(ctx);
        String adUnitId = mAdViewBanner.getAdUnitId();

        if (adUnitId == null || adUnitId.equals("")) {
            mAdViewBanner.setAdSize(com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE);
            mAdViewBanner.setAdUnitId(AdsConfig.getBannerId());
        }


        final RelativeLayout adViewContainer = ctx.findViewById(R.id.adView_container);
        adViewContainer.removeAllViews();

        try {
            adViewContainer.addView(mAdViewBanner);
        } catch (Exception e) {
            e.getMessage();
        }


        final AdRequest adRequest = getAdRequest();

        mAdViewBanner.loadAd(adRequest);
        mAdViewBanner.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                adViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adViewContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adViewContainer.setVisibility(View.GONE);
                mAdViewBanner.loadAd(adRequest);
            }
        });
    }

    public static void initNativeView(final View view){
        AdLoader adLoader;
        final UnifiedNativeAdView adView = view.findViewById(R.id.ad_view);
        AdLoader.Builder builder = new AdLoader.Builder(view.getContext(),AdsConfig.getNativeId());
        adLoader = builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                //            adView.setMediaView(adView.findViewById(R.id.ad_media));
                adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
                adView.setBodyView(adView.findViewById(R.id.ad_body));
                adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
                adView.setIconView(adView.findViewById(R.id.ad_icon));
                ((TextView) adView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
                ((TextView) adView.getHeadlineView()).setTextColor(Style.Background.getHomeTextColor());
                ((TextView) adView.getBodyView()).setText(unifiedNativeAd.getBody());
                ((TextView) adView.getBodyView()).setTextColor(Style.Background.getHomeTextColor());
                ((TextView) adView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
                ((TextView) adView.getCallToActionView()).setTextColor(Style.Home.getStyleColor());

                NativeAd.Image icon = unifiedNativeAd.getIcon();

                if (icon == null) {
                    adView.getIconView().setVisibility(View.INVISIBLE);
                } else {
                    ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
                    adView.getIconView().setVisibility(View.VISIBLE);
                }

                // Assign native ad object to the native view.
                adView.setNativeAd(unifiedNativeAd);
            }
        }).withAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                view.setVisibility(View.VISIBLE);
            }
        }).build();

        adLoader.loadAd(getAdRequest());
    }

    private static Handler handler = new Handler();
    private  static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            flagQC = 1;
        }
    };

}
