package com.vwmin.min.sharedpreferencestest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vwmin.min.sharedpreferencestest.adapters.IllustAdapter;
import com.vwmin.min.sharedpreferencestest.network.SearchRetrofit;
import com.vwmin.min.sharedpreferencestest.response.Illust;
import com.vwmin.min.sharedpreferencestest.response.SearchResponse;
import com.vwmin.min.sharedpreferencestest.utils.Density;
import com.vwmin.min.sharedpreferencestest.utils.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ShowSearchActivity extends BaseActivity {


    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ImageView imageView;

    private IllustAdapter illustAdapter;
    private List<Illust> illusts = new ArrayList<>();
    private String query;
    private int currentPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        setControl();
        setListener();

    }

    @Override
    void setLayout() {
        setContentView(R.layout.activity_showsearch);
    }

    @Override
    void setControl() {
        refreshLayout = findViewById(R.id.refreshLayout_in_acti_search);
        recyclerView = findViewById(R.id.recycle_in_acti_search);
        toolbar = findViewById(R.id.toolbar_show_search_query);
        progressBar = findViewById(R.id.progress_in_acti_search);
        imageView = findViewById(R.id.image_in_acti_search);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridItemDecoration(2, Density.dip2px(this, 4.0f), false));
        recyclerView.setHasFixedSize(true);

        refreshLayout.setRefreshHeader(new DeliveryHeader(this));
        refreshLayout.setOnRefreshListener(refreshLayout -> onRefreshListener());
        refreshLayout.setOnLoadMoreListener(refreshLayout -> onLoadMoreListener());

        Intent intent = getIntent();
        query = intent.getStringExtra("query");
        toolbar.setTitle(query);



        onRefreshListener();
    }

    @Override
    void setListener() {

    }


    private void onRefreshListener() {
        Observer<SearchResponse> observer = new Observer<SearchResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(SearchResponse searchResponse) {
                if(searchResponse!=null && searchResponse.getResponse()!=null){
                    illusts = Illust.parserSearchResponse(searchResponse);
                    if(illusts.size()!=0) {
                        illustAdapter = new IllustAdapter(illusts, ShowSearchActivity.this);
                        recyclerView.setAdapter(illustAdapter);
                    }else{
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
                refreshLayout.finishRefresh(true);
                Toast.makeText(ShowSearchActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                progressBar.setVisibility(View.GONE);
                refreshLayout.finishRefresh(true);
            }
        };
        SearchRetrofit.getInstance().getSearchAsTag(observer, query+"5000users入り", "desc", "1");
    }

    private void onLoadMoreListener(){
        currentPage+=1;
        Observer<SearchResponse> observer = new Observer<SearchResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(SearchResponse searchResponse) {
                if(searchResponse!=null && searchResponse.getResponse()!=null){
                    illusts.addAll(Illust.parserSearchResponse(searchResponse));
                    illustAdapter.notifyItemRangeChanged(illusts.size()-searchResponse.getResponse().size(),
                            searchResponse.getResponse().size());

                }
            }

            @Override
            public void onError(Throwable e) {
                refreshLayout.finishLoadMore(true);
                Toast.makeText(ShowSearchActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                refreshLayout.finishLoadMore(true);
            }
        };
        SearchRetrofit.getInstance().getSearchAsTag(observer, query+"5000users入り", "desc", currentPage+"");

    }


}
