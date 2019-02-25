package com.vwmin.min.sharedpreferencestest.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.rey.material.widget.Button;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.adapters.QueryAdapter;
import com.vwmin.min.sharedpreferencestest.data.QueryHistory;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.response.AutoCompleteResponse;

import org.litepal.Operator;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ShowSearchActivity extends BaseActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    private SearchView searchView;
    private ImageView goBack;
    private RecyclerView recyclerView;
    private TextView clearQueryHistory;
    private TextView searchSuggestion;
    private Button[] buttons = new Button[3];
    private QueryAdapter adapter;
    private int searchMod = 0; // 0：插画、漫画    1：小说    2：用户
    private static final int MOD_MANGA = 0, MOD_NOVEL = 1, MOD_USER = 2;

    private List<QueryHistory> queryHistories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));

        setLayout();
        setControl();
        setListener();
        setHistory();

    }

    @Override
    void setLayout() {
        setContentView(R.layout.activity_showsearch);
    }

    @Override
    void setControl() {
        searchView = findViewById(R.id.search_badge);
        goBack = findViewById(R.id.activity_search_back);
        recyclerView = findViewById(R.id.search_list);
        clearQueryHistory = findViewById(R.id.search_clear);
        searchSuggestion = findViewById(R.id.search_suggestion);
        buttons[0] = findViewById(R.id.search_button1);
        buttons[1] = findViewById(R.id.search_button2);
        buttons[2] = findViewById(R.id.search_button3);

        setButtonOnSelected(0);

        /* searchView init */
        searchView.onActionViewExpanded();
        searchView.setQueryHint("输入关键字");
        searchView.setMaxWidth(800);
        TextView textView = findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setTextSize(16);
        textView.setTextColor(getColor(R.color.white));

    }

    @Override
    void setListener() {
        goBack.setOnClickListener(v -> finish());
        for(Button b:buttons) b.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);
        clearQueryHistory.setOnClickListener(v-> Operator.deleteAll(QueryHistory.class));

    }

    void setHistory(){
        queryHistories = Operator.findAll(QueryHistory.class);
        Collections.sort(queryHistories);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new QueryAdapter(this);
        adapter.setMod(QueryAdapter.MOD_QUERY_HISTORY);
        adapter.setQueryHistories(queryHistories);
        recyclerView.setAdapter(adapter);

        clearQueryHistory.setVisibility(View.VISIBLE);
        searchSuggestion.setText("搜索历史");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_button1:
                setButtonOnSelected(0);
                break;

            case R.id.search_button2:
                setButtonOnSelected(1);
                break;


            case R.id.search_button3:
                setButtonOnSelected(2);
                break;

        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        QueryHistory queryHistory =  new QueryHistory(s);
        queryHistory.save();
        switch (searchMod){
            case MOD_MANGA:
                break;

            case MOD_NOVEL:
                break;

            case MOD_USER:
                break;
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() > 1){
            Observer<AutoCompleteResponse> observer = new Observer<AutoCompleteResponse>() {
                @Override
                public void onSubscribe(Disposable d) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    clearQueryHistory.setVisibility(View.INVISIBLE);
                    searchSuggestion.setText("搜索建议 Loading...");
                }

                @Override
                public void onNext(AutoCompleteResponse autoCompleteResponse) {
                    if(autoCompleteResponse!=null){
                        adapter = new QueryAdapter(ShowSearchActivity.this);
                        adapter.setMod(QueryAdapter.MOD_AUTO_COMPLETE);
                        adapter.setAutoCompleteResponses(autoCompleteResponse.getSearch_auto_complete_keywords());
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    recyclerView.setVisibility(View.VISIBLE);
                    setHistory();
                }

                @Override
                public void onComplete() {
                    recyclerView.setVisibility(View.VISIBLE);
                    searchSuggestion.setText("搜索建议");
                }
            };
            AppRetrofit.getInstance().searchAutoComplete(observer, UserInfo.getInstance(this).getAuthorization(), s);
        }else setHistory();
        return false;
    }



    private void setButtonOnSelected(int position){
        searchMod = position;
        for(int i=0; i<buttons.length; i++){
            if(i==position){
                buttons[i].setTextColor(getColor(R.color.white));
                buttons[i].setBackgroundDrawable(getDrawable(R.drawable.shape_button));
            }else{
                buttons[i].setTextColor(getColor(R.color.black));
                buttons[i].setBackgroundColor(getColor(R.color.transport));
            }
        }
    }
}

























//package com.vwmin.min.sharedpreferencestest.activity;
//
//import android.os.Bundle;
//import android.support.v4.view.MenuItemCompat;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//
//import com.scwang.smartrefresh.header.DeliveryHeader;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.vwmin.min.sharedpreferencestest.R;
//import com.vwmin.min.sharedpreferencestest.adapters.IllustAdapter;
//import com.vwmin.min.sharedpreferencestest.response.Illust;
//import com.vwmin.min.sharedpreferencestest.utils.Density;
//import com.vwmin.min.sharedpreferencestest.utils.GridItemDecoration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ShowSearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {
//
//
//    private SearchView searchView;
//
//    private RefreshLayout refreshLayout;
//    private RecyclerView recyclerView;
//    private Toolbar toolbar;
//    private ProgressBar progressBar;
//    private ImageView imageView;
//
//    private IllustAdapter illustAdapter;
//    private List<Illust> illusts = new ArrayList<>();
//    private String query;
//    private String nextUrl;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
//
//
////        setLayout();
////        setControl();
////        setListener();
//
//    }
//
//    @Override
//    void setLayout() {
//        setContentView(R.layout.activity_showsearch);
//    }
//
//    @Override
//    void setControl() {
////        refreshLayout = findViewById(R.id.refreshLayout_in_acti_search);
////        recyclerView = findViewById(R.id.recycle_in_acti_search);
////        toolbar = findViewById(R.id.toolbar_show_search_query);
////        progressBar = findViewById(R.id.progress_in_acti_search);
////        imageView = findViewById(R.id.image_in_acti_search);
////
////        // 没有这个 自定义的toolbar无法正常显示
////        setSupportActionBar(toolbar);
////        toolbar.setTitle("搜索");
////
////        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
////        recyclerView.addItemDecoration(new GridItemDecoration(2, Density.dip2px(this, 4.0f), false));
////        recyclerView.setHasFixedSize(true);
////
////        refreshLayout.setRefreshHeader(new DeliveryHeader(this));
////        refreshLayout.setOnRefreshListener(refreshLayout -> onRefreshListener());
////        refreshLayout.setOnLoadMoreListener(refreshLayout -> onLoadMoreListener());
////
////
////        onRefreshListener();
//    }
//
//    @Override
//    void setListener() {
//        toolbar.setNavigationOnClickListener(v -> {
//            finish();
//        });
//    }
//
//
//    private void onRefreshListener() {
////        Observer<IllustsResponse> observer = new Observer<IllustsResponse>() {
////            @Override
////            public void onSubscribe(Disposable d) {
////                progressBar.setVisibility(View.VISIBLE);
////            }
////
////            @Override
////            public void onNext(IllustsResponse illustsResponse) {
////                if(illustsResponse!=null && illustsResponse.getIllusts()!=null){
////                    illusts = Illust.parserIllustsResponse(illustsResponse);
////                    nextUrl = illustsResponse.getNext_url();
////                    if(illusts.size()!=0) {
////                        illustAdapter = new IllustAdapter(illusts, ShowSearchActivity.this);
////                        recyclerView.setAdapter(illustAdapter);
////                    }else{
////                        imageView.setVisibility(View.VISIBLE);
////                    }
////                }
////            }
////
////            @Override
////            public void onError(Throwable e) {
////                progressBar.setVisibility(View.GONE);
////                refreshLayout.finishRefresh(true);
////                Toast.makeText(ShowSearchActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
////            }
////
////            @Override
////            public void onComplete() {
////                progressBar.setVisibility(View.GONE);
////                refreshLayout.finishRefresh(true);
////            }
////        };
////        AppRetrofit.getInstance().searchIllust(observer, UserInfo.getInstance(this).getAuthorization(),
////                 query, "partial_match_for_tags", "date_desc", "for_android");
//    }
//
//    private void onLoadMoreListener(){
////        Observer<IllustsResponse> observer = new Observer<IllustsResponse>() {
////            @Override
////            public void onSubscribe(Disposable d) {
////
////            }
////
////            @Override
////            public void onNext(IllustsResponse illustsResponse) {
////                if(illustsResponse!=null && illustsResponse.getIllusts()!=null){
////                    nextUrl = illustsResponse.getNext_url();
////                    illusts.addAll(Illust.parserIllustsResponse(illustsResponse));
////                    illustAdapter.notifyItemRangeChanged(illusts.size()-illustsResponse.getIllusts().size(),
////                            illustsResponse.getIllusts().size());
////                }
////
////            }
////
////            @Override
////            public void onError(Throwable e) {
////                refreshLayout.finishLoadMore(true);
////                Toast.makeText(ShowSearchActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
////            }
////
////            @Override
////            public void onComplete() {
////                refreshLayout.finishLoadMore(true);
////            }
////        };
////        AppRetrofit.getInstance().getNext(observer,
////                UserInfo.getInstance(this).getAuthorization(), nextUrl);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // TODO:搜索框待完善
////        getMenuInflater().inflate(R.menu.search_function, menu);
////        searchView = (SearchView) menu.findItem(R.id.search_badge).getActionView();
////
////        searchView.setOnQueryTextListener(this);
//////        searchView.setBackgroundColor(getColor(R.color.white));
//////        searchView.onActionViewExpanded();
////        searchView.setMaxWidth(800);
//////        searchView.setSubmitButtonEnabled(true); // 添加提交按钮
////        searchView.setQueryHint("输入关键字");
//        return true;
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String s) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String s) {
//        return false;
//    }
//}
