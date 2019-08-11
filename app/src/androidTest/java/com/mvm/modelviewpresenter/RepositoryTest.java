package com.mvm.modelviewpresenter;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.mvm.modelviewpresenter.model.News;
import com.mvm.modelviewpresenter.repository.LoadNewsListener;
import com.mvm.modelviewpresenter.repository.NewsRepository;
import com.mvm.modelviewpresenter.utils.AppExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.net.UnknownHostException;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;


@RunWith(AndroidJUnit4ClassRunner.class)
public class RepositoryTest {

    private static final int MAX_NEWS_FROM_SOURCE = 20;
    private static final int REPOSITORY_LOAD_TIME = 4000;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public LoadNewsListener listener;

    @Captor
    public ArgumentCaptor<List<News>> newsCaptor;

    @Captor
    public ArgumentCaptor<Exception> exceptionCaptor;

    private Context context;
    private AppExecutor executor;
    private NewsRepository repository;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        executor = AppExecutor.getInstance();
        repository = new NewsRepository(context, executor);
    }

    @Test
    public void shouldLoadDataSuccess() {
        repository.loadArticles(15, listener);
        verify(listener, timeout(REPOSITORY_LOAD_TIME)).onNewsLoaded(anyList());
    }

    @Test
    public void maxItemsShouldBeReturned_WhenRequestingMuch() {
        repository.loadArticles(1000, listener);
        verify(listener, timeout(REPOSITORY_LOAD_TIME)).onNewsLoaded(newsCaptor.capture());

        assertThat(newsCaptor.getValue().size()).isEqualTo(MAX_NEWS_FROM_SOURCE);
    }

    @Test
    public void whenRequest404Items_gotUnknownHostException() {
        repository.loadArticles(404, listener);
        verify(listener, timeout(REPOSITORY_LOAD_TIME)).onError(any(UnknownHostException.class));
    }

    @Test
    public void UnknownHostException_ShouldContainRequiredMessage() {
        repository.loadArticles(404, listener);
        verify(listener, timeout(REPOSITORY_LOAD_TIME))
                .onError(exceptionCaptor.capture());

        assertThat(exceptionCaptor.getValue().getMessage())
                .isEqualTo("Can not connect to web server");
    }

    @Test
    public void whenRequest505Items_gotIllegalAccessException() {
        repository.loadArticles(500, listener);
        verify(listener, timeout(REPOSITORY_LOAD_TIME)).onError(any(IllegalAccessException.class));
    }

    @Test
    public void IllegalAccessException_ShouldContainRequiredMessage() {
        repository.loadArticles(500, listener);
        verify(listener, timeout(REPOSITORY_LOAD_TIME)).onError(exceptionCaptor.capture());

        assertThat(exceptionCaptor.getValue().getMessage()).isEqualTo("Internal server error has occured");
    }
}
