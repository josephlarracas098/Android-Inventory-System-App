package com.pylontradingintl.android_inventory_system_app;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityUITestValidation {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void loginEmptyEmailNotAccepted(){
        onView(withId(R.id.id_login_email)).perform(typeText(""));
        onView(withId(R.id.id_login_password)).perform(typeText("123456"));
        onView(withId(R.id.id_login)).perform(click());
        onView(withId(R.id.id_login_email)).check(matches(hasErrorText("Email is Required")));
    }

    @Test
    public void emptyPasswordNotAccepted(){
        onView(withId(R.id.id_login_email)).perform(typeText("joseph.larracas098@gmail.com"));
        onView(withId(R.id.id_login_password)).perform(typeText(""));
        onView(withId(R.id.id_login)).perform(click());
        onView(withId(R.id.id_login_password)).check(matches(hasErrorText("Please provide password")));
    }
}
