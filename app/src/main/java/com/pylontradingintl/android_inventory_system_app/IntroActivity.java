package com.pylontradingintl.android_inventory_system_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import androidx.viewpager.widget.ViewPager;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import static com.pylontradingintl.android_inventory_system_app.PreferenceUtils.*;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;

    Button btnGetStarted;
    Animation btnAnim;
    List<ScreenItem> mList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        hideSystemBars();


        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        mList.add(new ScreenItem("Fresh Food","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.",R.drawable.img1));
        mList.add(new ScreenItem("Fresh Delivery","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.",R.drawable.img2));
        mList.add(new ScreenItem("Easy Payment","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.",R.drawable.img3));

        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_getStarted);
        tabIndicator.setupWithViewPager(screenPager);

        btnNext.setOnClickListener(this);
        btnGetStarted.setOnClickListener(this);
        tabIndicator.addOnTabSelectedListener(this);
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

    private void nextPage(){
        int position = screenPager.getCurrentItem();
        if(position < mList.size()){
            position++;
            screenPager.setCurrentItem(position);
        }
        if(position == mList.size() - 1)
            loadLastScreen();
    }

    private void proceedToAdminPanel(){
        Intent mainActivity = new Intent(getApplicationContext(),AdminPanel.class);
        startActivity(mainActivity);
        savePreferenceData(getApplicationContext(), "intro-data", IS_INTRO_OPENED);
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                nextPage();
                break;
            case R.id.btn_getStarted:
                proceedToAdminPanel();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        proceedToAdminPanel();

    }

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
}