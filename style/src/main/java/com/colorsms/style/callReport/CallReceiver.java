package com.colorsms.style.callReport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.colorsms.style.App;
import com.colorsms.style.activities.DialogCallReportActivity;

import java.util.Date;
import static android.provider.CallLog.Calls.INCOMING_TYPE;
import static android.provider.CallLog.Calls.MISSED_TYPE;
import static android.provider.CallLog.Calls.OUTGOING_TYPE;

public class CallReceiver extends PhoneCallReceiver {

    private String TAG = getClass().getSimpleName();

    public static final String STATE = "state";

    public static final String NUMBER = "number";

    public static final String START = "start";

    public static final String END = "end";

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context,intent);
    }

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        super.onIncomingCallStarted(ctx, number, start);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        super.onOutgoingCallStarted(ctx, number, start);
    }


    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        super.onIncomingCallEnded(ctx, number, start, end);
        intentCallReports(INCOMING_TYPE,number,start.getTime(),end.getTime());
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        super.onMissedCall(ctx, number, start);
        intentCallReports(MISSED_TYPE,number,start.getTime(),0);

    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        super.onOutgoingCallEnded(ctx, number, start, end);
        intentCallReports(OUTGOING_TYPE,number,start.getTime(),end.getTime());
    }



    private void intentCallReports(final int state, final String number, final long start, final long end){
        Intent intent =  new Intent(App.get().getApplicationContext(), DialogCallReportActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_NO_USER_ACTION
                | Intent.FLAG_ACTIVITY_NO_ANIMATION
                | Intent.FLAG_FROM_BACKGROUND);
        intent.putExtra(STATE,state);
        intent.putExtra(NUMBER,number);
        intent.putExtra(START,start);
        intent.putExtra(END,end);
        App.get().getApplicationContext().startActivity(intent);
    }

}
