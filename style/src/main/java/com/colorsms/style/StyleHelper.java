package com.colorsms.style;

public class StyleHelper {

    private static StyleHelper helper;

    public static StyleHelper get(){
        if(helper==null)
            helper = new StyleHelper();
        return helper;
    }
    

}
