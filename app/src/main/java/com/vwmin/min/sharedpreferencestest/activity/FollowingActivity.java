package com.vwmin.min.sharedpreferencestest.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.adapters.UserAdapter;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.response.FollowingResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class FollowingActivity extends BaseActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));

        setLayout();
        setControl();
        setListener();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);

//        recyclerView.setAdapter(new UserAdapter(this));
        String userId = UserInfo.getInstance(this).getID();
        if(userId!=null) {
            Observer<FollowingResponse> observer = new Observer<FollowingResponse>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(FollowingResponse followingResponse) {
                    recyclerView.setAdapter(new UserAdapter(FollowingActivity.this, followingResponse.getUser_previews()));
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            AppRetrofit.getInstance().userFollowing(observer, userId,
                    UserInfo.getInstance(this).getAuthorization());
        }


    }

    @Override
    void setLayout() {
        setContentView(R.layout.activity_following);
    }

    @Override
    void setControl() {
        toolbar = findViewById(R.id.following_toolbar);
        recyclerView = findViewById(R.id.following_recycler);
    }

    @Override
    void setListener() {
        toolbar.setNavigationOnClickListener(v->finish());
    }
}
