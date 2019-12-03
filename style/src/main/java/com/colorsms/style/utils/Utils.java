package com.colorsms.style.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {

    public static int dpToPixel(float dp, Context context){
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
