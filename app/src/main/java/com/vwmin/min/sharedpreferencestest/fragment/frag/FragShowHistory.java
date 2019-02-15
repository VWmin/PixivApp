package com.vwmin.min.sharedpreferencestest.fragment.frag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.adapters.IllustAdapter;
import com.vwmin.min.sharedpreferencestest.data.ViewHistory;
import com.vwmin.min.sharedpreferencestest.data.ViewHistoryOperator;
import com.vwmin.min.sharedpreferencestest.event.IllustChangeEvent;
import com.vwmin.min.sharedpreferencestest.fragment.BaseFragment;
import com.vwmin.min.sharedpreferencestest.response.Illust;
import com.vwmin.min.sharedpreferencestest.utils.Density;
import com.vwmin.min.sharedpreferencestest.utils.GridItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.litepal.Operator.findAll;


public class FragShowHistory extends BaseFragment {

    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Illust> illustList = new ArrayList<>();
    private ImageView imageView;
    private IllustAdapter illustAdapter;
    private RefreshViewHistoryReceiver receiver = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void doIllustChange(IllustChangeEvent illustChangeEvent){
        Log.d("ViewHistoryOperator", "received");

        Illust newIllust = illustChangeEvent.getNewIllust();
        ViewHistoryOperator.refreshFollowStatus(newIllust.getIllust_id(), newIllust.isUser_isFollowed());
        ViewHistoryOperator.refreshStarStatus(newIllust.getIllust_id(), newIllust.isBookmarked());

    }

    @Override
    public void onResume() {
        super.onResume();
        if(receiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.vwmin.min.sharedpreferencestest.REFRESH_VIEW_HISTORY");
            receiver = new RefreshViewHistoryReceiver();
            Objects.requireNonNull(getContext()).registerReceiver(receiver, intentFilter);
            Log.d("broadCast", "注册了更新历史纪录的广播");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            onRefreshListener();
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.frag_recycle;
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_in_frag_recycle);
        progressBar = view.findViewById(R.id.progress_in_frag_recycle);
        refreshLayout = view.findViewById(R.id.refreshLayout_in_frag_recycle);
        imageView = view.findViewById(R.id.image_in_frag_recycle);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new GridItemDecoration(2, Density.dip2px(Objects.requireNonNull(getContext()), 4.0f), false));
        recyclerView.setHasFixedSize(true);

        refreshLayout.setRefreshHeader(new DeliveryHeader(getContext()));
        refreshLayout.setOnRefreshListener(refreshLayout -> onRefreshListener());
        refreshLayout.setOnLoadMoreListener(refreshLayout -> onLoadMoreListener());

    }

    @Override
    public void initData() {
        onRefreshListener();
    }


    private void onRefreshListener(){
        progressBar.setVisibility(View.VISIBLE);
        List<ViewHistory> viewHistoryList = findAll(ViewHistory.class);
        Collections.sort(viewHistoryList);
        // 无历史记录，显示NO DATA
        if(viewHistoryList.size()==0){
            Log.d("history", "There is no more history.");
            recyclerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            if(illustList != null) illustList.clear();
            for(ViewHistory viewHistory:viewHistoryList)
                illustList.add(Illust.parserViewHistory(viewHistory));
            illustAdapter = new IllustAdapter(illustList, getContext());
            recyclerView.setAdapter(illustAdapter);
        }
        refreshLayout.finishRefresh(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    // TODO:历史记录界面的下拉加载，暂无此需求，以后可以限制每次加载的数量
    private void onLoadMoreListener(){
        refreshLayout.finishLoadMore(true);
    }

    class RefreshViewHistoryReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("broadCast", "接收到了更新历史记录的广播");
            onRefreshListener();
        }
    }

}
