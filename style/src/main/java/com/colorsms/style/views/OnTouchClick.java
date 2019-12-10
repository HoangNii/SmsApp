package com.colorsms.style.views;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

public class OnTouchClick implements View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
            view.animate()
                    .alpha(0.5f)
                    .setDuration(50)
                    .start();
        }else if(motionEvent.getAction()==MotionEvent.ACTION_UP||motionEvent.getAction()==MotionEvent.ACTION_CANCEL){
            view.animate()
                    .alpha(1f)
                    .setDuration(50)
                    .start();
        }
        return false;
    }
}
