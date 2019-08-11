package com.mvm.modelviewpresenter.utils.utils;

import org.mockito.ArgumentMatcher;

import java.util.List;

public class CustomMatchers{

    public static ArgumentMatcher<List> ListWithSize(int size) {
        return list -> list.size() == size;
    }
}
