package com.mvm.modelviewpresenter.di.modules;

import com.mvm.modelviewpresenter.di.modules.sub.MainActivityModule;
import com.mvm.modelviewpresenter.ui.main.MainActivity;
import com.mvm.modelviewpresenter.ui.webview.WebViewActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    public abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector()
    public abstract WebViewActivity contributeWebViewActivity();
}
