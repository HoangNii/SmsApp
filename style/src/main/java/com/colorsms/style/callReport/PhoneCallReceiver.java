package com.colorsms.style.callReport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import java.util.Date;

public abstract class PhoneCallReceiver extends BroadcastReceiver {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;

    private static Date callStartTime;

    private static boolean isIncoming;

    private static String savedNumber;


    @Override
    public void onReceive(Context context, Intent intent) {

        String action  = intent.getAction()+"";

        if (action.equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getStringExtra("android.intent.extra.PHONE_NUMBER")+"";
        } else{
            String stateStr = intent.getStringExtra(TelephonyManager.EXTRA_STATE)+"";
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)+"";
            int state = 0;
            if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                state = TelephonyManager.CALL_STATE_IDLE;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                state = TelephonyManager.CALL_STATE_RINGING;
            }

            if(CallReports.get().isShowCallReport()){
                onCallStateChanged(context, state, number);
            }


        }
    }

    protected void onIncomingCallStarted(Context ctx, String number, Date start){}
    protected void onOutgoingCallStarted(Context ctx, String number, Date start){}
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end){}
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end){}
    protected void onMissedCall(Context ctx, String number, Date start){}

    public void onCallStateChanged(Context context, int state, String number) {
        if(lastState == state){
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                onIncomingCallStarted(context, number, callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    callStartTime = new Date();
                    onOutgoingCallStarted(context, savedNumber, callStartTime);
                }else {
                    callStartTime = new Date();
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if(lastState == TelephonyManager.CALL_STATE_RINGING){
                    onMissedCall(context, savedNumber, callStartTime);
                } else if(isIncoming){
                    onIncomingCallEnded(context, savedNumber, callStartTime, new Date());
                } else{
                    onOutgoingCallEnded(context, number, callStartTime, new Date());

                }
                break;
        }
        lastState = state;
    }


}
