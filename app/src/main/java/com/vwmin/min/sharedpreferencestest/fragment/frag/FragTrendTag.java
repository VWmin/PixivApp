package com.vwmin.min.sharedpreferencestest.fragment.frag;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.adapters.TrendTagAdapter;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;
import com.vwmin.min.sharedpreferencestest.fragment.BaseFragment;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.response.TrendTagsResponse;
import com.vwmin.min.sharedpreferencestest.utils.Density;
import com.vwmin.min.sharedpreferencestest.utils.GridItemDecoration;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FragTrendTag extends BaseFragment {
    private RecyclerView recyclerView;
    private TrendTagAdapter trendTagAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.frag_recycle_only;
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_only);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                // 返回一个item占几列
                if(i == 0) return 3;
                else return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridItemDecoration(3,
                Density.dip2px(Objects.requireNonNull(getContext()), 1.0f), false));
        recyclerView.setHasFixedSize(true);


    }

    @Override
    public void initData() {
        Observer<TrendTagsResponse> observer = new Observer<TrendTagsResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TrendTagsResponse trendTagsResponse) {
                if(trendTagsResponse!=null){
                    trendTagAdapter = new TrendTagAdapter(getContext(), trendTagsResponse.getTrend_tags());
                    recyclerView.setAdapter(trendTagAdapter);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        AppRetrofit.getInstance().getTrendTags(
                observer,
                UserInfo.getInstance(Objects.requireNonNull(getContext())).getAuthorization(),
                "for_android");
    }



}
