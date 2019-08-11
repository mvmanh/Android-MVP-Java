package com.mvm.modelviewpresenter.di.modules;

import android.app.Application;
import android.content.Context;

import com.mvm.modelviewpresenter.di.qualifiers.AppContext;
import com.mvm.modelviewpresenter.repository.NewsRepository;
import com.mvm.modelviewpresenter.utils.AppExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    @AppContext
    static Context provideAppContext(Application app) {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    static AppExecutor provideExecutor() {
        return AppExecutor.getInstance();
    }

    @Provides
    @Singleton
    static NewsRepository provideNewsRepository(@AppContext Context context, AppExecutor executor) {
        return new NewsRepository(context, executor);
    }
}
