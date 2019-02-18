package com.vwmin.min.sharedpreferencestest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vwmin.min.sharedpreferencestest.activity.PicDetailActivity;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;
import com.vwmin.min.sharedpreferencestest.data.ViewHistory;
import com.vwmin.min.sharedpreferencestest.event.IllustChangeEvent;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.response.Illust;
import com.vwmin.min.sharedpreferencestest.response.NullResponse;
import com.vwmin.min.sharedpreferencestest.utils.GlideUriUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class IllustAdapter extends RecyclerView.Adapter<IllustAdapter.ViewHolder> {

    private List<Illust> illustList;
    private Context context;
    private boolean clickable = true;
    private boolean showFavorite = true;
    private List<IllustAdapter.ViewHolder> holders = new ArrayList<>();

    public IllustAdapter(List<Illust> illustList, Context context){
        this.context = context;
        this.illustList = illustList;
    }

    // 定义Holder
    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView, star;
        private TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.pixiv_item_size);
            star = itemView.findViewById(R.id.star);
        }
    }

    @NonNull
    @Override
    public IllustAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_pic_container_in_recycle, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IllustAdapter.ViewHolder holder, int i) {
        holders.add(holder);
        // 载入图片
        Glide.with(context)
                .load(GlideUriUtil.getImgByIllust(illustList.get(i)))
                .error(R.mipmap.no_data)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageView);

        // 多图判断
        if(illustList.get(i).getPage_count() != 1){
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView.setText(String.format("%sP", String.valueOf(illustList.get(i).getPage_count())));
        }

        // 是否收藏判断
        /* 未收藏时显示灰色，点击后发送收藏请求并显示粉色
        * *成功则更新图片isBookmarked，失败则提示并置灰色
        * */
        if(showFavorite) {
            holder.star.setVisibility(View.VISIBLE);

            // TODO:制作点击动画（有生之年吧
            if(illustList.get(i).isBookmarked())
                holder.star.setImageResource(R.drawable.ic_favorite_pink_24dp);



            // 单击直接收藏\取消收藏
            holder.star.setOnClickListener(v -> {
                if(!illustList.get(i).isBookmarked()){
                    Observer<NullResponse> observer = new Observer<NullResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            holder.star.setImageResource(R.drawable.ic_favorite_pink_24dp);
                        }

                        @Override
                        public void onNext(NullResponse response) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            holder.star.setImageResource(R.drawable.ic_favorite_white_24dp);
                            Toast.makeText(context, "收藏失败，检查网络无误后重试", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(context, "收藏到喜欢~❤", Toast.LENGTH_SHORT).show();
                            illustList.get(i).setBookmarked(true);
                            EventBus.getDefault().post(new IllustChangeEvent(illustList.get(i)));
                        }
                    };
                    AppRetrofit.getInstance().starIllust(observer,
                            UserInfo.getInstance(context).getAuthorization(),
                            illustList.get(i).getIllust_id()+"",
                            "public",
                            null);
                }else{
                    Observer<NullResponse> observer = new Observer<NullResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            holder.star.setImageResource(R.drawable.ic_favorite_white_24dp);
                        }

                        @Override
                        public void onNext(NullResponse response) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            holder.star.setImageResource(R.drawable.ic_favorite_pink_24dp);
                            Toast.makeText(context, "取消收藏失败，检查网络无误后重试", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(context, "已取消收藏", Toast.LENGTH_SHORT).show();
                            illustList.get(i).setBookmarked(false);
                            EventBus.getDefault().post(new IllustChangeEvent(illustList.get(i)));
                        }
                    };
                    AppRetrofit.getInstance().unStarIllust(observer,
                            UserInfo.getInstance(context).getAuthorization(),
                            illustList.get(i).getIllust_id()+"");
                }
            });

            // TODO:长按编辑
            holder.star.setOnLongClickListener(v -> {


                return false;
            });
        }

        // 进入图片详情界面
        holder.imageView.setOnClickListener(v -> {

            if(clickable) {
                Intent intent = new Intent(context, PicDetailActivity.class);
                intent.putExtra("position", i);
                intent.putExtra("list", (Serializable) illustList);
                context.startActivity(intent);
            }

        });




    }

    @Override
    public int getItemCount() {
        return illustList.size();
    }


    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isShowFavorite() {
        return showFavorite;
    }

    public void setShowFavorite(boolean showFavorite) {
        this.showFavorite = showFavorite;
    }

    public void setStar(int illustId, boolean isStar){
        for(int i=0; i<holders.size(); i++) {
            if(illustList.get(i).getIllust_id() == illustId)
            if (isStar)
                holders.get(i).star.setImageResource(R.drawable.ic_favorite_pink_24dp);
            else
                holders.get(i).star.setImageResource(R.drawable.ic_favorite_white_24dp);
        }
    }



}
