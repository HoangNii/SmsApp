package com.colorsms.style.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.colorsms.style.App;
import com.colorsms.style.R;
import com.colorsms.style.adapters.ThemeStyleChatPreviewAdapter;
import com.colorsms.style.adapters.ThemeStyleSmsPreviewAdapter;
import com.colorsms.style.helper.Style;
import com.colorsms.style.models.StyleModel;
import com.colorsms.style.views.CircleIndicator;

public class ThemeStylePreviewActivity extends AppCompatActivity {

    private StyleModel model;

    public static void startPreview(Activity context, int position){
        Intent intent = new Intent(context,ThemeStylePreviewActivity.class);
        intent.putExtra("position",position);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_style_preview);

        model = Style.ColorStyle.getStyleModels().get(getIntent().getIntExtra("position",0));

        initToolbar();

        initBackground();

        setupPager();

        initBottom();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
    }

    private void initBottom() {
        Button btnApply = findViewById(R.id.bt_apply);
        if(model.getId()== Style.ColorStyle.getStyleId()){
            btnApply.setText("Current");
            btnApply.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        }else {
            btnApply.setText("Apply");
            btnApply.getBackground().setColorFilter(Color.parseColor("#e03a5e"), PorterDuff.Mode.SRC_IN);
            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Style.ColorStyle.setStyleId(model.getId());

                    //Home
                    Style.Home.setStyleColor(model.getStyleColor());
                    Style.Home.setHomeTitleColor(model.getTitleColor());

                    //
                    Style.Font.setFontFamilyPosition(0);
                    Style.Font.setFontSize(14);

                    //
                    Style.Background.setBackgroundBlackHomeAlpha(0);
                    Style.Background.setBackgroundHomePosition(1);
                    Style.Background.setBackgroundChatPosition(1);
                    Style.Background.setHomeTextColor(model.getSmsHomeColor());

                    Toast.makeText(ThemeStylePreviewActivity.this, "Done!", Toast.LENGTH_SHORT).show();

                    App.get().restart();
                }
            });
        }
    }

    private void setupPager() {
        ViewPager pager = findViewById(R.id.pager_preview);
        CircleIndicator crIndicator = findViewById(R.id.circle_indicator);
        pager.setAdapter(new PagerAdapter() {
            @NonNull
            public Object instantiateItem(@NonNull ViewGroup collection, int position) {
                int resId = 0;
                switch (position) {
                    case 0:
                        resId = R.id.tab_one;
                        break;
                    case 1:
                        resId = R.id.tab_two;
                        break;
                }
                return findViewById(resId);
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
                return arg0 == arg1;
            }

            @Override public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                // No super
            }
        });

        crIndicator.setViewPager(pager);

        initTabOne();
        initTabTwo();
    }

    private void initTabTwo() {
        RecyclerView rcvChatPreview = findViewById(R.id.rcv_chat_preview);
        rcvChatPreview.setLayoutManager(new LinearLayoutManager(this));
        rcvChatPreview.setAdapter(new ThemeStyleChatPreviewAdapter(this,model));
    }

    private void initTabOne() {
        RecyclerView rcvSmsPreview = findViewById(R.id.rcv_sms_preview);
        rcvSmsPreview.setLayoutManager(new LinearLayoutManager(this));
        rcvSmsPreview.setAdapter(new ThemeStyleSmsPreviewAdapter(this,model));
    }

    private void initBackground() {
        final View view = findViewById(R.id.root);
        if(model.getId()>0){
            view.post(new Runnable() {
                @Override
                public void run() {
                    Glide.with(ThemeStylePreviewActivity.this)
                            .load(model.getBackground())
                            .apply(new RequestOptions()
                                    .centerCrop()
                                    .override(view.getWidth(),view.getHeight()))
                            .into(new CustomTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    view.setBackground(resource);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                }
            });
        }else {
            view.setBackgroundColor(model.getStyleColor());
            findViewById(R.id.main).setBackgroundColor(Color.parseColor("#FAFAFA"));
        }

    }

    private void initToolbar() {
        View toolbar = findViewById(R.id.view_toolbar);
        if(model.getId()>0){
            toolbar.setBackgroundColor(0);
        }
        ImageView btnBack = findViewById(R.id.bt_back);
        TextView tvTitle = findViewById(R.id.tv_title);
        btnBack.setColorFilter(model.getTitleColor());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setTextColor(model.getTitleColor());
        tvTitle.setText(model.getName());
    }
}
