package com.mvm.modelviewpresenter.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.VisibleForTesting;

import com.mvm.modelviewpresenter.R;
import com.mvm.modelviewpresenter.model.News;
import com.mvm.modelviewpresenter.ui.adapters.NewsAdapter;
import com.mvm.modelviewpresenter.ui.base.BaseActivity;
import com.mvm.modelviewpresenter.ui.webview.WebViewActivity;
import com.mvm.modelviewpresenter.utils.ActivityUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MvpContract.View, AdapterView.OnItemClickListener {

    @Inject
    MvpContract.Presenter mPresenter;

    @Inject
    NewsAdapter mAdapter;

    @BindView(R.id.tvMessage)
    TextView tvMessage;

    @BindView(R.id.btnRetry)
    Button btnRetry;

    @BindView(R.id.progress_horizontal)
    @VisibleForTesting
    ProgressBar mProgressBar;

    @BindView(R.id.listview)
    ListView mListView;

    @BindView(R.id.loading_screen)
    View mLoadingScreen;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.mainActivity_label);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mPresenter.attachView(this);
        mPresenter.loadArticles(404);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destroyView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void setPresenter(MvpContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void displayArticles(List<News> articles) {

        mAdapter.clear();
        mAdapter.addAll(articles);
        mAdapter.notifyDataSetChanged();

        hideLoading(true);
    }

    @Override
    public void displayErrorMessaege(int messageId) {

        Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show();
        tvMessage.setText(messageId);

        hideLoading(false);
    }

    @Override
    public void loadFullArticle(int position) {
        News article = mAdapter.getItem(position);
        ActivityUtils.openActivityExtra(this, WebViewActivity.class,
                WebViewActivity.ARTICLE, article);
    }

    @OnClick(R.id.btnRetry)
    public void onRetryClicked(View view) {
        mPresenter.loadArticles(15);
        showLoading();
    }

    private void showLoading() {

        tvMessage.setText(R.string.message_loading_content);

        btnRetry.setVisibility(View.GONE);
        tvMessage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadingScreen.setVisibility(View.VISIBLE);
    }

    private void hideLoading(boolean loadSuccess) {
        if (loadSuccess) {
            mLoadingScreen.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }else {
            mListView.setVisibility(View.GONE);
            mLoadingScreen.setVisibility(View.VISIBLE);

            mProgressBar.setVisibility(View.GONE);
            btnRetry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mPresenter != null) {
            mPresenter.performItemClick(position);
        }
    }
}
