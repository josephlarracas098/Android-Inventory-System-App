package com.pylontradingintl.android_inventory_system_app;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class PreferenceUtils  {
    private static final String IS_INTRO_OPENED = "isIntroOpened";

    public static boolean containsPreference(Context context) {
        SharedPreferences pref = context.getSharedPreferences("intro-data",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean(IS_INTRO_OPENED,false);
        return isIntroActivityOpenedBefore;
    }

    public static void savePreferenceData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("intro-data",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_INTRO_OPENED,true);
        editor.commit();
    }
}
