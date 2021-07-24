package com.pylontradingintl.android_inventory_system_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import androidx.viewpager.widget.ViewPager;


import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.view.WindowInsetsCompat.Type.systemBars;
import static androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnim;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        hideSystemBars();

        if(PreferenceUtils.containsPreference(getApplicationContext())){
            Intent mainActivity = new Intent(getApplicationContext(),AdminPanel.class);
            startActivity(mainActivity);
            finish();
        }

        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);


        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Fresh Food","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.",R.drawable.img1));
        mList.add(new ScreenItem("Fresh Delivery","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.",R.drawable.img2));
        mList.add(new ScreenItem("Easy Payment","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.",R.drawable.img3));
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_getStarted);
        tabIndicator.setupWithViewPager(screenPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if(position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if(position == mList.size() - 1){
                    loadLastScreen();
                }
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size() - 1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getApplicationContext(),AdminPanel.class);
                startActivity(mainActivity);
                PreferenceUtils.savePreferenceData(getApplicationContext());
                finish();
            }
        });
    }

    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStarted.setAnimation(btnAnim);
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat insetsControllerCompat = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsControllerCompat.setSystemBarsBehavior(BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        insetsControllerCompat.hide(systemBars());
    }

    private void showSystemBars() {
        WindowInsetsControllerCompat insetsControllerCompat = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsControllerCompat.show(systemBars());
    }
}