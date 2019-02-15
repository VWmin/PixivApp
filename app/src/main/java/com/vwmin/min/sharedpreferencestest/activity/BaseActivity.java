package com.vwmin.min.sharedpreferencestest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;


public abstract class BaseActivity extends AppCompatActivity {

    private ForceOfflineReceiver receiver = null;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollection.addActivity(this);

        getWindow().setStatusBarColor(Color.TRANSPARENT); //设置状态栏透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );// 状态栏和导航栏的一些参数设置 这里分别是将布局扩展到状态栏后面和稳定布局
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);// 设置导航栏透明，上语句也能设置来着

    }

    // 当前活动重新启动时，接收器开始工作
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(); // 一个意图筛选器
        intentFilter.addAction("com.vwmin.min.sharedpreferencestest.FORCE_OFFLINE");
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver, intentFilter); // 为这个接收器加筛选条件
    }


    // 当前活动结束时，释放接收器
    @Override
    protected void onPause() {
        super.onPause();
        if(receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollection.removeActivity(this);

    }


    // 强制下线广播接收器
    class ForceOfflineReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context); //在当前活动背景下创建警告框
            builder.setTitle("FBI Warning");
            builder.setMessage("You are forced to be offline, please try again later.");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", (dialog, which) -> {
                ActivityCollection.finishAll();
                Intent intent1 = new Intent(context, LoginActivity.class);
                context.startActivity(intent1); // 通过当前活动背景启动活动
            });
            builder.show(); // 别忘了显示

        }
    }



    abstract void setLayout();

    abstract void setControl();

    abstract void setListener();
}
