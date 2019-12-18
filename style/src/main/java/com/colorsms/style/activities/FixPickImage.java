package com.colorsms.style.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.colorsms.style.R;

public class FixPickImage extends AppCompatActivity {

    public static void startPickImage(Activity context){
        Intent intent = new Intent(context,FixPickImage.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startPickGallery();
    }

    private void startPickGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 33);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 33&&resultCode== Activity.RESULT_OK&&data!=null&&!TextUtils.isEmpty(data.getDataString())) {
            BackgroundPreviewActivity.startPreview(this,0,data.getDataString());
            finish();
        }else {
            Toast.makeText(this, "Pick Image Error!", Toast.LENGTH_SHORT).show();
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
