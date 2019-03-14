package com.vwmin.min.sharedpreferencestest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;
import com.vwmin.min.sharedpreferencestest.event.IllustChangeEvent;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.response.FollowingResponse;
import com.vwmin.min.sharedpreferencestest.response.NullResponse;
import com.vwmin.min.sharedpreferencestest.utils.GlideUriUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<FollowingResponse.UserPreviewsBean> followings;
    private static int SCREEN_HEIGHT, SCREEN_WIDTH;

    public UserAdapter(Context context, List<FollowingResponse.UserPreviewsBean> followings){
        this.context = context;
        this.followings = followings;

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
    }
    public UserAdapter(Context context){
        this.context = context;

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView userProfile;
        private TextView userName;
        private Button follow;
        private ImageView[] imageViews = new ImageView[3];
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.user_profile);
            userName = itemView.findViewById(R.id.user_name);
            follow = itemView.findViewById(R.id.follow_button);
            imageViews[0] = itemView.findViewById(R.id.image1);
            imageViews[1] = itemView.findViewById(R.id.image2);
            imageViews[2] = itemView.findViewById(R.id.image3);

            follow.setText("✓已关注");

            ViewGroup.LayoutParams lp = imageViews[0].getLayoutParams();
            lp.width = (SCREEN_WIDTH-32)/3;
            lp.height = lp.width;

            for(int i=0; i<3; i++) imageViews[i].setLayoutParams(lp);



            userProfile.bringToFront();

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_user, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context)
                .load(GlideUriUtil.getImgByUrl(followings.get(i).getUser().getProfile_image_urls().getMedium()))
                .into(viewHolder.userProfile);
        viewHolder.userName.setText(followings.get(i).getUser().getName());

        viewHolder.follow.setOnClickListener(v->{
            if(followings.get(i).getUser().isIs_followed()) postFollow(viewHolder.follow, i, false); //已关注则取消关注
            else postFollow(viewHolder.follow, i, true);
        });

        for(int pos=0; pos<3; pos++)
        Glide.with(context)
                .load(GlideUriUtil.getImgByUrl(followings.get(i).getIllusts().get(pos).getImage_urls().getMedium()))
                .into(viewHolder.imageViews[pos]);

    }

    @Override
    public int getItemCount() {
        return followings.size();
    }


    private void postFollow(Button follow, int i, boolean choose){
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
                    follow.setTextColor(context.getColor(R.color.white));
                    follow.setBackground(context.getDrawable(R.drawable.btn_bg_blue));
                    Toast.makeText(context, "已关注", Toast.LENGTH_SHORT).show();
                    followings.get(i).getUser().setIs_followed(true);
                }
            };
            AppRetrofit.getInstance().followUser(observer,
                    UserInfo.getInstance(context).getAuthorization(),
                    followings.get(i).getUser().getId()+"",
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
                    follow.setTextColor(context.getColor(R.color.black));
                    follow.setBackground(context.getDrawable(R.drawable.btn_bg_white));
                    Toast.makeText(context, "已取消关注", Toast.LENGTH_SHORT).show();
                    followings.get(i).getUser().setIs_followed(false);
                }
            };
            AppRetrofit.getInstance().unFollowUser(observer,
                    UserInfo.getInstance(context).getAuthorization(),
                    followings.get(i).getUser().getId()+"");
        }
    }

}
