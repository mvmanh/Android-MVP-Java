package com.mvm.modelviewpresenter.di.modules.sub;

import android.content.Context;

import com.mvm.modelviewpresenter.R;
import com.mvm.modelviewpresenter.di.qualifiers.ActivityContext;
import com.mvm.modelviewpresenter.repository.NewsRepository;
import com.mvm.modelviewpresenter.ui.adapters.NewsAdapter;
import com.mvm.modelviewpresenter.ui.main.MainActivity;
import com.mvm.modelviewpresenter.ui.main.MvpContract;
import com.mvm.modelviewpresenter.ui.main.NewsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    static MvpContract.Presenter providePresenter(NewsRepository repository) {
        return new NewsPresenter(repository);
    }

    @Provides
    @ActivityContext
    static Context provideContext(MainActivity activity) {
        return activity;
    }

    @Provides
    static NewsAdapter provideNewsAdapter(@ActivityContext Context context) {
        return new NewsAdapter(context, R.layout.item_news_layout);
    }
}
