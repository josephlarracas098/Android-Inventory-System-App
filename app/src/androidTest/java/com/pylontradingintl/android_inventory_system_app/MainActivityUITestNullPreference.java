package com.pylontradingintl.android_inventory_system_app;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MainActivityUITestNullPreference {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    Instrumentation.ActivityMonitor adminPanelActivityMonitor = getInstrumentation().addMonitor(Category.class.getName(),null,false);
    Instrumentation.ActivityMonitor introActivityMonitor = getInstrumentation().addMonitor(IntroActivity.class.getName(),null,false);
    @Test
    public void test_Main_Go_Intro_Go_AdminPanel(){
        onView(withId(R.id.id_login_email)).perform(typeText("joseph.larracas098@gmail.com"));
        onView(withId(R.id.id_login_password)).perform(typeText("123456"));
        onView(withId(R.id.id_login)).perform(click());
        onView(withId(R.id.id_progress_dialog)).check(matches(isDisplayed()));

        Activity introActivity = getInstrumentation().waitForMonitorWithTimeout(introActivityMonitor, 5000);
        assertNotNull(introActivity);

        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_getStarted)).perform(click());

        Activity adminPanelActivity = getInstrumentation().waitForMonitorWithTimeout(adminPanelActivityMonitor, 5000);
        assertNotNull(adminPanelActivity);
        adminPanelActivity.finish();
    }

    @Test
    public void test_Main_Go_Intro_Go_AdminPanel_Press_Back_To_Exit(){
        onView(withId(R.id.id_login_email)).perform(typeText("joseph.larracas098@gmail.com"));
        onView(withId(R.id.id_login_password)).perform(typeText("123456"));
        onView(withId(R.id.id_login)).perform(click());
        onView(withId(R.id.id_progress_dialog)).check(matches(isDisplayed()));

        Activity introActivity = getInstrumentation().waitForMonitorWithTimeout(introActivityMonitor, 5000);
        assertNotNull(introActivity);

        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_getStarted)).perform(click());

        Activity adminPanelActivity = getInstrumentation().waitForMonitorWithTimeout(adminPanelActivityMonitor, 5000);
        assertNotNull(adminPanelActivity);

        pressBack();
        onView(withText("Exit Application?")).check(matches(isDisplayed()));
    }

    @Test
    public void test_Main_Go_Intro_Press_Back_To_AdminPanel(){
        onView(withId(R.id.id_login_email)).perform(typeText("joseph.larracas098@gmail.com"));
        onView(withId(R.id.id_login_password)).perform(typeText("123456"));
        onView(withId(R.id.id_login)).perform(click());
        onView(withId(R.id.id_progress_dialog)).check(matches(isDisplayed()));

        Activity introActivity = getInstrumentation().waitForMonitorWithTimeout(introActivityMonitor, 5000);
        assertNotNull(introActivity);

        pressBack();
        Activity adminPanelActivity = getInstrumentation().waitForMonitorWithTimeout(adminPanelActivityMonitor, 5000);
        assertNotNull(adminPanelActivity);
        adminPanelActivity.finish();
    }

}