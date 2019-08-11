package com.mvm.modelviewpresenter.ui.base;

public interface BasePresenter <V extends BaseView> {
    void attachView(V view);
    void destroyView();
}
