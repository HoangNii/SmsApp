package com.colorsms.style.fragments;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colorsms.style.R;

public class ThemeStyleFragment extends BaseFragment {

    public static ThemeStyleFragment newInstance() {
        Bundle args = new Bundle();
        ThemeStyleFragment fragment = new ThemeStyleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        update();

    }

    private void update() {
        RecyclerView rcvTheme = view.findViewById(R.id.rcv_theme_style);
        rcvTheme.setLayoutManager(new GridLayoutManager(activity,2));
//        rcvTheme.setAdapter(new ThemeStyleAdapter(activity, Style.ColorStyle.getStyleModels()) {
//            @Override
//            public void OnItemStyleClick(int position) {
//                ThemeStylePreviewActivity.startPreview(activity,position);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme_style;
    }
}
