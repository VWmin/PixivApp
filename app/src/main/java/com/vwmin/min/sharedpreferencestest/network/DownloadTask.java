package com.vwmin.min.sharedpreferencestest.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import android.app.NotificationChannel;
import android.app.NotificationManager;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.utils.GlideUriUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


/**
 *
 * @param1 启动任务时输入的参数类型
 * @param2 后台任务执行中返回进度值的类型
 * @param3 后台任务执行完成后返回结果的类型
 * */

public class DownloadTask extends AsyncTask<String, Integer, Void>  {

    // todo:创建一个服务给下载任务 替换掉context

    private Context context;
    private String savePath;
    private String fileName;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;

    public DownloadTask(Context context, String savePath, String fileName) {
        this.context = context;
        this.fileName = fileName;
        this.savePath = savePath;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "一个任务添加到后台,可在顶部通知栏查看进度", Toast.LENGTH_SHORT).show();

        // 创建通知渠道，需android版本>8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "downloadMsg";
            String channelName = "下载信息";
            int importance = NotificationManager.IMPORTANCE_LOW; // 重要等级
            createNotificationChannel(channelId, channelName, importance);
        }

        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context, "downloadMsg")
                .setContentTitle("下载进度")
                .setContentText("下载中")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_arrow_back_white_24dp)
                .setAutoCancel(true)
                .setProgress(100, 0, false);
        manager.notify(1, builder.build());

    }

    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }


    @Override
    protected Void doInBackground(String... strings) {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String url = strings[0];
            File file = null;
            try {
                file = Glide.with(context)
                        .load(GlideUriUtil.getImgByUrl(url))
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                if(file==null) Log.d("fileNullChk", "下载下来传过去之前是空的！");
                else Log.d("fileNullChk", "下载下来传过去之前不是空的");
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {

//                    File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录

                    File dir = new File(Environment.getExternalStorageDirectory()+savePath);
                    if(!dir.exists()){
                        dir.mkdir();
                    }else{
                        File saveFile = new File(dir, fileName);

//                            Log.d("sdcard", "getName："+saveFile.getName());
//                            Log.d("sdcard", "getPath："+saveFile.getPath());
//                            Log.d("sdcard", "getAbsolutePath："+saveFile.getAbsolutePath());
//                            Log.d("sdcard", "getCanonicalPath："+saveFile.getCanonicalPath());

                        // 创建一个写入路径的数据流
                        FileOutputStream fout = new FileOutputStream(saveFile);
                        FileInputStream fin = new FileInputStream(file);

                        byte[] bytes = new byte[1024];
                        long totalLength = Objects.requireNonNull(file).length();
                        long curLen = 0, len;
                        while ((len = fin.read(bytes)) > 0) {
                            curLen += len;
                            fout.write(bytes);
                            publishProgress((int) ((curLen*1.0 / totalLength) * 100));
                        }
                        fout.close();
                        fin.close();
                    }

                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
//                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                }
            }
            Log.d("downloadTest", "下载完成？");
        }

        return null;
    }


    // 更新进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        builder.setProgress(100, values[0], false);
        builder.setContentText(values[0]+"/100");
        manager.notify(1, builder.build());
        Log.d("downloadProgress", "currentProgress: "+ values[0]);
    }

    // 任务完成
    @Override
    protected void onPostExecute(Void aVoid) {
        builder.setContentTitle("下载完成");
        builder.setContentText("1个任务已经完成");
        manager.notify(1, builder.build());
        Toast.makeText(context, "下载完成~", Toast.LENGTH_SHORT).show();
    }


}
