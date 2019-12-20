package com.colorsms.style.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.bumptech.glide.Glide;
import com.colorsms.style.App;
import com.colorsms.style.R;
import com.colorsms.style.ads.Callback;
import com.colorsms.style.ads.MyAdmobController;
import com.colorsms.style.ads.MyAds;
import com.colorsms.style.ads.MyFacebookAdsController;
import com.colorsms.style.callReport.CallReceiver;
import com.colorsms.style.helper.Style;
import com.github.kayvannj.permission_utils.Func;
import com.github.kayvannj.permission_utils.PermissionUtil;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DialogCallReportActivity extends AppCompatActivity {

    private TextView tvContactName,tvPhone,tvTime,tvDuration;
    private ImageView imgType,imgAvatar;
    private CardView cvAvatar,cvCall,cvMessage;
    private PermissionUtil.PermissionRequestObject mRequestObject;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{ setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); }
        catch(Exception ignore){}
        setContentView(R.layout.dialog_call_report);

        initViews();

        findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setup();

        MyAds.initBannerReport(this);


    }

    private void loadAds(){

        if(!MyAds.isInterLoaded()){
            MyAds.initInterAds(this);
        }

        final View loadView = findViewById(R.id.load_view);
        loadView.setBackgroundColor(Style.Home.getStyleColor());


        final View reportView = findViewById(R.id.report_view);
        loadView.setVisibility(View.VISIBLE);
        reportView.setAlpha(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyAds.showInterFull(DialogCallReportActivity.this, new Callback() {
                    @Override
                    public void callBack(Object value, int where) {
                        loadView.setVisibility(View.GONE);
                        reportView.animate().alpha(1).setDuration(500).start();
                    }
                });

            }
        },2000);
    }

    @SuppressLint("SetTextI18n")
    private void setup() {
        long dateStart = getIntent().getLongExtra(CallReceiver.START,0);
        long dateEnd = getIntent().getLongExtra(CallReceiver.END,0);
        int type = getIntent().getIntExtra(CallReceiver.STATE,0);
        final String number = getIntent().getStringExtra(CallReceiver.NUMBER);
        Log.e("call",number+"");

        switch (type){
            case CallLog.Calls.MISSED_TYPE:
                imgType.setImageResource(R.drawable.ic_misscall);
                tvDuration.setVisibility(View.GONE);
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                imgType.setImageResource(R.drawable.ic_callend);
                tvDuration.setVisibility(View.GONE);
                loadAds();
                break;
            default:
                imgType.setImageResource(R.drawable.ic_callend);
                String callDuration = getTimeFromSeconds((dateEnd-dateStart)/1000);
                tvDuration.setVisibility(View.VISIBLE);
                tvDuration.setText("Durations : " + callDuration +" s");
                loadAds();

        }

        tvTime.setText(new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(dateStart));

        if(number==null||number.equals("null")){
            cvMessage.setVisibility(View.GONE);
            tvContactName.setText("Last Call");
            tvPhone.setVisibility(View.GONE);
            TextView tvCall = findViewById(R.id.tv_call);
            tvCall.setText("View Detail");
            findViewById(R.id.bt_call).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showCallLog = new Intent();
                    showCallLog.setAction(Intent.ACTION_VIEW);
                    showCallLog.setType(CallLog.Calls.CONTENT_TYPE);
                    startActivity(showCallLog);
                    finish();
                }
            });

        }else {
            String name = getContactName(this,number);
            if(!TextUtils.isEmpty(name)){
                tvPhone.setText(number);
                tvContactName.setText(name);
                imgAvatar.setImageResource(R.drawable.ic_user);
            }else {
                tvPhone.setVisibility(View.GONE);
                tvContactName.setText(number);
                imgAvatar.setImageResource(R.drawable.ic_user);
            }


            findViewById(R.id.bt_call).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRequestObject = PermissionUtil.with(DialogCallReportActivity.this)
                            .request(Manifest.permission.CALL_PHONE).onAllGranted(
                                    new Func() {
                                        @SuppressLint({"MissingPermission", "SetTextI18n"})
                                        @Override protected void call() {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +number));
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).ask(13);
                }
            });

            findViewById(R.id.bt_message).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("smsto:"+number);
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(it);
                    finish();
                }
            });
        }
    }

    private void initViews() {
        tvContactName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_number);
        tvTime = findViewById(R.id.tv_time);
        tvDuration = findViewById(R.id.tv_duration);
        imgType = findViewById(R.id.img_type);
        imgAvatar = findViewById(R.id.img_contact_avatar);
        cvAvatar = findViewById(R.id.cv_avatar);
        cvCall = findViewById(R.id.cv_call);
        cvMessage = findViewById(R.id.cv_message);

        cvAvatar.setCardBackgroundColor(Style.Home.getStyleColor());
        cvCall.setCardBackgroundColor(Style.Home.getStyleColor());
        cvMessage.setCardBackgroundColor(Style.Home.getStyleColor());
        tvContactName.setTextColor(Style.Home.getStyleColor());



    }

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if(!cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestObject.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    @SuppressLint("DefaultLocale")
    private String getTimeFromSeconds(long time){
        try {
            int hours = (int) (time / 3600);
            int minutes = (int) ((time % 3600) / 60);
            int seconds = (int) (time % 60);
            if(hours>0){
                return String.format("%02d:%02d:%02d", hours, minutes, seconds);
            } else {
                return String.format("%02d:%02d", minutes, seconds);
            }
        } catch (Exception e){
            return "00:00";
        }

    }




}
