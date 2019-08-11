package com.mvm.modelviewpresenter;

import android.content.Context;

import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.mvm.modelviewpresenter.model.News;
import com.mvm.modelviewpresenter.ui.main.MainActivity;
import com.mvm.modelviewpresenter.ui.webview.WebViewActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityShownTest {

    @Rule
    public IntentsTestRule<MainActivity> activityTestRule = new IntentsTestRule<>(MainActivity.class);

    private Context context;
    private MainActivity activity;

    @Before
    public void setUp(){
        context = activityTestRule.getActivity();
        activity = activityTestRule.getActivity();

    }

    @Test
    public void newActivityShouldShown_whenClickingOnItem() throws InterruptedException {

        Thread.sleep(5000);

        onView(withId(R.id.btnRetry))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btnRetry))
                .perform(click());

        Thread.sleep(5000);

        onView(withId(R.id.listview))
                .check(matches(isDisplayed()));

        onData(instanceOf(News.class))
                .atPosition(3)
                .check(matches(notNullValue())).perform(click());

         intended(IntentMatchers.hasComponent(WebViewActivity.class.getName()));
    }

}
