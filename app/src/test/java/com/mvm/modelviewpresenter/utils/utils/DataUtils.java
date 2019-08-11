package com.mvm.modelviewpresenter.utils.utils;

import com.mvm.modelviewpresenter.model.News;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    public static List<News> createNews(int size) {
        List<News> newsList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            newsList.add(new News("Title " + i, "Link " + i));
        }
        return newsList;
    }
}
