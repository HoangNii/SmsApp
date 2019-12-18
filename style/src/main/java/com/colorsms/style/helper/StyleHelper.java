package com.colorsms.style.helper;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.colorsms.style.models.StyleModel;

public class StyleHelper {

    public static class Loader{
        public static void loadBackgroundHome(final View root, final View main, final boolean isHome){
            root.post(new Runnable() {
                @Override
                public void run() {
                    StyleModel model = Style.ColorStyle.getStyleModels().get(Style.ColorStyle.getStyleId());
                    int position = isHome?Style.Background.getBackgroundHomePosition():Style.Background.getBackgroundChatPosition();
                    if(position==0){
                        Glide.with(root.getContext())
                                .load(isHome?Style.Background.getBackgroundHomeUri():Style.Background.getBackgroundChatUri())
                                .apply(new RequestOptions()
                                        .centerCrop()
                                        .override(root.getWidth(),root.getHeight()))
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        root.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });
                    }else if(position==1){
                        if(model.getId()==0){
                            root.setBackgroundColor(Style.Home.getStyleColor());
                        }else {
                            Glide.with(root.getContext())
                                    .load(model.getBackground())
                                    .apply(new RequestOptions()
                                            .centerCrop()
                                            .override(root.getWidth(),root.getHeight()))
                                    .into(new CustomTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            root.setBackground(resource);
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {

                                        }
                                    });
                        }

                    } else {
                        Glide.with(root.getContext())
                                .load(ContextCompat.getDrawable(root.getContext(),Style.Background.backgroundList[
                                        isHome?Style.Background.getBackgroundHomePosition():Style.Background.getBackgroundChatPosition()]))
                                .apply(new RequestOptions()
                                        .centerCrop()
                                        .override(root.getWidth(),root.getHeight()))
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        root.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });
                    }

                    if(position==1&&Style.ColorStyle.getStyleId()==0){
                        main.setBackgroundColor(Color.parseColor("#FAFAFA"));
                    }else main.setBackgroundColor(0);
                }
            });

        }

        public static void setAllTextView(ViewGroup parent) {
            for (int i = parent.getChildCount() - 1; i >= 0; i--) {
                final View child = parent.getChildAt(i);
                if (child instanceof ViewGroup) {
                    setAllTextView((ViewGroup) child);
                } else if (child instanceof TextView) {
                    ((TextView) child).setTypeface(Style.Font.getFontTypeFace(Style.Font.getFontFamilyPosition()));
                }
            }
        }



        public static void loadStyleNavigationView(ViewGroup parent){
            int color =  Style.Home.getStyleColor();
            for (int i = parent.getChildCount() - 1; i >= 0; i--) {
                final View child = parent.getChildAt(i);
                if (child instanceof ViewGroup) {
                    loadStyleNavigationView((ViewGroup) child);
                } else if (child instanceof TextView) {
                    ((TextView) child).setTextColor(color);
                }else if(child instanceof ImageView){
                    ((ImageView) child).setColorFilter(color);
                }
            }
        }
    }

}
