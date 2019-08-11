package com.mvm.modelviewpresenter;

import android.content.Context;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.mvm.modelviewpresenter.ui.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private Context context;
    private MainActivity activity;

    @Before
    public void setUp() {
        context = activityTestRule.getActivity();
        activity = activityTestRule.getActivity();

        IdlingRegistry.getInstance().register(activity.getIdlingResourceInTest());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(activity.getIdlingResourceInTest());
    }

    @Test
    public void shouldDisplayRequiredTitle() {
        assertEquals(context.getString(R.string.mainActivity_label),
                activity.getSupportActionBar().getTitle());
    }

    @Test
    public void onStartup_viewVisibilityShouldValid() {
        onView(withId(R.id.loading_screen))
                .check(matches(isDisplayed()));

        onView(withId(R.id.listview))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.progress_horizontal))
                .check(matches(isDisplayed()));

        onView(withId(R.id.tvMessage))
                .check(matches(isDisplayed()));

        onView(withId(R.id.tvMessage))
                .check(matches(withText(context.getString(R.string.message_loading_content))));

        onView(withId(R.id.btnRetry))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void afterLoaded_RetryButtonMustBeShown() throws InterruptedException {
        // Thread.sleep(5000);

        onView(withId(R.id.loading_screen))
                .check(matches(isDisplayed()));

        onView(withId(R.id.listview))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.progress_horizontal))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.tvMessage))
                .check(matches(isDisplayed()));

        onView(withId(R.id.tvMessage))
                .check(matches(withText(context.getString(R.string.error_network_connection))));

        onView(withId(R.id.btnRetry))
                .check(matches(isDisplayed()));
    }

    @Test
    public void afterRetry_ListviewShouldShown() throws InterruptedException {
        // Thread.sleep(5000);

        onView(withId(R.id.btnRetry))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btnRetry))
                .perform(click());

        // Thread.sleep(5000);

        onView(withId(R.id.listview))
                .check(matches(isDisplayed()));
    }
}
