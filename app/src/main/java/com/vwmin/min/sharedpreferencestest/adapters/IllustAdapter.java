package com.vwmin.min.sharedpreferencestest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vwmin.min.sharedpreferencestest.PicDetailActivity;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.response.IllustBean;
import com.vwmin.min.sharedpreferencestest.utils.GlideUriUtil;

import java.io.Serializable;
import java.util.List;


public class IllustAdapter extends RecyclerView.Adapter<IllustAdapter.ViewHolder> {

    private AdapterView.OnItemClickListener onItemClickListener;
    private List<IllustBean> illustList;
    private Context context;

    public IllustAdapter(List<IllustBean> illustList, Context context){
        this.context = context;
        this.illustList = illustList;
    }

    // 定义Holder
    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView, star;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
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
        ViewHolder holder = new ViewHolder(view);

        //设置监听



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IllustAdapter.ViewHolder holder, int i) {
        // 载入图片
        Glide.with(context)
                .load(GlideUriUtil.getImgByIllustBean(illustList.get(i)))
                .error(R.mipmap.no_data)
                .into(holder.imageView);

        // 多图判断
        if(illustList.get(i).getPage_count() != 1){
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView.setText(String.format("%sP", String.valueOf(illustList.get(i).getPage_count())));
        }
        // 是否收藏判断
        if(illustList.get(i).isIs_bookmarked()) holder.star.setVisibility(View.VISIBLE);


        // 收藏与取消收藏
        holder.star.setOnClickListener(v -> {

        });

        // 进入图片详情界面
        holder.imageView.setOnClickListener(v -> {

            Intent intent = new Intent(context, PicDetailActivity.class);
            intent.putExtra("position", i);
            intent.putExtra("list", (Serializable)illustList);
            context.startActivity(intent);

        });



    }

    @Override
    public int getItemCount() {
        return illustList.size();
    }




}
