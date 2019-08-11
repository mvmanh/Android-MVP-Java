package com.mvm.modelviewpresenter.repository;

import android.content.Context;

import com.mvm.modelviewpresenter.R;
import com.mvm.modelviewpresenter.model.News;
import com.mvm.modelviewpresenter.utils.AppExecutor;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class NewsRepository {

    private Context context;
    private AppExecutor executor;


    @Inject
    public NewsRepository(Context context, AppExecutor executor) {
        this.context = context;
        this.executor = executor;
    }

    private String[] loadNews(Context context) {
        String[] news = context.getResources().getStringArray(R.array.news_title);
        return news;
    }

    private String[] loadLinks(Context context) {
        String[] links = context.getResources().getStringArray(R.array.news_image);
        return links;
    }

    public void loadArticles(int numOfArticles, LoadNewsListener callback) {
        Runnable job = () -> {
            // simulate network delay
            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // simulate network error when numOfArticles = 404
            if (numOfArticles == 404) {
                executor.mainThread().execute(() ->
                        callback.onError(new UnknownHostException("Can not connect to web server")));
                return;
            }
            else if (numOfArticles == 500) {
                executor.mainThread().execute(() ->
                        callback.onError(new IllegalAccessException("Internal server error has occured")));
                return;
            }

            String[] title = loadNews(context);
            String[] links = loadLinks(context);

            List<News> data = new ArrayList<>();
            for (int i = 0; i < title.length; i++) {
                data.add(new News(title[i], links[i]));
            }

            Collections.shuffle(data);

            if (numOfArticles < title.length) {
                if (callback != null) {
                    executor.mainThread().execute(() ->
                            callback.onNewsLoaded(data.subList(0, numOfArticles)));
                }
            }else if (callback != null) {
                executor.mainThread().execute(() ->
                        callback.onNewsLoaded(data));
            }
        };

        executor.diskIO().execute(job);
    }
}
