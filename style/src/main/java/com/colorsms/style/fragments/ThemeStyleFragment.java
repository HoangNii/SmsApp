package com.colorsms.style.fragments;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.colorsms.style.R;
import com.colorsms.style.activities.ThemeStylePreviewActivity;
import com.colorsms.style.adapters.ThemeStyleAdapter;
import com.colorsms.style.helper.Style;

public class ThemeStyleFragment extends BaseFragment {

    private ThemeStyleAdapter adapter;

    public static ThemeStyleFragment newInstance() {
        Bundle args = new Bundle();
        ThemeStyleFragment fragment = new ThemeStyleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

    }

    private void init() {
        RecyclerView rcvTheme = view.findViewById(R.id.rcv_theme_style);
        rcvTheme.setLayoutManager(new GridLayoutManager(activity,2));
        rcvTheme.setAdapter(adapter = new ThemeStyleAdapter(activity, Style.ColorStyle.getStyleModels()) {
            @Override
            public void OnItemStyleClick(int position) {
                ThemeStylePreviewActivity.startPreview(activity,position);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme_style;
    }
}
