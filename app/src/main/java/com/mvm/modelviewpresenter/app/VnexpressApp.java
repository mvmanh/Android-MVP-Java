package com.mvm.modelviewpresenter.app;

import com.mvm.modelviewpresenter.BuildConfig;
import com.mvm.modelviewpresenter.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public class VnexpressApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().setApplication(this).build();
    }
}
