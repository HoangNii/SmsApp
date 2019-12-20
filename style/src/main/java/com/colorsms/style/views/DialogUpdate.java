package com.colorsms.style.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.colorsms.style.R;
import com.colorsms.style.ads.AdsConfigLoaded;
import com.colorsms.style.helper.Style;

import static com.colorsms.style.activities.NewMessageActivity.getStringDateSms;

public class DialogUpdate extends Dialog {

    public DialogUpdate(@NonNull Context context) {
        super(context, R.style.TranslucentDialog);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_update);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvMessage = findViewById(R.id.tv_message);
        TextView tvTime = findViewById(R.id.tv_time);
        ImageView imgIcon = findViewById(R.id.img_icon);

        TextView btnCancel = findViewById(R.id.bt_cancel);
        TextView btnOk = findViewById(R.id.bt_ok);

        tvTime.setText(getStringDateSms(System.currentTimeMillis()));
        tvMessage.setText(AdsConfigLoaded.get().getUpdateMessage());
        tvTitle.setText(AdsConfigLoaded.get().getUpdateTitle());
        btnOk.setText(AdsConfigLoaded.get().getUpdateActionTitle());
        Glide.with(getContext())
                .load(AdsConfigLoaded.get().getUpdateIcon())
                .into(imgIcon);

        btnCancel.setTextColor(Style.Home.getStyleColor());
        btnOk.getBackground().setColorFilter(Style.Home.getStyleColor(), PorterDuff.Mode.SRC_IN);
        if(AdsConfigLoaded.get().getUpdateMode().equals("2")){
            setCancelable(false);
            btnCancel.setVisibility(View.GONE);
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStore(getContext(),AdsConfigLoaded.get().getUpdateLink());
            }
        });

        if(!AdsConfigLoaded.get().getUpdateIsAd().equals("1")){
            findViewById(R.id.tv_ads).setVisibility(View.GONE);
        }

    }

    private void goToStore(Context context, String url) {
        if(url.contains("https://play.google.com/")){
            String MARKET_DETAILS_ID = "market://details?id=";
            String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=";
            String link = url.replace(PLAY_STORE_LINK,"");
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_DETAILS_ID +link))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } catch (android.content.ActivityNotFoundException anfe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_LINK +link))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }else {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }

    }


}
