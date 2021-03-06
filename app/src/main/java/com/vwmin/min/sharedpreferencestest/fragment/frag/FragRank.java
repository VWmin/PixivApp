package com.vwmin.min.sharedpreferencestest.fragment.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.adapters.IllustAdapter;
import com.vwmin.min.sharedpreferencestest.event.IllustChangeEvent;
import com.vwmin.min.sharedpreferencestest.fragment.BaseFragment;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.response.Illust;
import com.vwmin.min.sharedpreferencestest.response.IllustsResponse;
import com.vwmin.min.sharedpreferencestest.utils.AfterComplete;
import com.vwmin.min.sharedpreferencestest.utils.Density;
import com.vwmin.min.sharedpreferencestest.utils.GridItemDecoration;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FragRank extends BaseFragment {

    private String mode;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RefreshLayout refreshLayout;
    private IllustAdapter illustAdapter;
    private List<Illust> illustList;
    private String nextUrl = null;


    public static FragRank getInstance(String mode){
        Bundle arg = new Bundle();
        arg.putString("mode", mode);
        FragRank frag = new FragRank();
        frag.setArguments(arg);
        return frag;
    }


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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doIllustChange(IllustChangeEvent illustChangeEvent){
        Illust newIllust = illustChangeEvent.getNewIllust();
        for(int i=0; i<illustList.size(); i++)
            if (illustList.get(i).getIllust_id() == illustChangeEvent.getNewIllust().getIllust_id()) {
                illustList.get(i).setUser_isFollowed(newIllust.isUser_isFollowed());
                illustList.get(i).setBookmarked(newIllust.isBookmarked());

                illustAdapter.setStar(newIllust.getIllust_id(), newIllust.isBookmarked());
            }
    }

    @Override
    public int setLayoutId() {
        return R.layout.frag_recycle;
    }

    @Override
    public void initView(View view) {
        //控件初始化
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
        Bundle arg = getArguments();
        this.mode = arg.getString("mode", "day");
        onRefreshListener();
    }

    private void onRefreshListener() {
        AfterComplete ifTokenOK = new AfterComplete() {
            @Override
            public void onSuccess() {
                Observer<IllustsResponse> observer = new Observer<IllustsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(IllustsResponse illustsResponse) {
                        if(illustsResponse!=null && illustsResponse.getIllusts()!=null){
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
                AppRetrofit.getInstance().getRank(
                        observer,
                        UserInfo.getInstance(getContext()).getAuthorization(),
                        "for_android",
                        mode);
            }

            @Override
            public void onError() {

            }


        };

        AppRetrofit.getInstance().chkToken(getContext(), ifTokenOK);
    }

    private void onLoadMoreListener() {
        if(nextUrl==null){
            Toast.makeText(getContext(), "没有更多了哦~", Toast.LENGTH_SHORT).show();
            refreshLayout.finishLoadMore(true);
        }else {
            AfterComplete ifTokenOK = new AfterComplete() {
                @Override
                public void onSuccess() {
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
                                illustAdapter.notifyItemRangeChanged(illustList.size() - illustsResponse.getIllusts().size(),
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

                @Override
                public void onError() {

                }
            };
            AppRetrofit.getInstance().chkToken(getContext(), ifTokenOK);
        }
    }


}
