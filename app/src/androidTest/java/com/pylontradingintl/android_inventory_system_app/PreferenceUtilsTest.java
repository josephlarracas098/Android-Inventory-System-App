package com.pylontradingintl.android_inventory_system_app;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

public class PreferenceUtilsTest {
    @Test
    public void emptyIntroPreferenceReturnsFalse(){
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = targetContext.getSharedPreferences("intro-data",Context.MODE_PRIVATE);
        assertFalse(sharedPreferences.getBoolean(PreferenceUtils.IS_INTRO_OPENED, false));

    }

    @Test
    public void emptyLoginPreferenceReturnsFalse(){
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = targetContext.getSharedPreferences("logged",Context.MODE_PRIVATE);
        assertFalse(sharedPreferences.getBoolean(PreferenceUtils.IS_USER_LOGGED, false));

    }
}