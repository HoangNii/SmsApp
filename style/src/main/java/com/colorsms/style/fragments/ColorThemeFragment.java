package com.colorsms.style.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.colorsms.style.R;
import com.colorsms.style.adapters.ColorPreviewAdapter;
import com.colorsms.style.helper.Style;
import com.colorsms.style.views.OnTouchClick;
import com.colorsms.style.views.colorpicker.ColorPickerView;

public class ColorThemeFragment extends BaseFragment{

    private ColorUpdateListener colorUpdateListener;
    private boolean isUpdate = false;


    public void setColorUpdateListener(ColorUpdateListener colorUpdateListener) {
        this.colorUpdateListener = colorUpdateListener;
    }

    public static ColorThemeFragment newInstance() {
        Bundle args = new Bundle();
        ColorThemeFragment fragment = new ColorThemeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListColor();

        updateToolbar();
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void setupListColor() {
        final RecyclerView rcvColor = view.findViewById(R.id.rcv_color);
        final Button btnCustom = view.findViewById(R.id.bt_custom_color);
        final ColorPickerView colorPickerView = view.findViewById(R.id.color_picker_view);
        colorPickerView.animate().alpha(0).scaleX(0.5f).scaleY(0.5f)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        colorPickerView.setVisibility(View.INVISIBLE);
                    }
                })
                .setDuration(0).start();

        final ColorPreviewAdapter colorPreviewAdapter = new ColorPreviewAdapter(activity) {
            @Override
            public void OnColorItemClick(int color) {
                Style.Home.setStyleColor(color);
                notifyDataSetChanged();
                updateToolbar();
                if(btnCustom.getText().toString().equals("Customize")){
                    btnCustom.setTextColor(Style.Home.getStyleColor());
                }
                isUpdate = true;
                colorUpdateListener.onColorUpdate(false);
            }
        };

        colorPickerView.getLayoutParams().width = (int) (getResources().getDisplayMetrics().widthPixels/1.2);
        colorPickerView.getLayoutParams().height = (int) (getResources().getDisplayMetrics().widthPixels/2);

        colorPickerView.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int newColor) {
                Style.Home.setStyleColor(newColor);
                colorPreviewAdapter.notifyDataSetChanged();
                updateToolbar();
                isUpdate = true;
                colorUpdateListener.onColorUpdate(false);
            }
        });


        rcvColor.setLayoutManager(new GridLayoutManager(activity,4));
        rcvColor.setAdapter(colorPreviewAdapter);


        btnCustom.setText("Customize");
        btnCustom.setTextColor(Style.Home.getStyleColor());
        btnCustom.setOnTouchListener(new OnTouchClick());
        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCustom.getText().toString().equals("Customize")){
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
                    colorPickerView.setColor(Style.Home.getStyleColor());
                }else {
                    btnCustom.setText("Customize");
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
    }

    private void updateToolbar(){
        view.findViewById(R.id.view_toolbar).setBackgroundColor(Style.Home.getStyleColor());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme_color;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isUpdate&&colorUpdateListener!=null){
            colorUpdateListener.onColorUpdate(true);
        }
    }

    public interface ColorUpdateListener{
        void onColorUpdate(boolean isDestroy);
    }
}
