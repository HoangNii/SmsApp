package com.colorsms.style.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.colorsms.style.App;
import com.colorsms.style.R;
import com.colorsms.style.helper.Style;
import com.colorsms.style.models.StyleModel;

import java.util.ArrayList;
import java.util.List;

public class ThemeStyleFirstActivity extends AppCompatActivity {

    final static ArrayList<StyleModel> models  = Style.ColorStyle.getStyleModels();

    public static void startPreview(Activity context){
        Intent intent = new Intent(context,ThemeStyleFirstActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{ setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); } catch(Exception ignore){}
        setContentView(R.layout.activity_theme_style_first);



        final ViewPager pager = findViewById(R.id.pager_preview);
        PreviewAdapter pagerAdapter = new PreviewAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        final Button btnApply = findViewById(R.id.bt_apply);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                btnApply.getBackground().setColorFilter(models.get(position+1).getStyleColor(), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnApply.getBackground().setColorFilter(models.get(1).getStyleColor(), PorterDuff.Mode.SRC_IN);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StyleModel model = models.get(pager.getCurrentItem()+1);
                Style.ColorStyle.setStyleId(model.getId());

                //Home
                Style.Home.setStyleColor(model.getStyleColor());
                Style.Home.setHomeTitleColor(model.getTitleColor());

                //
                Style.Font.setFontFamilyPosition(0);
                Style.Font.setFontSize(14);

                //
                Style.Background.setBackgroundBlackHomeAlpha(0);
                Style.Background.setBackgroundHomePosition(1);
                Style.Background.setBackgroundChatPosition(1);
                Style.Background.setHomeTextColor(model.getSmsHomeColor());

                Style.Bubble.toReloadBubbleStyle();

                Style.Avatar.setAvatarGravity(model.getAvatarGravity());
                Style.Avatar.setAvatarContentColor(model.getAvatarContentColor());

                Toast.makeText(ThemeStyleFirstActivity.this, "Done!", Toast.LENGTH_SHORT).show();

                firstOpen();

                App.get().restart();



            }
        });

    }

    public static class PreviewBackground extends Fragment{

        private int i;

        PreviewBackground(int i){
            this.i = i;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_style_preview,container,false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ImageView imgPreview = view.findViewById(R.id.img_preview);
            Glide.with(this)
                    .load(models.get(i+1).getPreview())
                    .into(imgPreview);
        }


    }

    public static class PreviewAdapter extends FragmentStatePagerAdapter{

        private List<PreviewBackground> mFragments;
        public PreviewAdapter(@NonNull FragmentManager fm) {
            super(fm,1);
            mFragments = new ArrayList<>();

            for(int i = 0; i< 10; i++){
                mFragments.add(new PreviewBackground(i));
            }

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new PreviewBackground(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }


    public static boolean isFirstOpen(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
        return preferences.getBoolean("is_first",true);
    }
    public static void firstOpen(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.get());
        preferences.edit().putBoolean("is_first",false).apply();
    }
}
