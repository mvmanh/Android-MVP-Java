package com.mvm.modelviewpresenter.ui.base;

public interface BaseView <P extends BasePresenter>{
    void setPresenter(P presenter);
}
