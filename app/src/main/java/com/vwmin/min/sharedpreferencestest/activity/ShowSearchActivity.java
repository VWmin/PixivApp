package com.vwmin.min.sharedpreferencestest.activity;

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
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.adapters.IllustAdapter;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.response.Illust;
import com.vwmin.min.sharedpreferencestest.response.IllustsResponse;
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
    private String nextUrl;

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
        Observer<IllustsResponse> observer = new Observer<IllustsResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(IllustsResponse illustsResponse) {
                if(illustsResponse!=null && illustsResponse.getIllusts()!=null){
                    illusts = Illust.parserIllustsResponse(illustsResponse);
                    nextUrl = illustsResponse.getNext_url();
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
        AppRetrofit.getInstance().searchIllust(observer, UserInfo.getInstance(this).getAuthorization(),
                 query, "partial_match_for_tags", "date_desc", "for_android");
    }

    private void onLoadMoreListener(){
        Observer<IllustsResponse> observer = new Observer<IllustsResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(IllustsResponse illustsResponse) {
                if(illustsResponse!=null && illustsResponse.getIllusts()!=null){
                    nextUrl = illustsResponse.getNext_url();
                    illusts.addAll(Illust.parserIllustsResponse(illustsResponse));
                    illustAdapter.notifyItemRangeChanged(illusts.size()-illustsResponse.getIllusts().size(),
                            illustsResponse.getIllusts().size());
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
        AppRetrofit.getInstance().getNext(observer,
                UserInfo.getInstance(this).getAuthorization(), nextUrl);
    }


}
