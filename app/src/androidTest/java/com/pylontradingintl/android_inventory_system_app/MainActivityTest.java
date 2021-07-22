package com.pylontradingintl.android_inventory_system_app;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(AdminPanel.class.getName(),null,false);
    @Test
    public void testLoginToGotoAdminPanel(){
        onView(withId(R.id.id_login_email)).perform(typeText("joseph.larracas098@gmail.com"));
        onView(withId(R.id.id_login_password)).perform(typeText("123456"));
        onView(withId(R.id.id_login)).perform(click());
        onView(withId(R.id.id_progress_dialog)).check(matches(isDisplayed()));
        Activity activity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(activity);
        activity.finish();
    }


}