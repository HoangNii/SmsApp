package com.colorsms.style.views;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;

public class DrawerLayout extends androidx.drawerlayout.widget.DrawerLayout {
    public DrawerLayout(@NonNull Context context) {
        super(context);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean openDrawer(){
        if(!isDrawerOpen(GravityCompat.START)){
            openDrawer(GravityCompat.START);
            return true;
        }else return false;
    }

    public boolean closeDrawer(){
        if(isDrawerOpen(GravityCompat.START)){
            closeDrawer(GravityCompat.START);
            return true;
        }else return false;
    }
}
