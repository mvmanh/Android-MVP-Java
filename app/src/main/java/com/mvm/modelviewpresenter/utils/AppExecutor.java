package com.mvm.modelviewpresenter.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class AppExecutor {

    private static AppExecutor instance;
    public static synchronized AppExecutor getInstance() {
        if (instance == null) {
            instance = new AppExecutor();
        }
        return instance;
    }
    public static synchronized void destroyInstance() {
        instance = null;
    }

    private Executor diskIO;
    private Executor networkIO;
    private Executor mainThread;

    @Inject
    public AppExecutor() {
        this.diskIO = Executors.newSingleThreadExecutor();
        this.networkIO = Executors.newFixedThreadPool(3);
        this.mainThread = new MainExecutor();
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public static class MainExecutor implements Executor {

        private Handler mHandler;

        public MainExecutor() {
            this.mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void execute(Runnable command) {
            mHandler.post(command);
        }
    }
}
