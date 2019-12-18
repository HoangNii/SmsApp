package com.colorsms.style.callReport;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.colorsms.style.App;

public class CallReports {
    private static CallReports callReports;

    public static CallReports get() {
        if(callReports==null)
            callReports = new CallReports();
        return callReports;
    }


    public boolean isShowAds(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
        long i = preferences.getLong(getClass().getSimpleName()+"show_ads",1);
        i++;
        preferences.edit().putLong(getClass().getSimpleName()+"show_ads",i).apply();
        return i%2==0;
    }

    public boolean isShowCallReport(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
        return preferences.getBoolean(getClass().getSimpleName()+"show_call_report",true);
    }

    public void enableCallReport(boolean bl){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
        preferences.edit().putBoolean(getClass().getSimpleName()+"show_call_report",bl).apply();
    }
}
