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
import static org.junit.Assert.*;

public class RegisterUITest {
    @Rule
    public ActivityScenarioRule<Register> registerActivityScenarioRule = new ActivityScenarioRule<>(Register.class);
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
    @Test
    public void registerCredentialsAccepted(){
        onView(withId(R.id.id_register_companyName)).perform(typeText("joseph"));
        onView(withId(R.id.id_register_email)).perform(typeText("joseph.larracas098@gmail.com"));
        onView(withId(R.id.id_register_password)).perform(typeText("123456"));
        onView(withId(R.id.id_register_confirm_password)).perform(typeText("123456"));
        onView(withId(R.id.id_register)).perform(click());
        onView(withId(R.id.id_progress_dialog)).check(matches(isDisplayed()));
        Activity activity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(activity);
        activity.finish();
    }
    @Test
    public void wrongEmailPatterNotAccepted(){
        onView(withId(R.id.id_register_companyName)).perform(typeText("joseph"));
        onView(withId(R.id.id_register_email)).perform(typeText("chopes890gmail.com"));
        onView(withId(R.id.id_register_password)).perform(typeText("123456"));
        onView(withId(R.id.id_register_confirm_password)).perform(typeText("123456"));
        onView(withId(R.id.id_register)).perform(click());
        Activity activity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNull(activity);
    }

    @Test
    public void emptyUsernameNotAccepted(){
        onView(withId(R.id.id_register_companyName)).perform(typeText(""));
        onView(withId(R.id.id_register_email)).perform(typeText("chopes890@gmail.com"));
        onView(withId(R.id.id_register_password)).perform(typeText("123456"));
        onView(withId(R.id.id_register_confirm_password)).perform(typeText("123456"));
        onView(withId(R.id.id_register)).perform(click());
        Activity activity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNull(activity);
    }

    @Test
    public void passwordLengthLessThanSixNotAccepted(){
        onView(withId(R.id.id_register_companyName)).perform(typeText(""));
        onView(withId(R.id.id_register_email)).perform(typeText("chopes890@gmail.com"));
        onView(withId(R.id.id_register_password)).perform(typeText("1234"));
        onView(withId(R.id.id_register)).perform(click());
        Activity activity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNull(activity);
    }

    @Test
    public void emptyEmailNotAccepted(){
        onView(withId(R.id.id_register_companyName)).perform(typeText("joseph"));
        onView(withId(R.id.id_register_email)).perform(typeText(""));
        onView(withId(R.id.id_register_password)).perform(typeText("123456"));
        onView(withId(R.id.id_register_confirm_password)).perform(typeText("123456"));
        onView(withId(R.id.id_register)).perform(click());
        Activity activity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNull(activity);
    }

    @Test
    public void emptyPasswordNotAccepted(){
        onView(withId(R.id.id_register_companyName)).perform(typeText("joseph"));
        onView(withId(R.id.id_register_email)).perform(typeText("chopes890@gmail.com"));
        onView(withId(R.id.id_register_password)).perform(typeText(""));
        onView(withId(R.id.id_register)).perform(click());
        Activity activity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNull(activity);
    }

    @Test
    public void unMatchPasswordsNotAccepted(){
        onView(withId(R.id.id_register_companyName)).perform(typeText("joseph"));
        onView(withId(R.id.id_register_email)).perform(typeText("chopes890@gmail.com"));
        onView(withId(R.id.id_register_password)).perform(typeText("123456"));
        onView(withId(R.id.id_register_confirm_password)).perform(typeText(""));
        onView(withId(R.id.id_register)).perform(click());
        Activity activity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNull(activity);
    }


}