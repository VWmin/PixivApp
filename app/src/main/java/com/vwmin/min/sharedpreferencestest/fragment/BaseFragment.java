package com.vwmin.min.sharedpreferencestest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;


public abstract class BaseFragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutId(), container, false);
        initView(view);
        initData();

        return view;
    }


    public abstract int  setLayoutId();
    public abstract void initView(View view);
    public abstract void initData();

}

