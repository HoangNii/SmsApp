package com.colorsms.style.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.colorsms.style.R;
import com.colorsms.style.helper.Style;

public abstract class BaseFragment extends Fragment {

    protected View view;

    protected AppCompatActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(),container,false);

        View btBack = view.findViewById(R.id.bt_back);
        if(btBack!=null){
            btBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.getSupportFragmentManager().popBackStack();
                }
            });
        }

        View toolbar = view.findViewById(R.id.view_toolbar);
        if(toolbar!=null){
            toolbar.setBackgroundColor(Style.Home.getStyleColor());
        }

        return view;
    }

    protected abstract int getLayoutId();


    public static void startAddToBackStack(AppCompatActivity activity, Fragment fragment){
        if(activity.getSupportFragmentManager().findFragmentByTag(fragment.getClass().getName())==null){
            addFragment(activity,fragment,fragment.getClass().getName());
        }
    }


    private static void addFragment(AppCompatActivity activity, @NonNull Fragment fragment,
                                    @NonNull String tag) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .setCustomAnimations(R.anim.anim_in,R.anim.anim_out,R.anim.anim_in,R.anim.anim_out)
                .add(R.id.root, fragment, tag)
                .commitAllowingStateLoss();

    }

}
