package com.colorsms.style.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.colorsms.style.adapters.ColorBackgroundSmsPreviewAdapter;
import com.colorsms.style.helper.Style;
import com.colorsms.style.helper.StyleHelper;
import com.colorsms.style.models.StyleModel;
import com.colorsms.style.views.CircleIndicator;
import com.colorsms.style.views.OnTouchClick;
import com.colorsms.style.views.colorpicker.ColorPickerView;

import me.shaohui.bottomdialog.BottomDialog;

public class BackgroundPreviewActivity extends AppCompatActivity {

    private int position;

    private String uri;

    private int colorSmsPreview = Style.Background.getHomeTextColor();


    private String[][] listPreviews = new String[][]{
            new String[]{"Justin","Hey! Have you watched the lastest Arsenal game?","4:10 PM"},
            new String[]{"Hana","Hey! Have you watched the lastest Arsenal game?","Friday"},
            new String[]{"1234567890","Hey! Have you watched the lastest Arsenal game?","3/4/2019"},
            new String[]{"1346802","Hey! Have you watched the lastest Arsenal game?","1/4/2019"},
            new String[]{"Jeremy","Hey! Have you watched the lastest Arsenal game?","26/3/2019"},
            new String[]{"Romeo","You: Where are you going?","22/3/2019"},
            new String[]{"Victoria","You: Where are you going?","22/3/2019"},
            new String[]{"Chris","You: Where are you going?","22/3/2019"},
            new String[]{"Lena","You: Where are you going?","22/3/2019"},
    };

    public static void startPreview(Activity context, int position, String uri){
        Intent intent = new Intent(context,BackgroundPreviewActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("uri",uri);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{ setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); } catch(Exception ignore){}
        setContentView(R.layout.activity_background_preview);

        findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        position = getIntent().getIntExtra("position",1);

        if(position==0){
            uri = getIntent().getStringExtra("uri");
        }

        setupBackground();

        setupPager();


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

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                findViewById(R.id.bt_setting).setVisibility(position==0?View.VISIBLE:View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initTabOne() {
        RecyclerView rcv = findViewById(R.id.rcv_sms_background);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        final ItemSmsAdapter smsAdapter = new ItemSmsAdapter(this);
        rcv.setAdapter(smsAdapter);

        View btnSetting = findViewById(R.id.bt_setting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialog.create(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_background_preview_sms_setting)
                        .setViewListener(new BottomDialog.ViewListener() {
                            @Override
                            public void bindView(View v) {
                                setupDialogColor(v,smsAdapter);
                            }
                        })
                        .setDimAmount(0.5f)
                        .show();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupDialogColor(View dialog, final ItemSmsAdapter smsAdapter) {
        final Button btnCustomColor = dialog.findViewById(R.id.bt_custom_color);
        final RecyclerView rcvColor = dialog.findViewById(R.id.rcv_color);
        rcvColor.setLayoutManager(new GridLayoutManager(this,4));
        rcvColor.setAdapter(new ColorBackgroundSmsPreviewAdapter(this) {
            @Override
            public void OnColorItemClick(int color) {
                colorSmsPreview = color;
                smsAdapter.notifyDataSetChanged();
            }
        });

        final ColorPickerView colorPickerView = dialog.findViewById(R.id.color_picker_view);
        colorPickerView.setColor(colorSmsPreview);
        colorPickerView.getLayoutParams().width = (int) (getResources().getDisplayMetrics().widthPixels/1.2);
        colorPickerView.getLayoutParams().height = (int) (getResources().getDisplayMetrics().widthPixels/2);
        colorPickerView.animate().alpha(0).scaleX(0.5f).scaleY(0.5f)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        colorPickerView.setVisibility(View.INVISIBLE);
                    }
                })
                .setDuration(0).start();
        colorPickerView.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int newColor) {
                colorSmsPreview = newColor;
                smsAdapter.notifyDataSetChanged();
            }
        });

        btnCustomColor.setText("Customs");
        btnCustomColor.setTextColor(Style.Home.getStyleColor());
        btnCustomColor.setOnTouchListener(new OnTouchClick());
        btnCustomColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCustomColor.getText().toString().equals("Customs")){
                    btnCustomColor.setText("Cancel");
                    btnCustomColor.setTextColor(Color.GRAY);
                    rcvColor.animate().alpha(0).scaleX(0.5f).scaleY(0.5f)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    rcvColor.setVisibility(View.INVISIBLE);
                                    colorPickerView.setVisibility(View.VISIBLE);
                                    colorPickerView.animate().alpha(1).scaleX(1).scaleY(1).setDuration(200).start();
                                }
                            })
                            .setDuration(200).start();
                    colorPickerView.setColor(Style.Home.getStyleColor());
                }else {
                    btnCustomColor.setText("Customs");
                    btnCustomColor.setTextColor(Style.Home.getStyleColor());
                    colorPickerView.animate().alpha(0).scaleX(0.5f).scaleY(0.5f).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            rcvColor.setVisibility(View.VISIBLE);
                            colorPickerView.setVisibility(View.INVISIBLE);
                            rcvColor.animate().alpha(1).scaleX(1).scaleY(1).setDuration(200).start();
                        }
                    }).setDuration(200).start();
                }
            }
        });

    }

    private void setupBackground() {
        final View root = findViewById(R.id.root);
        final View main = findViewById(R.id.main);
        final View toolbar = findViewById(R.id.view_toolbar);
        final View btnSaveAs = findViewById(R.id.bt_save_as);

        root.post(new Runnable() {
            @Override
            public void run() {
                StyleModel model = Style.ColorStyle.getStyleModels().get(Style.ColorStyle.getStyleId());
                if(position==0){
                    Glide.with(BackgroundPreviewActivity.this)
                            .load(uri)
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
                        Glide.with(BackgroundPreviewActivity.this)
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

                }else {
                    Glide.with(BackgroundPreviewActivity.this)
                            .load(ContextCompat.getDrawable(App.get(),Style.Background.backgroundList[position]))
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

                if(position==1&&model.getId()==0){
                    main.setBackgroundColor(Color.parseColor("#FAFAFA"));
                }else main.setBackgroundColor(0);


                if(position==1&&model.getId()==0){
                    toolbar.setBackgroundColor(Style.Home.getStyleColor());
                }else {
                    toolbar.setBackgroundColor(0);
                }


                btnSaveAs.getBackground().setColorFilter(
                        Style.Home.getStyleColor(), PorterDuff.Mode.SRC_IN);
            }
        });

        btnSaveAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSave();
            }
        });
    }

    private void showSave() {

        final BottomDialog bottomDialog = BottomDialog.create(getSupportFragmentManager());
        bottomDialog.setLayoutRes(R.layout.dialog_background);
        bottomDialog.setViewListener(new BottomDialog.ViewListener() {
            @Override
            public void bindView(View v) {
                View bg = v.findViewById(R.id.bg);
                bg.setBackgroundColor(Style.Home.getStyleColor());
                v.findViewById(R.id.bt_set_sms_background).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Style.Background.setHomeTextColor(colorSmsPreview);
                        Style.Background.setBackgroundHomePosition(position);
                        if(position==0){
                            Style.Background.setBackgroundHomeUri(uri);
                        }
                        bottomDialog.dismiss();
                        App.get().restart();
                    }
                });

                v.findViewById(R.id.bt_set_chat_background).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Style.Background.setBackgroundChatPosition(position);
                        if(position==0){
                            Style.Background.setBackgroundChatUri(uri);
                        }
                        bottomDialog.dismiss();
                        Toast.makeText(BackgroundPreviewActivity.this, "Done!", Toast.LENGTH_SHORT).show();
                    }
                });

                v.findViewById(R.id.bt_set_both).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Style.Background.setHomeTextColor(colorSmsPreview);
                        Style.Background.setBackgroundHomePosition(position);
                        Style.Background.setBackgroundChatPosition(position);
                        if(position==0){
                            Style.Background.setBackgroundChatUri(uri);
                            Style.Background.setBackgroundHomeUri(uri);
                        }
                        bottomDialog.dismiss();
                        App.get().restart();
                    }
                });

            }
        });
        bottomDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }


    class ItemSmsAdapter extends RecyclerView.Adapter<ItemSmsAdapter.ViewHolder>{

        private Context context;

        public ItemSmsAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ItemSmsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_background_sms_preview,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemSmsAdapter.ViewHolder holder, int position) {
            holder.viewCount.getBackground().setColorFilter(Style.Home.getStyleColor(), PorterDuff.Mode.SRC_IN);
            holder.imgAvatar.getBackground().setColorFilter(Style.Home.getStyleColor(), PorterDuff.Mode.SRC_IN);
            holder.tvBody.setTextColor(colorSmsPreview);
            holder.tvTime.setTextColor(colorSmsPreview);
            holder.tvName.setTextColor(colorSmsPreview);
            holder.imgArrowEnd.setColorFilter(colorSmsPreview);

            holder.tvName.setText(listPreviews[position][0]);
            holder.tvBody.setText(listPreviews[position][1]);
            holder.tvTime.setText(listPreviews[position][2]);

            holder.viewCount.setVisibility(position>2?View.INVISIBLE:View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return listPreviews.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private View viewCount,imgAvatar;
            private TextView tvName,tvTime,tvBody;
            private ImageView imgArrowEnd;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                viewCount = itemView.findViewById(R.id.view_count_sms);
                imgAvatar = itemView.findViewById(R.id.img_contact_avatar);
                tvName = itemView.findViewById(R.id.tv_name);
                tvTime = itemView.findViewById(R.id.tv_time);
                tvBody = itemView.findViewById(R.id.tv_body);
                imgArrowEnd = itemView.findViewById(R.id.img_arrow_end);
            }
        }
    }
}
