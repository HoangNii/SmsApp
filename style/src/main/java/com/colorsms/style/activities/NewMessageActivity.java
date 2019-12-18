package com.colorsms.style.activities;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.colorsms.style.App;
import com.colorsms.style.R;
import com.colorsms.style.helper.Style;
import java.util.Calendar;

public class NewMessageActivity extends AppCompatActivity {

    private String address;
    private String body;
    private String messId;

    public static void startPreview(Context context, String address, String body, String messId){
        Intent newIntent = new Intent(context, NewMessageActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_NO_USER_ACTION
                | Intent.FLAG_ACTIVITY_NO_ANIMATION
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_FROM_BACKGROUND);
        newIntent.putExtra("address",address);
        newIntent.putExtra("body",body);
        newIntent.putExtra("messId",messId);
        context.getApplicationContext().startActivity(newIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        try{ setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); } catch(Exception ignore){}

        address = getIntent().getStringExtra("address");
        body = getIntent().getStringExtra("body");
        messId = getIntent().getStringExtra("messId");

        final Dialog dialog = new Dialog(this,R.style.TranslucentDialog);
        dialog.setContentView(R.layout.activity_new_message);
        dialog.setCancelable(false);

        TextView tvAuthor = dialog.findViewById(R.id.tv_author);
        TextView tvAddress = dialog.findViewById(R.id.tv_address);
        TextView tvBody = dialog.findViewById(R.id.tv_body);
        TextView tvTime = dialog.findViewById(R.id.tv_time);
        TextView btReply = dialog.findViewById(R.id.bt_reply);
        TextView btCancel = dialog.findViewById(R.id.bt_cancel);
        String name = getContactName(this,address);
        if(!TextUtils.isEmpty(name)&&!name.equals(address)){
            tvAuthor.setText(name);
            tvAddress.setText(address);
        }else {
            tvAuthor.setText(address);
            tvAddress.setVisibility(View.GONE);
        }

        tvBody.setText(body);
        tvTime.setText(getStringDateSms(System.currentTimeMillis()));
        btReply.getBackground().setColorFilter(Style.Home.getStyleColor(), PorterDuff.Mode.SRC);
        ((ImageView)dialog.findViewById(R.id.img_icon)).setColorFilter(Style.Home.getStyleColor());
        btCancel.setTextColor(Style.Home.getStyleColor());
        btReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.get().start(messId);
                dialog.dismiss();
                finish();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }


    public static String getStringDateSms(long date) {
        Calendar today = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);

        //nếu date là hôm nay
        if(cal.get(Calendar.DAY_OF_YEAR)==today.get(Calendar.DAY_OF_YEAR)
                &&cal.get(Calendar.YEAR)==today.get(Calendar.YEAR)){
            return DateFormat.format("HH:mm aa",cal).toString();
        }
        //nếu date trong tuần
        if(cal.get(Calendar.WEEK_OF_YEAR)==today.get(Calendar.WEEK_OF_YEAR)
                &&cal.get(Calendar.YEAR)==today.get(Calendar.YEAR)){
            return DateFormat.format("EEEE",cal).toString();
        }else {
            return DateFormat.format("dd/MM/yyyy",cal).toString();
        }

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

    public interface NewSmsListener{
        void onShowReport();
    }
}
