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

public class TestActivity extends BaseActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    private SearchView searchView;
    private ImageView goBack;
    private RecyclerView recyclerView;
    private TextView clearQueryHistory;
    private Button[] buttons = new Button[3];
    private QueryAdapter adapter;
    private int searchMod = 0; // 0：插画、漫画    1：小说    2：用户

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

        if(adapter == null){
            adapter = new QueryAdapter(this);
            adapter.setMod(QueryAdapter.MOD_QUERY_HISTORY);
            adapter.setQueryHistories(queryHistories);
        }
        else adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
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

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() > 1){
            Observer<AutoCompleteResponse> observer = new Observer<AutoCompleteResponse>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(AutoCompleteResponse autoCompleteResponse) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

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
