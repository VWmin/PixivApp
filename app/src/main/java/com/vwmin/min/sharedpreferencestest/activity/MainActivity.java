package com.vwmin.min.sharedpreferencestest.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.data.ViewHistory;
import com.vwmin.min.sharedpreferencestest.fragment.MineFragment;
import com.vwmin.min.sharedpreferencestest.fragment.RankFragment;
import com.vwmin.min.sharedpreferencestest.fragment.RecommendFragment;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.response.FollowingResponse;
import com.vwmin.min.sharedpreferencestest.utils.GlideUriUtil;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static org.litepal.Operator.deleteAll;
import static org.litepal.Operator.findAll;
import static org.litepal.Operator.getDatabase;

public class MainActivity extends BaseActivity
        implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        OnTabSelectListener {

    private NavigationView navigationView;
    private ImageView profile;
    private DrawerLayout drawer;
    private TextView username;
    private TextView useremail;
    private BottomBar bottomNav;
//    private  BottomNavigationView bottomNav;
    private Fragment[] fragments;
    private int current_fragment;
    private Toolbar toolbar;
    private long exitTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));


        setLayout();
        setControl();
        setListener();

    }

    @Override
    void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    void setControl() {
        navigationView = findViewById(R.id.navigation);
        username = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        useremail = navigationView.getHeaderView(0).findViewById(R.id.user_email);
        bottomNav = findViewById(R.id.bottom_navigation);
        profile = navigationView.getHeaderView(0).findViewById(R.id.profile_img);
        drawer = findViewById(R.id.nav_drawer);
        toolbar = findViewById(R.id.toolbar_open_nav);

        toolbar.setTitle("Pixiv");


        /* 设置开启drawer */
        setSupportActionBar(toolbar);



        /* 设置昵称邮箱头像 */
        username.setText(UserInfo.getInstance(this).getName());
        String email = UserInfo.getInstance(this).getEmail();
        useremail.setText(email.equals("")?"该账户未设置邮箱":email);

        String profile_url = UserInfo.getInstance(this).getProfileUrl();

        if(profile_url != null) Glide.with(this)
                                .load(GlideUriUtil.getImgByUrl(profile_url))
                                .into(profile);
        else profile.setImageResource(R.drawable.no_profile);



        initFragments();

        chkPermission();

        switchFragment(0);

    }

    @Override
    void setListener() {
        navigationView.setNavigationItemSelectedListener(this);
        bottomNav.setOnTabSelectListener(this);
//        bottomNav.setOnNavigationItemSelectedListener(this);
        toolbar.setNavigationOnClickListener(v -> drawer.openDrawer(Gravity.START, true));
        // 点搜索图标的响应
        toolbar.setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            return false;
        });
    }

    // 定义 控件 的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 点头像
            case R.id.profile_img:
                break;

        }
    }

    // 定义 侧滑栏 的点击事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
//            /* 这是底部的 */
//            case R.id.tab_recommend:
//                switchFragment(0);
//                return true;
//            case R.id.tab_rank:
//                switchFragment(1);
//                return true;
//            case R.id.tab_mine:
//                switchFragment(2);
//                return true;

            /* 这是侧边的 */
            case R.id.menu_item_following:
                Intent intent = new Intent(MainActivity.this, FollowingActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_item_recommend_users:
                break;
            case R.id.menu_item_favorite:
                break;
            case R.id.menu_item_hot_tags:
                break;
            case R.id.menu_item_history:
                break;
            case R.id.menu_item_setting:
                break;
            case R.id.menu_item_about:
                break;
            case R.id.menu_item_delete:
                getDatabase();
                List<ViewHistory> viewHistoryList = findAll(ViewHistory.class);
                if(viewHistoryList!=null){
                    deleteAll(ViewHistory.class);
                    Toast.makeText(this, "清空完成", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "没有找到任何记录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.menu_item_signOut:
//                ActivityCollection.forceOffline();
                UserInfo.getInstance(MainActivity.this).deleteUserInfo();
                sendBroadcast(new Intent("com.vwmin.min.sharedpreferencestest.FORCE_OFFLINE"));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    // 底部导航栏的点击事件
    @Override
    public void onTabSelected(int tabId) {
        switch (tabId){
            /* 这是底部的 */
            case R.id.tab_recommend:
                switchFragment(0);
//                toolbar.setBackgroundColor(Color.parseColor("#86c166"));
                break ;
            case R.id.tab_rank:
                switchFragment(1);
//                toolbar.setBackgroundColor(Color.parseColor("#58b2dc"));
                break ;
            case R.id.tab_mine:
                switchFragment(2);
//                toolbar.setBackgroundColor(Color.parseColor("#f19483"));
                break ;
        }
    }

    // 创建搜索栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }


    private void initFragments(){
        RecommendFragment recommendFragment = new RecommendFragment();
        RankFragment rankFragment = new RankFragment();
        MineFragment mineFragment = new MineFragment();

        fragments = new Fragment[]{recommendFragment, rankFragment, mineFragment};

        current_fragment = 1;
    }

    private void switchFragment(int position){
        if(position == current_fragment) return;
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        // 执行切换之前的视图已经存在则隐藏之
        if(fragments[current_fragment].isAdded())
            transaction.hide(fragments[current_fragment]);
        // 执行之后的视图不存在则创建之
        if (!fragments[position].isAdded())
            transaction.add(R.id.fragment_container_main_interface, fragments[position]);

        transaction.show(fragments[position]).commit();
        current_fragment = position;
    }

    private void chkPermission(){
        if(this.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "需要外部储存权限", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", this.getPackageName(), null));
            this.startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            if(System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再次点击返回来退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else{
                ActivityCollection.finishAll();
            }
        }
    }



}
