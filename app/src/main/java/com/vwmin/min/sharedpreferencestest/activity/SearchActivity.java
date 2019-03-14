package com.vwmin.min.sharedpreferencestest.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.rey.material.widget.Button;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.adapters.QueryAdapter;
import com.vwmin.min.sharedpreferencestest.data.QueryHistory;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;
import com.vwmin.min.sharedpreferencestest.fragment.frag.FragSearchResult;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.response.AutoCompleteResponse;

import org.litepal.Operator;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    private ImageView goBack;
    private ImageView searchSetting;
    private RecyclerView queryRecyclerView;
    private TextView clearQueryHistory;
    private TextView searchSuggestion;
    private Button[] buttons = new Button[3];
    private LinearLayout line_query;
    private FrameLayout frame_result;
    private Fragment resultFragment;
    private QueryAdapter adapter;
    private SearchReceiver searchReceiver;
    private DeleteHistoryReceiver deleteHistoryReceiver;
    private int searchType = 0; // 0：插画、漫画    1：小说    2：用户
    private static final int TYPE_MANGA = 0, TYPE_NOVEL = 1, TYPE_USER = 2;

    public static final int REQUESTCODE_SEARCHSETTING = 1;
    public static final int RESULTCODE_OK = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));

        setLayout();
        setControl();
        setListener();
        setHistory();

        String externQuery = getIntent().getStringExtra("tag");
        if(externQuery!=null) onQueryTextSubmit(externQuery);
        TextView textView = findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setText(externQuery);


    }

    @Override
    void setLayout() {
        setContentView(R.layout.activity_search);
    }

    @Override
    void setControl() {
        searchView = findViewById(R.id.search_badge);
        goBack = findViewById(R.id.activity_search_back);
        searchSetting = findViewById(R.id.search_setting);
        queryRecyclerView = findViewById(R.id.search_list);
        clearQueryHistory = findViewById(R.id.search_clear);
        searchSuggestion = findViewById(R.id.search_suggestion);
        line_query = findViewById(R.id.line1);
        frame_result = findViewById(R.id.recycler_container_search_interface);

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
        textView.setOnClickListener(v -> switchBack());

    }

    @Override
    void setListener() {
        goBack.setOnClickListener(v -> finish());
        for(int i=0; i<buttons.length; i++) {
            final int position = i;
            buttons[i].setOnClickListener(v -> setButtonOnSelected(position));
        }
        searchView.setOnQueryTextListener(this);
        clearQueryHistory.setOnClickListener(v-> Operator.deleteAll(QueryHistory.class));
        searchSetting.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, SearchSettingActivity.class);
            this.startActivityForResult(intent, REQUESTCODE_SEARCHSETTING);
        });
    }

    void setHistory(){
        List<QueryHistory> queryHistories = Operator.findAll(QueryHistory.class);
        Collections.sort(queryHistories);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        queryRecyclerView.setLayoutManager(layoutManager);

        adapter = new QueryAdapter(this);
        adapter.setMod(QueryAdapter.MOD_QUERY_HISTORY);
        adapter.setQueryHistories(queryHistories);
        queryRecyclerView.setAdapter(adapter);

        clearQueryHistory.setVisibility(View.VISIBLE);
        searchSuggestion.setText("搜索历史");
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        QueryHistory queryHistory =  new QueryHistory(s);
        queryHistory.save();

        Bundle args = new Bundle();
        args.putString("word", s);
        args.putInt("searchType", searchType);

        switch (searchType){
            case TYPE_MANGA:
                resultFragment = FragSearchResult.getInstance(args);
                switch2Frag();
                break;

            case TYPE_NOVEL:
                break;

            case TYPE_USER:
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
                    queryRecyclerView.setVisibility(View.INVISIBLE);
                    clearQueryHistory.setVisibility(View.INVISIBLE);
                    searchSuggestion.setText("搜索建议 Loading...");
                }

                @Override
                public void onNext(AutoCompleteResponse autoCompleteResponse) {
                    if(autoCompleteResponse!=null){
                        adapter = new QueryAdapter(SearchActivity.this);
                        adapter.setMod(QueryAdapter.MOD_AUTO_COMPLETE);
                        adapter.setAutoCompleteResponses(autoCompleteResponse.getSearch_auto_complete_keywords());
                        queryRecyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    queryRecyclerView.setVisibility(View.VISIBLE);
                    setHistory();
                }

                @Override
                public void onComplete() {
                    queryRecyclerView.setVisibility(View.VISIBLE);
                    searchSuggestion.setText("搜索建议");
                }
            };
            AppRetrofit.getInstance().searchAutoComplete(observer, UserInfo.getInstance(this).getAuthorization(), s);
        }else setHistory();
        return false;
    }

    private void setButtonOnSelected(int position){
        searchType = position;
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

    private void switch2Frag(){
        line_query.setVisibility(View.GONE);
        frame_result.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!resultFragment.isAdded())
            transaction.add(R.id.recycler_container_search_interface, resultFragment);

        transaction.show(resultFragment).commit();
    }

    private void switchBack(){
        frame_result.setVisibility(View.GONE);
        line_query.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(resultFragment!=null && resultFragment.isAdded())
            transaction.remove(resultFragment).commit();

        setHistory();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE_SEARCHSETTING && resultCode==RESULTCODE_OK){
            onQueryTextSubmit(searchView.getQuery().toString());
        }
    }

    private class SearchReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String tmpWord = intent.getStringExtra("word");
            onQueryTextSubmit(tmpWord);
            TextView textView = findViewById(android.support.v7.appcompat.R.id.search_src_text);
            textView.setText(tmpWord);
        }
    }

    private class DeleteHistoryReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            setHistory();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter searchFilter = new IntentFilter();
        searchFilter.addAction("com.vwmin.min.sharedpreferencestest.START_SEARCH");
        searchReceiver = new SearchReceiver();
        registerReceiver(searchReceiver, searchFilter);

        IntentFilter deleteFilter = new IntentFilter();
        deleteFilter.addAction("com.vwmin.min.sharedpreferencestest.DELETE_PER_SEARCH_HISTORY");
        deleteHistoryReceiver = new DeleteHistoryReceiver();
        registerReceiver(deleteHistoryReceiver, deleteFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(searchReceiver!=null){
            unregisterReceiver(searchReceiver);
            searchReceiver = null;
        }
        if(deleteHistoryReceiver!=null){
            unregisterReceiver(deleteHistoryReceiver);
            deleteHistoryReceiver = null;
        }
    }

}
