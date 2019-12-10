package com.colorsms.style.helper;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.Gravity;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import com.colorsms.style.App;
import com.colorsms.style.R;
import com.colorsms.style.models.StyleModel;
import java.util.ArrayList;
public class Style {

    public static class ColorStyle{

        public static ArrayList<StyleModel> getStyleModels(){
            ArrayList<StyleModel> styleModels = new ArrayList<>();
            styleModels.add(new StyleModel(
                    0, //id
                    "Default",                                                 //name
                    0,                                                    //background
                    ContextCompat.getColor(App.get(),R.color.colorPrimary),         /// style Color
                    R.drawable.bg_oval,                                                   // frame avatar
                    Gravity.BOTTOM,                                                     // avatar gravity
                    new float[]{4,4,4,4},                                             // sms avatar padding
                    new int[]{13,13,13,13},                                             // chat avatar padding
                    Color.WHITE,                                             // avatar content color
                    ContextCompat.getColor(App.get(),R.color.colorPrimary),                        // unread Color
                    Color.parseColor("#222222"),                        // sms color
                    R.drawable.bubble_default_inbox_1,                        // sms color
                    Color.parseColor("#3AB54A"),
                    Color.WHITE,
                    R.drawable.bubble_default_send_1,
                    Color.parseColor("#FBB03B"),
                    Color.WHITE,
                    R.drawable.t0chat,Color.WHITE
            ));
            styleModels.add(new StyleModel(
                    1,"Theme 1",R.drawable.t1_background,Color.parseColor("#F08B9F"),R.drawable.t1_avatar,
                    Gravity.BOTTOM,new float[]{14,13,12,14},new int[]{17,15,15,15},Color.WHITE,
                    Color.parseColor("#E22348"),Color.parseColor("#222222"),
                    R.drawable.t1_bb_inbox,Color.TRANSPARENT,Color.WHITE,
                    R.drawable.t1_bb_send,Color.TRANSPARENT,Color.WHITE,R.drawable.t1chat,Color.BLACK
            ));
            styleModels.add(new StyleModel(
                    2,"Theme 2",R.drawable.t2_background,Color.parseColor("#D13D5A"),R.drawable.t2_avatar,
                    Gravity.TOP,new float[]{12,11,11,11},new int[]{17,15,15,15},Color.WHITE,
                    Color.parseColor("#D13D5A"),Color.WHITE,
                    R.drawable.t2_bb_inbox,Color.TRANSPARENT,Color.WHITE,
                    R.drawable.t2_bb_send,Color.TRANSPARENT,Color.WHITE,R.drawable.t2chat,Color.WHITE
            ));

            styleModels.add(new StyleModel(
                    3,"Theme 3",R.drawable.t3_background,Color.parseColor("#CCAD05"),R.drawable.t3_avatar,
                    Gravity.TOP,new float[]{4,4,4,4},new int[]{13,13,13,13},Color.WHITE,
                    Color.parseColor("#CCAD05"),Color.WHITE,
                    R.drawable.t3_bb_inbox,Color.TRANSPARENT,Color.WHITE,
                    R.drawable.t3_bb_send,Color.TRANSPARENT,Color.WHITE,R.drawable.t3chat,Color.WHITE
            ));

            styleModels.add(new StyleModel(
                    4,"Theme 4",R.drawable.t4_background,Color.parseColor("#E9741A"),R.drawable.t4_avatar,
                    Gravity.CENTER_VERTICAL,new float[]{8,10,10,8},new int[]{13,15,15,15},Color.WHITE,
                    Color.parseColor("#E9741A"),Color.BLACK,
                    R.drawable.t4_bb_inbox,Color.TRANSPARENT,Color.WHITE,
                    R.drawable.t4_bb_send,Color.TRANSPARENT,Color.WHITE,R.drawable.t4chat,Color.BLACK
            ));

            styleModels.add(new StyleModel(
                    5,"Theme 5",R.drawable.t5_background,Color.parseColor("#FFD600"),R.drawable.t5_avatar,
                    Gravity.CENTER_VERTICAL,new float[]{13,0,13,13},new int[]{15,15,15,30},Color.WHITE,
                    Color.parseColor("#FFD600"),Color.WHITE,
                    R.drawable.t5_bb_inbox,Color.TRANSPARENT,Color.BLACK,
                    R.drawable.t5_bb_send,Color.TRANSPARENT,Color.BLACK,R.drawable.t5chat,Color.WHITE
            ));

            styleModels.add(new StyleModel(
                    6,"Theme 6",R.drawable.t6_background,Color.parseColor("#A639DD"),R.drawable.t6_avatar,
                    Gravity.TOP,new float[]{8,8,7,7},new int[]{15,15,15,15},Color.WHITE,
                    Color.parseColor("#A639DD"),Color.WHITE,
                    R.drawable.t6_bb_inbox,Color.TRANSPARENT,Color.WHITE,
                    R.drawable.t6_bb_send,Color.TRANSPARENT,Color.WHITE,R.drawable.t6chat,Color.WHITE
            ));

            styleModels.add(new StyleModel(
                    7,"Theme 7",R.drawable.t7_background,Color.parseColor("#A639DD"),R.drawable.t7_avatar,
                    Gravity.TOP,new float[]{5.5f,5.5f,6,6},new int[]{15,15,15,15},Color.WHITE,
                    Color.parseColor("#A639DD"),Color.parseColor("#00258D"),
                    R.drawable.t7_bb_inbox,Color.TRANSPARENT,Color.parseColor("#00258D"),
                    R.drawable.t7_bb_send,Color.TRANSPARENT,Color.parseColor("#00258D"),R.drawable.t7chat,Color.parseColor("#00258D")
            ));

            styleModels.add(new StyleModel(
                    8,"Theme 8",R.drawable.t8_background,Color.parseColor("#0091EA"),R.drawable.t8_avatar,
                    Gravity.TOP,new float[]{3,3,3,3},new int[]{15,15,15,15},Color.WHITE,
                    Color.parseColor("#0091EA"),Color.parseColor("#00258D"),
                    R.drawable.t8_bb_inbox,Color.TRANSPARENT,Color.WHITE,
                    R.drawable.t8_bb_send,Color.TRANSPARENT,Color.WHITE,R.drawable.t8chat,Color.WHITE
            ));

            styleModels.add(new StyleModel(
                    9,"Theme 9",R.drawable.t9_background,Color.parseColor("#F0834C"),R.drawable.t9_avatar,
                    Gravity.BOTTOM,new float[]{12,10,12,12},new int[]{15,15,15,15},Color.parseColor("#F0834C"),
                    Color.parseColor("#F0834C"),Color.BLACK,
                    R.drawable.t9_bb_inbox,Color.TRANSPARENT,Color.BLACK,
                    R.drawable.t9_bb_send,Color.TRANSPARENT,Color.BLACK,R.drawable.t9chat,Color.BLACK
            ));

            styleModels.add(new StyleModel(
                    10,"Theme 10",R.drawable.t10_background,Color.parseColor("#FFD600"),R.drawable.t10_avatar,
                    Gravity.CENTER_VERTICAL,new float[]{9,4,3,4},new int[]{19,13,13,13},Color.WHITE,
                    Color.parseColor("#FFD600"),Color.WHITE,
                    R.drawable.t10_bb_inbox,Color.TRANSPARENT,Color.WHITE,
                    R.drawable.t10_bb_send,Color.TRANSPARENT,Color.WHITE,R.drawable.t10chat,Color.WHITE
            ));


            styleModels.add(new StyleModel(
                    11,"Theme 11",R.drawable.t11_background,Color.parseColor("#AF2222"),R.drawable.t11_avatar,
                    Gravity.BOTTOM,new float[]{5,5,5,5},new int[]{13,13,13,13},Color.BLACK,
                    Color.parseColor("#AF2222"),Color.WHITE,
                    R.drawable.t11_bb_inbox,Color.TRANSPARENT,Color.BLACK,
                    R.drawable.t11_bb_send,Color.TRANSPARENT,Color.BLACK,R.drawable.t11chat,Color.WHITE
            ));


            styleModels.add(new StyleModel(
                    12,"Theme 12",R.drawable.t12_background,Color.parseColor("#F498BD"),R.drawable.t12_avatar,
                    Gravity.CENTER_VERTICAL,new float[]{15,18,15,13},new int[]{13,13,18,13},Color.parseColor("#F498BD"),
                    Color.parseColor("#F498BD"),Color.WHITE,
                    R.drawable.t12_bb_inbox,Color.TRANSPARENT,Color.WHITE,
                    R.drawable.t12_bb_send,Color.TRANSPARENT,Color.WHITE,R.drawable.t12chat,Color.WHITE
            ));

            styleModels.add(new StyleModel(
                    13,"Theme 13",R.drawable.t13_background,Color.parseColor("#ED1B24"),R.drawable.t13_avatar,
                    Gravity.BOTTOM,new float[]{14,14,14,14},new int[]{13,13,13,13},Color.parseColor("#ED1B24"),
                    Color.WHITE,Color.WHITE,
                    R.drawable.t13_bb_inbox,Color.TRANSPARENT,Color.BLACK,
                    R.drawable.t13_bb_send,Color.TRANSPARENT,Color.BLACK,R.drawable.t13chat,Color.WHITE
            ));

            styleModels.add(new StyleModel(
                    14,"Theme 14",R.drawable.t14_background,Color.parseColor("#ED1B24"),R.drawable.t14_avatar,
                    Gravity.BOTTOM,new float[]{5,5,5,5},new int[]{13,13,13,13},Color.WHITE,
                    Color.WHITE,Color.WHITE,
                    R.drawable.t14_bb_inbox,Color.TRANSPARENT,Color.WHITE,
                    R.drawable.t14_bb_send,Color.TRANSPARENT,Color.WHITE,R.drawable.t14chat,Color.WHITE
            ));

            styleModels.add(new StyleModel(
                    15,"Theme 15",R.drawable.t15_background,Color.parseColor("#26FDFF"),R.drawable.t15_avatar,
                    Gravity.BOTTOM,new float[]{15,21,24,15},new int[]{11,21,15,13},Color.WHITE,
                    Color.parseColor("#26FDFF"),Color.WHITE,
                    R.drawable.t15_bb_inbox,Color.TRANSPARENT,Color.WHITE,
                    R.drawable.t15_bb_send,Color.TRANSPARENT,Color.WHITE,R.drawable.t15chat,Color.WHITE
            ));

            return styleModels;
        }

        public static void setStyleId(int id){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("style_id",id).apply();
        }

        public static int getStyleId(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("style_id",1);
        }
    }

    public static class Home{
        public static void setStyleColor(int color){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("style_color",color).apply();
        }

        public static int getStyleColor(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("style_color", ContextCompat.getColor(App.get(), R.color.colorPrimary));
        }

        public static void setHomeTitleColor(int color){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("home_title_color",color).apply();
        }

        public static int getHomeTitleColor(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("home_title_color",Color.WHITE);
        }
    }


    public static class Bubble{
        private static int[][] bubbleDefaultList = new int[][]{
                {R.drawable.bubble_default_inbox_1,R.drawable.bubble_default_send_1},
                {R.drawable.bubble_default_inbox_2,R.drawable.bubble_default_send_2},
                {R.drawable.bubble_default_inbox_3,R.drawable.bubble_default_send_3},
                {R.drawable.bubble_default_inbox_4,R.drawable.bubble_default_send_4},
                {R.drawable.bubble_default_inbox_5,R.drawable.bubble_default_send_5},
        };

        public static int[][] getBubbleDefaultList() {
            return bubbleDefaultList;
        }

        public static boolean isUseBubbleShapeDefault(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getBoolean("use_bubble_shape_default",true);
        }

        public static void setUseBubbleShapeDefault(boolean bl){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putBoolean("use_bubble_shape_default",bl).apply();
        }

        public static void setBubbleShapeDefaultPosition(int position){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("bubble_shape_position",position).apply();
        }

        public static int getBubbleShapeDefaultPosition(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("bubble_shape_position",0);
        }

        public static void setBubbleShapeReceivedColor(int color){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("bubble_shape_default_received_color",color).apply();
        }

        public static int getBubbleShapeReceivedColor(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("bubble_shape_default_received_color",Color.parseColor("#3AB54A"));
        }

        public static void setBubbleShapeSentColor(int position){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("bubble_shape_default_sent_color",position).apply();
        }

        public static int getBubbleShapeSentColor(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("bubble_shape_default_sent_color",Color.parseColor("#FBB03B"));
        }



        public static void setBubbleTextReceivedColor(int color){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("bubble_text_default_received_color",color).apply();
        }

        public static int getBubbleTextReceivedColor(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("bubble_text_default_received_color",Color.WHITE);
        }

        public static void setBubbleTextSentColor(int position){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("bubble_text_default_sent_color",position).apply();
        }

        public static int getBubbleTextSentColor(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("bubble_text_default_sent_color",Color.WHITE);
        }


        public static int[] getBubbleShapeDefault(){
            int position = getBubbleShapeDefaultPosition();
            return bubbleDefaultList[position];
        }

        public static void toReloadBubbleStyle() {
            StyleModel model = Style.ColorStyle.getStyleModels().get(Style.ColorStyle.getStyleId());
            if(model.getId()==0){
                Style.Bubble.setUseBubbleShapeDefault(true);
                Style.Bubble.setBubbleShapeDefaultPosition(0);
                Style.Bubble.setBubbleTextReceivedColor(Color.WHITE);
                Style.Bubble.setBubbleTextSentColor(Color.WHITE);
                Style.Bubble.setBubbleShapeSentColor(Color.parseColor("#3AB54A"));
                Style.Bubble.setBubbleShapeReceivedColor(Color.parseColor("#FBB03B"));
                Style.Avatar.setAvatarGravity(Gravity.BOTTOM);
            }else {
                Style.Bubble.setUseBubbleShapeDefault(false);
                Style.Bubble.setBubbleTextReceivedColor(model.getBbChatInboxTextColor());
                Style.Bubble.setBubbleTextSentColor(model.getBbChatSendTextColor());
                Style.Bubble.setBubbleShapeSentColor(Color.TRANSPARENT);
                Style.Bubble.setBubbleShapeReceivedColor(Color.TRANSPARENT);
                Style.Avatar.setAvatarGravity(model.getAvatarGravity());
            }
        }
    }

    public static class Font{

        public static int[] fontDefaultList =
                new int[]{0,R.font.dancing,
                        R.font.garamond,R.font.pamega,
                        R.font.rukola,R.font.script,
                        R.font.soupof,R.font.wallows};

        public static String[] fontDefaultListName =
                new String[]{"Default","Dancing",
                        "Garamond","Pamega",
                        "Rukola","Script",
                        "Soupof","Wallows"};

        public static void setFontFamilyPosition(int position){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("font_family_position",position).apply();
        }

        public static int getFontFamilyPosition(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("font_family_position",0);
        }

        public static Typeface getFontTypeFace(int positionFont){
            Typeface typeface;
            if(positionFont==0){
                typeface = Typeface.DEFAULT;
            }else typeface = ResourcesCompat.getFont(App.get(),fontDefaultList[positionFont]);
            return typeface;
        }


        public static void setFontSize(int size){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("font_size",size).apply();
        }

        public static int getFontSize(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("font_size",14);
        }
    }

    public static class Background{

        //0 url, 1 style
        public static int[] backgroundList = new int[]{
                0,1, R.drawable.a_1,R.drawable.a_2,
                R.drawable.a_3,R.drawable.a_4,
                R.drawable.a_5,R.drawable.a_6,
                R.drawable.a_7,R.drawable.a_8};

        public static void setBackgroundHomePosition(int position){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("background_home_position",position).apply();
        }

        public static int getBackgroundHomePosition(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("background_home_position",1);
        }

        public static void setBackgroundChatPosition(int position){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("background_chat_position",position).apply();
        }

        public static int getBackgroundChatPosition(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("background_chat_position",1);
        }

        public static void setBackgroundHomeUri(String uri){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putString("background_home_uri",uri).apply();
        }

        public static String getBackgroundHomeUri(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getString("background_home_uri","");
        }

        public static void setBackgroundChatUri(String uri){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putString("background_chat_uri",uri).apply();
        }

        public static String getBackgroundChatUri(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getString("background_chat_uri","");
        }

        public static void setHomeTextColor(int color){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("home_text_color",color).apply();
        }

        public static int getHomeTextColor(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("home_text_color",Color.parseColor("#222222"));
        }

        public static void setBackgroundBlackHomeAlpha(float alpha){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putFloat("background_home_alpha",alpha).apply();
        }

        public static float getBackgroundBlackHomeAlpha(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getFloat("background_home_alpha",0f);
        }
    }

    public static class Avatar{

        public static void setAvatarContentColor(int color){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("avatar_content_color",color).apply();
        }

        public static int getAvatarContentColor(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("avatar_content_color",ContextCompat.getColor(App.get(),R.color.colorPrimary));
        }

        public static void setAvatarGravity(int gravity){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            preferences.edit().putInt("avatar_gravity",gravity).apply();
        }

        public static int getAvatarGravity(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
            return preferences.getInt("avatar_gravity",Gravity.CENTER_VERTICAL);
        }


    }
}
