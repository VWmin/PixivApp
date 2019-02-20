package com.vwmin.min.sharedpreferencestest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.activity.ShowSearchActivity;
import com.vwmin.min.sharedpreferencestest.response.TrendTagsResponse;
import com.vwmin.min.sharedpreferencestest.response.TrendTagsResponse.TrendTagsBean;
import com.vwmin.min.sharedpreferencestest.utils.GlideUriUtil;


import java.util.ArrayList;
import java.util.List;

public class TrendTagAdapter extends RecyclerView.Adapter<TrendTagAdapter.ViewHolder> {

    private Context context;
    private List<TrendTagsBean> trendTags;
    private static int SCREEN_HEIGHT, SCREEN_WIDTH;

    public TrendTagAdapter(Context context, List<TrendTagsBean> trendTags){
        this.context = context;
        this.trendTags = trendTags;

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.trendTags_imageView);
            textView = itemView.findViewById(R.id.trendTags_textView);

            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = TrendTagAdapter.SCREEN_WIDTH/3;
            imageView.setLayoutParams(lp);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_trendtags, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(trendTags.get(i).getTag());

        if(i==0) {
            ViewGroup.LayoutParams lp = viewHolder.imageView.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = (int)(0.618 * TrendTagAdapter.SCREEN_WIDTH) + 100;
            viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewHolder.imageView.setLayoutParams(lp);
        }

        Glide.with(context)
                .load(GlideUriUtil.getImgByUrl(trendTags.get(i).getIllust().getImage_urls().getMedium()))
                .error(R.mipmap.no_data)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowSearchActivity.class);
            intent.putExtra("tag", trendTags.get(i).getTag());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return trendTags.size();
    }

}
