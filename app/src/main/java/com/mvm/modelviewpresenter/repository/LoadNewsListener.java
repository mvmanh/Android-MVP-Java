package com.mvm.modelviewpresenter.repository;

import com.mvm.modelviewpresenter.model.News;
import java.util.List;

public interface LoadNewsListener {
    void onNewsLoaded(List<News> articles);
    void onError(Exception ex);
}