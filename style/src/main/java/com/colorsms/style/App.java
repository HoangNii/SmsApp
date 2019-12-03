package com.colorsms.style;

import android.app.Application;

public abstract class App extends Application {

    private static App app;

    public static App get(){
        return app;
    }

    public static void setApp(App app) {
        App.app = app;
    }

    public abstract void restart();
}
