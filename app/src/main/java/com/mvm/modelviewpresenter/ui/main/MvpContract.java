package com.mvm.modelviewpresenter.ui.main;

import androidx.annotation.StringRes;

import com.mvm.modelviewpresenter.ui.base.BasePresenter;
import com.mvm.modelviewpresenter.ui.base.BaseView;
import com.mvm.modelviewpresenter.model.News;

import java.util.List;

public interface MvpContract {

    interface View extends BaseView<Presenter> {
        void displayArticles(List<News> articles);
        void displayErrorMessaege(@StringRes int messageId);
        void loadFullArticle(int position);
    }

    interface Presenter extends BasePresenter<View> {
        void loadArticles(int numOfArticles);
        void performItemClick(int position);
    }
}
