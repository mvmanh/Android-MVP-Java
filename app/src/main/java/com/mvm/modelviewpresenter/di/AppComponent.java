package com.mvm.modelviewpresenter.di;

import android.app.Application;

import com.mvm.modelviewpresenter.app.VnexpressApp;
import com.mvm.modelviewpresenter.di.modules.ActivitiesModule;
import com.mvm.modelviewpresenter.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivitiesModule.class
})
public interface AppComponent extends AndroidInjector<VnexpressApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder setApplication(Application app);
        AppComponent build();
    }
}
