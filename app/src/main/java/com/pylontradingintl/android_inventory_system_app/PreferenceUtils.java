package com.pylontradingintl.android_inventory_system_app;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class PreferenceUtils  {

    public static final String IS_INTRO_OPENED = "isIntroOpened";
    public static final String IS_USER_LOGGED = "isUserLogged";


    public static boolean containsPreference(Context context , String name , String key) {
        SharedPreferences pref = context.getSharedPreferences(name,MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean(key,false);
        return isIntroActivityOpenedBefore;
    }

    public static void savePreferenceData(Context context , String name , String key) {
        SharedPreferences preferences = context.getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,true);
        editor.commit();
    }
}
