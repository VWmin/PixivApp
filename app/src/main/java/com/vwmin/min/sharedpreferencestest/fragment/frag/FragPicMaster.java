package com.vwmin.min.sharedpreferencestest.fragment.frag;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.fragment.BaseFragment;
import com.vwmin.min.sharedpreferencestest.utils.GlideUriUtil;

public class FragPicMaster extends BaseFragment {

    private PhotoView photoView;
    private String masterUrl;


    public static FragPicMaster getInstance(String url){
        Bundle arg = new Bundle();
        arg.putString("url", url);
        FragPicMaster frag = new FragPicMaster();
        frag.setArguments(arg);
        return frag;
    }


    @Override
    public int setLayoutId() {
        return R.layout.frag_pic_master;
    }

    @Override
    public void initView(View view) {
        photoView = view.findViewById(R.id.pic_master);
    }

    @Override
    public void initData() {
        masterUrl = getArguments().getString("url");

        Glide.with(this)
                .load(GlideUriUtil.getImgByUrl(masterUrl))
                .error(R.mipmap.no_data)
                .crossFade()
                .into(photoView);
    }
}
