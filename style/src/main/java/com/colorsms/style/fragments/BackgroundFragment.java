package com.colorsms.style.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.colorsms.style.R;
import com.colorsms.style.adapters.BackgroundAdapter;
import com.github.kayvannj.permission_utils.Func;
import com.github.kayvannj.permission_utils.PermissionUtil;

public class BackgroundFragment extends BaseFragment {

    private PermissionUtil.PermissionRequestObject mRequestObject;

    public static BackgroundFragment newInstance() {
        Bundle args = new Bundle();
        BackgroundFragment fragment = new BackgroundFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView rcvBackground = view.findViewById(R.id.rcv_background);
        rcvBackground.setLayoutManager(new GridLayoutManager(activity,2));
        rcvBackground.setAdapter(new BackgroundAdapter(activity) {
            @Override
            public void OnItemBackgroundClick(int position) {
                if(position==0){
                    mRequestObject = PermissionUtil.with(BackgroundFragment.this)
                            .request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).onAllGranted(
                            new Func() {
                                @Override protected void call() {
                                    startPickGallery();
                                }
                            }).onAnyDenied(
                            new Func() {
                                @Override protected void call() {
                                    Toast.makeText(activity, "No no no !!~~ Allow and continue...", Toast.LENGTH_SHORT).show();
                                }
                            }).ask(12);
                }else {
//                    BackgroundPreviewActivity.startPreview(activity,position,null);
                }


            }
        });
    }

    private void startPickGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 33);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 33&&resultCode== Activity.RESULT_OK&&data!=null&&!TextUtils.isEmpty(data.getDataString())) {
//            BackgroundPreviewActivity.startPreview(activity,0,data.getDataString());
        }else Toast.makeText(activity, "Pick Image Error!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(mRequestObject!=null){
            mRequestObject.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_background;
    }
}