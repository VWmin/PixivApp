package com.vwmin.min.sharedpreferencestest.fragment.mine;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.adapters.IllustAdapter;
import com.vwmin.min.sharedpreferencestest.data.ViewHistory;
import com.vwmin.min.sharedpreferencestest.fragment.BaseFragment;
import com.vwmin.min.sharedpreferencestest.response.Illust;
import com.vwmin.min.sharedpreferencestest.utils.Density;
import com.vwmin.min.sharedpreferencestest.utils.GridItemDecoration;

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

    // TODO:历史记录界面进入需要一次滑动才开始显示；删除历史记录后需要手动刷新
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
            if(illustList != null) illustList.clear();
            for(ViewHistory viewHistory:viewHistoryList)
                illustList.add(Illust.parserViewHistory(viewHistory));
            IllustAdapter illustAdapter = new IllustAdapter(illustList, getContext());
            recyclerView.setAdapter(illustAdapter);
            imageView.setVisibility(View.GONE);
        }
        refreshLayout.finishRefresh(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    // TODO:历史记录界面的下拉加载，暂无此需求，以后可以限制每次加载的数量
    private void onLoadMoreListener(){
        refreshLayout.finishLoadMore(true);
    }


}
