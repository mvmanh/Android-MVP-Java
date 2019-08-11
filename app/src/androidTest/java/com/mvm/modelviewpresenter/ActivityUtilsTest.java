package com.mvm.modelviewpresenter;

import android.content.Context;

import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.mvm.modelviewpresenter.model.News;
import com.mvm.modelviewpresenter.ui.main.MainActivity;
import com.mvm.modelviewpresenter.ui.webview.WebViewActivity;
import com.mvm.modelviewpresenter.utils.ActivityUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static androidx.test.espresso.intent.Intents.intended;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ActivityUtilsTest {

    @Rule
    public IntentsTestRule<MainActivity> activityIntentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Mock
    public News news;

    private Context context;

    @Before
    public void setUp() {
        context = activityIntentsTestRule.getActivity();
    }

    @Test
    public void shouldOpenActivity() {
        ActivityUtils.openActivity(context, WebViewActivity.class);
        intended(IntentMatchers.hasComponent(WebViewActivity.class.getName()));
    }

    @Test
    public void shouldOpenActivityWithExtra() {
        ActivityUtils.openActivityExtra(context, WebViewActivity.class,
                WebViewActivity.ARTICLE, news);
        intended(IntentMatchers.hasComponent(WebViewActivity.class.getName()));
        intended(IntentMatchers.hasExtraWithKey(WebViewActivity.ARTICLE));
    }
}
