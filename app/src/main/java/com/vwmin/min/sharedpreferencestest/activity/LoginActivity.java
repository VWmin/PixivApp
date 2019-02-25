package com.vwmin.min.sharedpreferencestest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import com.rey.material.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.adapters.IllustAdapter;
import com.vwmin.min.sharedpreferencestest.network.AppRetrofit;
import com.vwmin.min.sharedpreferencestest.network.LoginRetrofit;
import com.vwmin.min.sharedpreferencestest.response.Illust;
import com.vwmin.min.sharedpreferencestest.response.IllustsResponse;
import com.vwmin.min.sharedpreferencestest.response.LoginResponse;
import com.vwmin.min.sharedpreferencestest.utils.AutoScrollRecyclerView;
import com.vwmin.min.sharedpreferencestest.utils.Density;
import com.vwmin.min.sharedpreferencestest.utils.GridItemDecoration;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class LoginActivity extends BaseActivity  implements  View.OnClickListener{

    // 登陆用参数表
    private static final String LOGIN_PARAM_1 = "MOBrBDS8blbauoSck0ZfDbtuzpyT";
    private static final String LOGIN_PARAM_2 = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj";
    private static final String LOGIN_PARAM_3 = "pixiv";
    private static final boolean LOGIN_PARAM_4 = true;
    private static final String LOGIN_PARAM_5 = "password";

    // 控件部分
    private EditText edit_account;
    private EditText edit_password;
    private CheckBox chk_remember;
    private CardView button_login;
    private Toolbar toolbar_exit;
    private ProgressBar progressBar;
    private AutoScrollRecyclerView recyclerView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );// 状态栏和导航栏的一些参数设置 这里分别是将布局扩展到状态栏后面和稳定布局

        setLayout();
        setControl();
        setListener();


        setLoginBg();


    }

    @Override
    protected void onResume() {
        super.onResume();
        // 设置了适配器,说明连接已完成
        if(recyclerView.getAdapter() != null){
            recyclerView.setLoopEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(recyclerView.getAdapter() != null){
            recyclerView.setLoopEnabled(false);
        }
    }

    @Override
    void setLayout(){
        setContentView(R.layout.activity_login);
    }

    @Override
    void setControl() {



        // 反正就是初始化
        edit_account = findViewById(R.id.edit_account);
        edit_password = findViewById(R.id.edit_password);
        chk_remember = findViewById(R.id.chk_remember);
        button_login = findViewById(R.id.button_login);
        progressBar = findViewById(R.id.progress_login);
        toolbar_exit = findViewById(R.id.toolbar_exit);

        progressBar.setVisibility(View.INVISIBLE);


        /* recyclerView初始化 */
        recyclerView = findViewById(R.id.recycle_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(LoginActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        // 添加自定义分割线（不限于）
        recyclerView.addItemDecoration(new GridItemDecoration(
                2, Density.dip2px(LoginActivity.this, 4.0f), false
        ));
        // 确保图片大小不会影响recycler View宽高的时候设置
        recyclerView.setHasFixedSize(true);


        // 填上上一次的账号
        edit_account.setText(UserInfo.getInstance(this).getAccount());

        // 如果记住密码就填上去，并保持记录
        if(UserInfo.getInstance(this).isRemember()){
            edit_password.setText(UserInfo.getInstance(this).getPassword());
            chk_remember.setChecked(true);
        }

    }

    @Override
    void setListener() {
        button_login.setOnClickListener(this);
        toolbar_exit.setNavigationOnClickListener(v -> {
            ActivityCollection.finishAll();
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                login();
                break;

        }
    }


    private void login(){


        // 获取输入
        String account = edit_account.getText().toString();
        String password = edit_password.getText().toString();

        // 非空判断
        if(account.length() == 0){
            Toast.makeText(LoginActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(password.length() == 0){
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        // 网络登陆逻辑
        Observer<LoginResponse> observer = new Observer<LoginResponse>() {

            @Override
            public void onSubscribe(Disposable d) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                if(loginResponse != null && loginResponse.getResponse() != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    UserInfo.getInstance(LoginActivity.this).saveUserInfo(loginResponse, password, chk_remember.isChecked());
//                    String tag = "yyy";
//                    Log.d(tag, UserInfo.getInstance(LoginActivity.this).getPref().getString("name", "404"));
//                    Log.d(tag, loginResponse.getResponse().getUser().getMail_address()+"???");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "网络似乎出了点问题，请重新登陆", Toast.LENGTH_LONG).show();

                }
            }


            @Override
            public void onError(Throwable e) {
                Log.d("yyy", "error: "+e.getMessage());
                Toast.makeText(LoginActivity.this,
                        "连接失败，网络状态呀，账号密码啊错了（别问我为什么不知道具体情况，问就杀了P站做服务器的）",
                        Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onComplete() {

            }


        };

        LoginRetrofit.getInstance().tryLogin(
                observer,
                LOGIN_PARAM_1, // client_id
                LOGIN_PARAM_2, // client_secret
                LOGIN_PARAM_3, // device_token
                LOGIN_PARAM_4, // get_secure_url
                LOGIN_PARAM_5, // grant_type
                edit_password.getText().toString(), // password
                edit_account.getText().toString()); // username

    }


    private void setLoginBg(){
        Observer<IllustsResponse> observer = new Observer<IllustsResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(IllustsResponse illustsResponse) {
                if(illustsResponse != null && illustsResponse.getIllusts() != null){
                    IllustAdapter adapter = new IllustAdapter(Illust.parserIllustsResponse(illustsResponse), LoginActivity.this);
                    adapter.setClickable(false);
                    adapter.setShowFavorite(false);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLoopEnabled(true);
                    recyclerView.setPointTouch(false);
                    recyclerView.openAutoScroll(); //开启滚动
                }else{
                    Log.d("yyy", "something error");
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.d("yyy", "error: "+e.getMessage());
                Toast.makeText(LoginActivity.this, "请检查网络~", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };

        AppRetrofit.getInstance().getLoginBg(observer);
    }


}
