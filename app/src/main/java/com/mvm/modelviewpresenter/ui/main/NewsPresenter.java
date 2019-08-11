package com.mvm.modelviewpresenter.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mvm.modelviewpresenter.R;
import com.mvm.modelviewpresenter.model.News;
import com.mvm.modelviewpresenter.repository.LoadNewsListener;
import com.mvm.modelviewpresenter.repository.NewsRepository;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class NewsPresenter implements MvpContract.Presenter{

    @Nullable
    private MvpContract.View mView;
    private NewsRepository mRepository;

    @Inject
    public NewsPresenter(@NonNull NewsRepository repository) {
        if (repository == null) {
            throw new NullPointerException("News Repository must not be null");
        }
        this.mRepository = repository;
    }

    @Override
    public void loadArticles(int numOfArticles) {
        mRepository.loadArticles(numOfArticles, new LoadNewsListener() {
            @Override
            public void onNewsLoaded(List<News> articles) {
                if (mView != null) {
                    Timber.d("Load news success, notified to view");
                    mView.displayArticles(articles);
                }else {
                    Timber.d("Load news success, but view is destroyed");
                }
            }

            @Override
            public void onError(Exception ex) {
                if (mView == null) {
                    Timber.d("Load news error, but view is destroyed: " + ex.getMessage());
                    return;
                }
                Timber.d("Load news error, notified to view: " + ex.getMessage());
                if (ex instanceof UnknownHostException) {
                    mView.displayErrorMessaege(R.string.error_network_connection);
                }
                else if (ex instanceof IllegalAccessException) {
                    mView.displayErrorMessaege(R.string.error_internal_server);
                }else {
                    mView.displayErrorMessaege(R.string.error_others);
                }
            }
        });
    }

    @Override
    public void performItemClick(int position) {
        if (mView != null) {
            mView.loadFullArticle(position);
        }
    }

    @Inject
    @Override
    public void attachView(MvpContract.View view) {
        this.mView = view;
    }

    @Override
    public void destroyView() {
        this.mView = null;
    }

}

