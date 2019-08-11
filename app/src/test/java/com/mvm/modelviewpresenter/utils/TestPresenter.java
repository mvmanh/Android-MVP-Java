package com.mvm.modelviewpresenter.utils;

import com.mvm.modelviewpresenter.R;
import com.mvm.modelviewpresenter.model.News;
import com.mvm.modelviewpresenter.repository.LoadNewsListener;
import com.mvm.modelviewpresenter.repository.NewsRepository;
import com.mvm.modelviewpresenter.ui.main.MvpContract;
import com.mvm.modelviewpresenter.ui.main.NewsPresenter;
import com.mvm.modelviewpresenter.utils.utils.DataUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.net.UnknownHostException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class TestPresenter {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public MvpContract.View view;

    @Mock
    public NewsRepository repository;

    @Captor
    public ArgumentCaptor<LoadNewsListener> listenerCaptor;

    @Captor
    public ArgumentCaptor<List<News>> newsCaptor;

    public MvpContract.Presenter presenter;

    @Before
    public void setup() {
        presenter = new NewsPresenter(repository);
        presenter.attachView(view);

    }

    @After
    public void tearDown() {
        presenter.destroyView();
    }

    @Test
    public void presenterShouldNotNull() {
        assertNotNull(presenter);
    }

    @Test
    public void whenClickingAnItem_ViewShouldDisplayIt() {
        presenter.performItemClick(4);
        verify(view).loadFullArticle(4);
    }

    @Test
    public void whenPresenterLoadArticles_RepositoryShouldBeCalled() {
        presenter.loadArticles(15);
        verify(repository).loadArticles(eq(15), any(LoadNewsListener.class));
    }

    @Test
    public void whenRepositoryResponse_ViewShouldDisplayResult() {
        presenter.loadArticles(10);
        verify(repository).loadArticles(eq(10), listenerCaptor.capture());

        // create fake data
        List<News> data = DataUtils.createNews(10);

        // mock behavior
        listenerCaptor.getValue().onNewsLoaded(data);
        verify(view).displayArticles(data);
    }

    @Test
    public void whenRepositoryResponse_ViewShouldDisplayExactNumberOfItems() {
        presenter.loadArticles(10);
        verify(repository).loadArticles(eq(10), listenerCaptor.capture());

        List<News> data = DataUtils.createNews(10);
        listenerCaptor.getValue().onNewsLoaded(data);

        verify(view).displayArticles(newsCaptor.capture());
        List<News> actualData = newsCaptor.getValue();

        assertEquals(10, actualData.size());
    }

    @Test
    public void whenRequestMoreThanMaxItems_ViewShouldReceiveAllItems() {
        presenter.loadArticles(100);
        verify(repository).loadArticles(eq(100), listenerCaptor.capture());

        // mock only 50 items are available
        listenerCaptor.getValue().onNewsLoaded(DataUtils.createNews(50));

        verify(view).displayArticles(newsCaptor.capture());
        assertEquals(50, newsCaptor.getValue().size());
    }

    @Test
    public void whenNetworkError_ViewShouldDisplayError() {
        presenter.loadArticles(400);
        verify(repository).loadArticles(eq(400), listenerCaptor.capture());

        LoadNewsListener listener = listenerCaptor.getValue();

        // mock error
        listener.onError(new UnknownHostException("Can not connect to web server"));
        verify(view).displayErrorMessaege(R.string.error_network_connection);
    }

    @Test
    public void whenInternalServerError_ViewShouldDisplayCorrectError() {
        presenter.loadArticles(500);
        verify(repository).loadArticles(eq(500), listenerCaptor.capture());

        // mock error
        listenerCaptor.getValue().onError(new IllegalAccessException("Internal server error has occured"));

        verify(view).displayErrorMessaege(R.string.error_internal_server);
    }
}
