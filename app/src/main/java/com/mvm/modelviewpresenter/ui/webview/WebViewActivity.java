package com.mvm.modelviewpresenter.ui.webview;

import android.os.Bundle;
import android.webkit.WebView;

import com.mvm.modelviewpresenter.R;
import com.mvm.modelviewpresenter.model.News;
import com.mvm.modelviewpresenter.ui.base.BaseActivity;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    public static final String ARTICLE = "ARTICLE";

    @BindView(R.id.webView)
    WebView webView;

    private News article;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        article = getIntent().getParcelableExtra(ARTICLE);
        if (article == null) {
            finish();
            return;
        }
        webView.loadUrl(article.link);
        getSupportActionBar().setTitle(article.title);
        showBackButton();
    }
}
