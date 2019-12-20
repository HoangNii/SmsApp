package com.colorsms.style.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.colorsms.style.R;
import com.colorsms.style.adapters.BubbleShapeAdapter;
import com.colorsms.style.adapters.ColorBubblePreviewAdapter;
import com.colorsms.style.adapters.ColorTextBubblePreviewAdapter;
import com.colorsms.style.helper.Style;
import com.colorsms.style.previews.ChatPreview;
import com.colorsms.style.views.colorpicker.ColorPickerView;

public class BubbleThemeFragment extends BaseFragment {

    private ChatPreview chatPreview;

    private View bubbleShapeView,bubbleColorView,textColorView;

    private Button btnShape,btnColor,btnText;

    public static BubbleThemeFragment newInstance() {
        Bundle args = new Bundle();
        BubbleThemeFragment fragment = new BubbleThemeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();

        setupBubbleShape();

        setupBubbleColor();

        setupTextColor();

        setupButtonBottom();


        view.findViewById(R.id.bt_reload_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDefaultBubble();
            }
        });

    }

    private void toDefaultBubble() {
        Style.Bubble.toReloadBubbleStyle();
        chatPreview.reloadDefault();
        setupBubbleShape();
    }

    private void setupBubbleColor() {
        final TextView tvTitle = view.findViewById(R.id.tv_bubble_color_title);
        tvTitle.setText("Select Bubble Color ( Received )");

        final ImageView imgBubbleReceivedColor = view.findViewById(R.id.img_bubble_received_color);
        final ImageView imgBubbleSentColor = view.findViewById(R.id.img_bubble_sent_color);
        final View btnBubbleReceivedColor = view.findViewById(R.id.bt_bubble_received_shape_color);
        View btnBubbleSentColor = view.findViewById(R.id.bt_bubble_sent_shape_color);

        View viewBubbleShapeColorMain  = view.findViewById(R.id.view_bubble_shape_color_main);
        final View viewBubbleShapeColorSelect = view.findViewById(R.id.view_bubble_shape_color_select);
        viewBubbleShapeColorMain.setVisibility(View.VISIBLE);
        viewBubbleShapeColorSelect.setVisibility(View.GONE);
        viewBubbleShapeColorSelect.animate().alpha(0)
                .scaleY(0.5f).scaleX(0.5f)
                .translationY(300)
                .setDuration(0).start();

        Button btnBackShape = view.findViewById(R.id.bt_back_shape_color);
        btnBackShape.setTextColor(Style.Home.getStyleColor());

        final Button btnCustom = view.findViewById(R.id.bt_custom_shape_color);
        final ColorPickerView colorPickerView = view.findViewById(R.id.color_picker_view);

        colorPickerView.animate().alpha(0).scaleX(0.5f).scaleY(0.5f)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        colorPickerView.setVisibility(View.INVISIBLE);
                    }
                })
                .setDuration(0).start();

        final RecyclerView rcvColor = view.findViewById(R.id.rcv_color);
        rcvColor.setLayoutManager(new GridLayoutManager(activity,4));

        btnCustom.setText("Customs");
        btnCustom.setTextColor(Style.Home.getStyleColor());

        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCustom.getText().toString().equals("Customs")){
                    btnCustom.setText("Cancel");
                    btnCustom.setTextColor(Color.GRAY);

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
                    if(btnBubbleReceivedColor.isSelected()){
                        colorPickerView.setColor(Style.Bubble.getBubbleShapeReceivedColor());
                    }else {
                        colorPickerView.setColor(Style.Bubble.getBubbleShapeSentColor());
                    }
                }else {
                    btnCustom.setText("Customs");
                    btnCustom.setTextColor(Style.Home.getStyleColor());

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

        final ColorBubblePreviewAdapter colorBubblePreviewAdapter = new ColorBubblePreviewAdapter(activity,btnBubbleReceivedColor) {
            @Override
            public void OnColorItemClick(int color) {
                if(btnBubbleReceivedColor.isSelected()){
                    Style.Bubble.setBubbleShapeReceivedColor(color);
                    imgBubbleReceivedColor.getBackground().setColorFilter(Style.Bubble.getBubbleShapeReceivedColor(), PorterDuff.Mode.SRC_IN);
                }else {
                    Style.Bubble.setBubbleShapeSentColor(color);
                    imgBubbleSentColor.getBackground().setColorFilter(Style.Bubble.getBubbleShapeSentColor(),PorterDuff.Mode.SRC_IN);
                }
                notifyDataSetChanged();
                chatPreview.updateStyle();
            }
        };
        rcvColor.setAdapter(colorBubblePreviewAdapter);


        colorPickerView.getLayoutParams().width = (int) (getResources().getDisplayMetrics().widthPixels/1.2);
        colorPickerView.getLayoutParams().height = (int) (getResources().getDisplayMetrics().widthPixels/2);
        colorPickerView.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int newColor) {
                if(btnBubbleReceivedColor.isSelected()){
                    Style.Bubble.setBubbleShapeReceivedColor(newColor);
                    imgBubbleReceivedColor.getBackground().setColorFilter(Style.Bubble.getBubbleShapeReceivedColor(), PorterDuff.Mode.SRC_IN);
                }else {
                    Style.Bubble.setBubbleShapeSentColor(newColor);
                    imgBubbleSentColor.getBackground().setColorFilter(Style.Bubble.getBubbleShapeSentColor(),PorterDuff.Mode.SRC_IN);

                }
                chatPreview.updateStyle();
                colorBubblePreviewAdapter.notifyDataSetChanged();
            }
        });



        imgBubbleReceivedColor.getBackground().setColorFilter(Style.Bubble.getBubbleShapeReceivedColor(), PorterDuff.Mode.SRC_IN);
        imgBubbleSentColor.getBackground().setColorFilter(Style.Bubble.getBubbleShapeSentColor(),PorterDuff.Mode.SRC_IN);
        btnBubbleReceivedColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBubbleShapeColorSelect.setVisibility(View.VISIBLE);
                btnBubbleReceivedColor.setSelected(true);
                viewBubbleShapeColorSelect.animate().alpha(1).scaleY(1f).scaleX(1f)
                        .translationY(0)
                        .setDuration(300).start();
                tvTitle.setText("Select Bubble Color ( Received )");
            }
        });

        btnBubbleSentColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBubbleShapeColorSelect.setVisibility(View.VISIBLE);
                btnBubbleReceivedColor.setSelected(false);
                viewBubbleShapeColorSelect.animate().alpha(1).scaleY(1f).scaleX(1f)
                        .translationY(0)
                        .setDuration(300).start();
                tvTitle.setText("Select Bubble Color ( Sent )");
            }
        });

        btnBackShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCustom.getText().toString().equals("Cancel")){
                    btnCustom.performClick();
                }

                viewBubbleShapeColorSelect.animate().alpha(0).scaleY(0.5f).scaleX(0.5f)
                        .translationY(300)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                viewBubbleShapeColorSelect.setVisibility(View.GONE);
                            }
                        })
                        .setDuration(300).start();
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void setupTextColor() {

        final TextView tvTitle = view.findViewById(R.id.tv_text_color_title);
        tvTitle.setText("Select Text Color ( Received )");

        final ImageView imgTextReceivedColor = view.findViewById(R.id.img_text_received_color);
        final ImageView imgTextSentColor = view.findViewById(R.id.img_text_sent_color);
        final View btnTextReceivedColor = view.findViewById(R.id.bt_text_received_shape_color);
        View btnTextSentColor = view.findViewById(R.id.bt_text_sent_color);

        View viewTextColorMain  = view.findViewById(R.id.view_text_color_main);
        final View viewTextColorSelect = view.findViewById(R.id.view_text_color_select);
        viewTextColorMain.setVisibility(View.VISIBLE);
        viewTextColorSelect.setVisibility(View.GONE);
        viewTextColorSelect.animate().alpha(0)
                .scaleY(0.5f).scaleX(0.5f)
                .translationY(300)
                .setDuration(0).start();

        Button btnBackText = view.findViewById(R.id.bt_back_text_color);
        btnBackText.setTextColor(Style.Home.getStyleColor());

        final Button btnCustom = view.findViewById(R.id.bt_custom_text_color);
        final ColorPickerView colorPickerView = view.findViewById(R.id.color_picker_view_text);
        colorPickerView.animate().alpha(0).scaleX(0.5f).scaleY(0.5f)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        colorPickerView.setVisibility(View.INVISIBLE);
                    }
                })
                .setDuration(0).start();

        final RecyclerView rcvColor = view.findViewById(R.id.rcv_text_color);
        rcvColor.setLayoutManager(new GridLayoutManager(activity,4));

        btnCustom.setText("Customs");
        btnCustom.setTextColor(Style.Home.getStyleColor());

        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCustom.getText().toString().equals("Customs")){
                    btnCustom.setText("Cancel");
                    btnCustom.setTextColor(Color.GRAY);

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

                    if(btnTextReceivedColor.isSelected()){
                        colorPickerView.setColor(Style.Bubble.getBubbleTextReceivedColor());
                    }else {
                        colorPickerView.setColor(Style.Bubble.getBubbleTextSentColor());
                    }
                }else {
                    btnCustom.setText("Customs");
                    btnCustom.setTextColor(Style.Home.getStyleColor());

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

        final ColorTextBubblePreviewAdapter colorTextBubblePreviewAdapter = new ColorTextBubblePreviewAdapter(activity,btnTextReceivedColor) {
            @Override
            public void OnColorItemClick(int color) {
                if(btnTextReceivedColor.isSelected()){
                    Style.Bubble.setBubbleTextReceivedColor(color);
                    imgTextReceivedColor.getBackground().setColorFilter(Style.Bubble.getBubbleTextReceivedColor(), PorterDuff.Mode.SRC_IN);
                }else {
                    Style.Bubble.setBubbleTextSentColor(color);
                    imgTextSentColor.getBackground().setColorFilter(Style.Bubble.getBubbleTextSentColor(),PorterDuff.Mode.SRC_IN);
                }
                notifyDataSetChanged();
                chatPreview.updateStyle();
            }
        };
        rcvColor.setAdapter(colorTextBubblePreviewAdapter);


        colorPickerView.getLayoutParams().width = (int) (getResources().getDisplayMetrics().widthPixels/1.2);
        colorPickerView.getLayoutParams().height = (int) (getResources().getDisplayMetrics().widthPixels/2);
        colorPickerView.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int newColor) {
                if(btnTextReceivedColor.isSelected()){
                    Style.Bubble.setBubbleTextReceivedColor(newColor);
                    imgTextReceivedColor.getBackground().setColorFilter(Style.Bubble.getBubbleTextReceivedColor(), PorterDuff.Mode.SRC_IN);
                }else {
                    Style.Bubble.setBubbleTextSentColor(newColor);
                    imgTextSentColor.getBackground().setColorFilter(Style.Bubble.getBubbleTextSentColor(),PorterDuff.Mode.SRC_IN);

                }
                chatPreview.updateStyle();
                colorTextBubblePreviewAdapter.notifyDataSetChanged();
            }
        });



        imgTextReceivedColor.getBackground().setColorFilter(Style.Bubble.getBubbleTextReceivedColor(), PorterDuff.Mode.SRC_IN);
        imgTextSentColor.getBackground().setColorFilter(Style.Bubble.getBubbleTextSentColor(),PorterDuff.Mode.SRC_IN);
        btnTextReceivedColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewTextColorSelect.setVisibility(View.VISIBLE);
                btnTextReceivedColor.setSelected(true);
                viewTextColorSelect.animate().alpha(1).scaleY(1f).scaleX(1f)
                        .translationY(0)
                        .setDuration(300).start();
                tvTitle.setText("Select Text Color ( Received )");
            }
        });

        btnTextSentColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewTextColorSelect.setVisibility(View.VISIBLE);
                btnTextReceivedColor.setSelected(false);
                viewTextColorSelect.animate().alpha(1).scaleY(1f).scaleX(1f)
                        .translationY(0)
                        .setDuration(300).start();
                tvTitle.setText("Select Text Color ( Sent )");
            }
        });

        btnBackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCustom.getText().toString().equals("Cancel")){
                    btnCustom.performClick();
                }
                viewTextColorSelect.animate().alpha(0).scaleY(0.5f).scaleX(0.5f)
                        .translationY(300)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                viewTextColorSelect.setVisibility(View.GONE);
                            }
                        })
                        .setDuration(300).start();
            }
        });


    }
    private void setupButtonBottom() {
        updateColorButton(btnShape);

        btnShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleShapeView.setVisibility(View.VISIBLE);
                bubbleColorView.setVisibility(View.GONE);
                textColorView.setVisibility(View.GONE);
                updateColorButton((Button) v);
            }
        });

        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleShapeView.setVisibility(View.GONE);
                bubbleColorView.setVisibility(View.VISIBLE);
                textColorView.setVisibility(View.GONE);
                updateColorButton((Button) v);
            }
        });

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleShapeView.setVisibility(View.GONE);
                bubbleColorView.setVisibility(View.GONE);
                textColorView.setVisibility(View.VISIBLE);
                updateColorButton((Button) v);
            }
        });
    }

    private void updateColorButton(Button btn) {
        int color = ContextCompat.getColor(activity,R.color.colorBubblePreview);
        btnShape.setTextColor(color);
        btnText.setTextColor(color);
        btnColor.setTextColor(color);
        btn.setTextColor(Style.Home.getStyleColor());
    }

    private void initViews() {
        chatPreview = view.findViewById(R.id.chat_preview);
        bubbleShapeView = view.findViewById(R.id.view_bubble_shape);
        bubbleColorView = view.findViewById(R.id.view_bubble_color);
        textColorView = view.findViewById(R.id.view_text_color);
        btnShape = view.findViewById(R.id.bt_shape);
        btnColor = view.findViewById(R.id.bt_color);
        btnText = view.findViewById(R.id.bt_text);
    }

    private void setupBubbleShape() {
        RecyclerView rcvBubbleShape = view.findViewById(R.id.rcv_bubble_shape);
        rcvBubbleShape.setLayoutManager(new LinearLayoutManager(activity));
        rcvBubbleShape.setAdapter(new BubbleShapeAdapter(activity) {
            @Override
            public void OnBubbleShapeItemClick(int position) {
                Style.Bubble.setUseBubbleShapeDefault(true);
                Style.Bubble.setBubbleShapeDefaultPosition(position);
                if(Style.Bubble.getBubbleShapeReceivedColor()==Color.TRANSPARENT){
                    Style.Bubble.setBubbleShapeReceivedColor(Style.Home.getStyleColor());
                }
                if(Style.Bubble.getBubbleShapeSentColor()==Color.TRANSPARENT){
                    Style.Bubble.setBubbleShapeSentColor(Style.Home.getStyleColor());
                }
                notifyDataSetChanged();
                checkAvatarGravity(position);
                chatPreview.updateStyle();

            }
        });
    }

    private void checkAvatarGravity(int position) {
        int gravity = Gravity.CENTER_VERTICAL;
        switch (position){
            case 0: gravity = Gravity.BOTTOM; break;
            case 1: gravity = Gravity.BOTTOM; break;
            case 2: gravity = Gravity.CENTER_VERTICAL; break;
            case 3: gravity = Gravity.BOTTOM; break;
            case 4: gravity = Gravity.BOTTOM; break;
        }
        Style.Avatar.setAvatarGravity(gravity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bubble;
    }
}
