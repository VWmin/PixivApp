package com.vwmin.min.sharedpreferencestest.fragment.frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rey.material.widget.Button;
import com.rey.material.widget.ImageView;
import com.rey.material.widget.TextView;
import com.vwmin.min.sharedpreferencestest.activity.PicDetailActivity;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.activity.PicMasterActivity;
import com.vwmin.min.sharedpreferencestest.data.AppSetting;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;
import com.vwmin.min.sharedpreferencestest.event.IllustChangeEvent;
import com.vwmin.min.sharedpreferencestest.fragment.BaseFragment;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.network.DownloadTask;
import com.vwmin.min.sharedpreferencestest.response.Illust;
import com.vwmin.min.sharedpreferencestest.response.NullResponse;
import com.vwmin.min.sharedpreferencestest.utils.GlideUriUtil;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static org.litepal.Operator.find;
import static org.litepal.Operator.findAll;


public class FragPerPic extends BaseFragment implements View.OnClickListener {

    private Illust illust;
    private android.widget.ImageView bgImg;
    private TextView pageCnt;
    private ImageView img;
    private ProgressBar progress;
    private CircleImageView authorProfile;
    private TextView authorNickname;
    private TextView follow;
    private TextView imgDescribe;
    private Context context;
    private Button download;
    private TextView totalViewed;
    private TextView totalBooks;

    @Override
    public int setLayoutId() {
        return R.layout.frag_pic_container_in_detail;
    }

    @Override
    public void initView(View view) {
        // 控件
        bgImg = view.findViewById(R.id.imageView_picdetail_bg);
        pageCnt = view.findViewById(R.id.card_picdetail_pic_pagecnt);
        img = view.findViewById(R.id.card_picdetail_pic_pic);
        progress = view.findViewById(R.id.card_picdetail_pic_progress);
        authorProfile = view.findViewById(R.id.card_picdetail_author_profile);
        authorNickname = view.findViewById(R.id.card_picdetail_author_nickname);
        follow = view.findViewById(R.id.card_picdetail_author_isfollow);
        imgDescribe = view.findViewById(R.id.card_picdetail_author_describe);
        download = view.findViewById(R.id.card_picdetail_download_button);
        totalViewed = view.findViewById(R.id.card_picdetail_author_total_view);
        totalBooks = view.findViewById(R.id.card_picdetail_author_total_bookmarks);



        // 数据
        if( getArguments() == null){
            Toast.makeText(context, "资源加载失败", Toast.LENGTH_LONG).show();
        }
        else illust = (Illust) getArguments().getSerializable("illust");

        context = Objects.requireNonNull(getActivity()).getApplicationContext();


        // 设置监听
        img.setOnClickListener(this);
        follow.setOnClickListener(this);
        download.setOnClickListener(this);
    }

    @Override
    public void initData() {
        // 背景
        Glide.with(this)
                .load(GlideUriUtil.getImgByIllust(illust))
                .bitmapTransform(new BlurTransformation(context, 20, 1))
                .error(R.mipmap.pivision)
                .into(bgImg);

        // 未加载完成时显示progress
        progress.setVisibility(View.VISIBLE);

        // 原图
        Glide.with(this)
                .load(GlideUriUtil.getImgByUrl(getFirstImg()))
                .error(R.mipmap.no_data)
//                .override(illust.getWidth(),illust.getHeight())
                .crossFade()
                .listener(new RequestListener<GlideUrl, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, GlideUrl model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, GlideUrl model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progress.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(img);

        // 作者头像
        Glide.with(this)
                .load(GlideUriUtil.getImgByUrl(illust.getUser_profile()))
                .into(authorProfile);

        // 作者昵称
        authorNickname.setText(illust.getUser_name());

        // 图片描述
        imgDescribe.setText(illust.getCaption()==null||illust.getCaption().equals("") ?"作者没有添加描述~":illust.getCaption());

        // 关注
        if(!illust.isUser_isFollowed()) {
            follow.setText("+关注");
        }else{
            follow.setText("✓已关注");
        }

        // 设置张数
        pageCnt.setText(String.format("%sP", String.valueOf(illust.getPage_count())));

        // 设置总浏览次数
        totalViewed.setText(String.format("%d", illust.getTotal_viewed()));

        // 设置总收藏
        totalBooks.setText(String.format("%d", illust.getTotal_bookmarks()));

        // 设置标题
        ((PicDetailActivity) Objects.requireNonNull(getActivity())).mySetTitle();
    }


    // TODO:待完成：点击图片事件，关注事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_picdetail_pic_pic:
                Intent intent = new Intent(context, PicMasterActivity.class);
                intent.putExtra("urls", (ArrayList<String>)illust.getMeta_pages());
                startActivity(intent);
                break;
            case R.id.card_picdetail_author_isfollow:
                if(!illust.isUser_isFollowed()) postFollow(true);
                else postFollow(false);
                break;
            case R.id.card_picdetail_download_button: // 下载
                new DownloadTask(context, AppSetting.getSavePath(), illust.getTitle()+"_"+illust.getIllust_id()+".jpg").execute(illust.getMeta_pages().get(0));

                break;
        }
    }

    public static FragPerPic newInstance(Illust illust){
        Bundle bundle = new Bundle();
        bundle.putSerializable("illust", illust);


        FragPerPic frag = new FragPerPic();
        frag.setArguments(bundle);

        return frag;
    }

    private String getFirstImg(){
        Log.d("urlExistChk", illust.getMeta_pages().get(0));
//        return illust.getMeta_pages().get(0);
//
        return illust.getMedium_url();
    }

    private void postFollow(boolean choose){
        Observer<NullResponse> observer;
        if (choose){
            observer = new Observer<NullResponse>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(NullResponse response) {

                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(context, "操作失败，请检查网络", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {
                    follow.setText("✓已关注");
                    Toast.makeText(context, "已关注", Toast.LENGTH_SHORT).show();
                    illust.setUser_isFollowed(true);
                    EventBus.getDefault().post(new IllustChangeEvent(illust));
                }
            };
            AppRetrofit.getInstance().followUser(observer,
                    UserInfo.getInstance(context).getAuthorization(),
                    illust.getUser_id()+"",
                    "public");
        }else{
            observer = new Observer<NullResponse>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(NullResponse response) {

                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(context, "操作失败，请检查网络", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    follow.setText("+关注");
                    Toast.makeText(context, "已取消关注", Toast.LENGTH_SHORT).show();
                    illust.setUser_isFollowed(false);
                    EventBus.getDefault().post(new IllustChangeEvent(illust));
                }
            };
            AppRetrofit.getInstance().unFollowUser(observer,
                    UserInfo.getInstance(context).getAuthorization(),
                    illust.getUser_id()+"");
        }
    }

}
