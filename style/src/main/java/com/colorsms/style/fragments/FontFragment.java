package com.colorsms.style.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.colorsms.style.R;
import com.colorsms.style.adapters.FontFamilyAdapter;
import com.colorsms.style.helper.Style;
import com.colorsms.style.previews.ChatPreview;

public class FontFragment extends BaseFragment{

    private ChatPreview chatPreview;
    private View viewFontSize,viewFontFamily;
    private Button btnFontSize,btnFontFamily;

    public static FontFragment newInstance() {
        Bundle args = new Bundle();
        FontFragment fragment = new FontFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initViews();

        setupFontFamily();

        setupFontSize();

        setupButtonBottom();
    }

    private void setupFontSize() {
        SeekBar sbFontSize = view.findViewById(R.id.sb_font_size);
        sbFontSize.setMax(20);
        sbFontSize.setProgress(Style.Font.getFontSize()-10);
        sbFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Style.Font.setFontSize(i+10);
                chatPreview.updateStyle();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setupFontFamily() {
        RecyclerView rcvFontFamily = view.findViewById(R.id.rcv_font_family);
        rcvFontFamily.setLayoutManager(new GridLayoutManager(activity,2));
        rcvFontFamily.setAdapter(new FontFamilyAdapter(activity) {
            @Override
            public void OnItemFontFamilyClick(int position) {
                Style.Font.setFontFamilyPosition(position);
                chatPreview.updateStyle();
                notifyDataSetChanged();
            }
        });
    }

    private void initViews() {
        viewFontSize = view.findViewById(R.id.view_font_size);
        viewFontFamily = view.findViewById(R.id.view_font_family);
        btnFontSize = view.findViewById(R.id.bt_font_size);
        btnFontFamily = view.findViewById(R.id.bt_font_family);
        chatPreview = view.findViewById(R.id.chat_preview);
    }


    private void setupButtonBottom() {
        updateColorButton(btnFontFamily);

        btnFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFontSize.setVisibility(View.VISIBLE);
                viewFontFamily.setVisibility(View.GONE);
                updateColorButton((Button) v);
            }
        });

        btnFontFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFontSize.setVisibility(View.GONE);
                viewFontFamily.setVisibility(View.VISIBLE);
                updateColorButton((Button) v);
            }
        });
    }


    private void updateColorButton(Button btn) {
        int color = ContextCompat.getColor(activity,R.color.colorBubblePreview);
        btnFontSize.setTextColor(color);
        btnFontFamily.setTextColor(color);
        btn.setTextColor(Style.Home.getStyleColor());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_font;
    }
}
