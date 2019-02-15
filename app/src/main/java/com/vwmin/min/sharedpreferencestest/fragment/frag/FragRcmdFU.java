package com.vwmin.min.sharedpreferencestest.fragment.recommned;


import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.adapters.IllustAdapter;
import com.vwmin.min.sharedpreferencestest.fragment.BaseFragment;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.response.Illust;
import com.vwmin.min.sharedpreferencestest.response.IllustBean;
import com.vwmin.min.sharedpreferencestest.response.IllustsResponse;
import com.vwmin.min.sharedpreferencestest.utils.AfterComplete;
import com.vwmin.min.sharedpreferencestest.utils.Density;
import com.vwmin.min.sharedpreferencestest.utils.GridItemDecoration;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


// 为你推荐
public class FragRcmdFU extends BaseFragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RefreshLayout refreshLayout;
    private IllustAdapter illustAdapter;
    private List<Illust> illustList;
    private String nextUrl = null;


    @Override
    public int setLayoutId(){return R.layout.frag_recycle;}

    @Override
    public void initView(View view) {
        // 控件初始化
        recyclerView = view.findViewById(R.id.recycle_in_frag_recycle);
        progressBar = view.findViewById(R.id.progress_in_frag_recycle);
        refreshLayout = view.findViewById(R.id.refreshLayout_in_frag_recycle);

        // 栅格化
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new GridItemDecoration(2, Density.dip2px(Objects.requireNonNull(getContext()), 4.0f), false));
        recyclerView.setHasFixedSize(true);

        // 刷新事件
        refreshLayout.setRefreshHeader(new DeliveryHeader(getContext()));
        refreshLayout.setOnRefreshListener(refreshLayout -> onRefreshListener());
        refreshLayout.setOnLoadMoreListener(refreshLayout -> onLoadMoreListener());
    }

    @Override
    public void initData() {
        onRefreshListener();
    }

    private void onRefreshListener(){
        // 创建如果Token有效时的后续事件
        AfterComplete ifTokenOK = new AfterComplete() {
            @Override
            public void onSuccess() {
                {
                    Observer<IllustsResponse> observer = new Observer<IllustsResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onNext(IllustsResponse illustsResponse) {
                            if(illustsResponse!=null && illustsResponse.getIllusts()!=null) {
                                illustList = Illust.parserIllustsResponse(illustsResponse);
                                nextUrl = illustsResponse.getNext_url();
                                illustAdapter = new IllustAdapter(illustList, getContext());
                                recyclerView.setAdapter(illustAdapter);
                            }
                            else
                                Toast.makeText(getContext(), "加载失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            refreshLayout.finishRefresh(false);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "加载失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {
                            refreshLayout.finishRefresh(true);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    };
                    AppRetrofit.getInstance().getRecommend(
                            observer,
                            UserInfo.getInstance(Objects.requireNonNull(getContext())).getAuthorization(),
                            "for_android",
                            false);
                }
            }

            @Override
            public void onError() {

            }
        };

        // 检查Token状态，携带后续执行事件
        AppRetrofit.getInstance().chkToken(getContext(), ifTokenOK);

    }

    private void onLoadMoreListener(){

        AfterComplete ifTokenOK = new AfterComplete() {
            @Override
            public void onSuccess() {
                {
                    Observer<IllustsResponse> observer = new Observer<IllustsResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(IllustsResponse illustsResponse) {
                            if (illustsResponse != null && illustsResponse.getIllusts() != null) {
                                // 添加到滚动列表
                                illustList.addAll(Illust.parserIllustsResponse(illustsResponse));
                                nextUrl = illustsResponse.getNext_url();
                                illustAdapter.notifyItemRangeChanged(
                                        illustList.size() - illustsResponse.getIllusts().size(),
                                        illustsResponse.getIllusts().size());
                            } else {
                                Toast.makeText(getContext(), "加载失败", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            refreshLayout.finishLoadMore(false);
                            Toast.makeText(getContext(), "加载失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {
                            refreshLayout.finishLoadMore(true);
                        }
                    };
                    AppRetrofit.getInstance().getNext(observer,
                            UserInfo.getInstance(getContext()).getAuthorization(),
                            nextUrl);
                }
            }

            @Override
            public void onError() {

            }
        };

        AppRetrofit.getInstance().chkToken(getContext(), ifTokenOK);
    }

}
