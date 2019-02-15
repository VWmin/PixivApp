package com.vwmin.min.sharedpreferencestest.network;

import com.vwmin.min.sharedpreferencestest.response.LoginResponse;



import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class LoginRetrofit {

    private static final String LOGIN_BASE_URL = "https://oauth.secure.pixiv.net";

    private static final int DEFAULT_TIMEOUT = 5;


    private LoginApi loginApi;

    // 使默认构造方法私有，那么就只能通过获取单例来调用接口
    private LoginRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(LOGIN_BASE_URL)
                .build();
        loginApi = retrofit.create(LoginApi.class);
    }

    // 访问Retro时创建单例
    private static class SingletonHolder{
        private static final LoginRetrofit INSTANCE = new LoginRetrofit();
    }

    // 获取单例
    public static LoginRetrofit getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private static OkHttpClient getOkhttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)) // 请求拦截器 这里只用来打印request和response
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }



    /* 以下为网络接口 */
    public void tryLogin(Observer<LoginResponse> observer,
                         String client_id,
                         String client_secret,
                         String device_token,
                         boolean get_secure_url,
                         String grant_type,
                         String password,
                         String username){
        loginApi.tryLogin(client_id, client_secret, device_token, get_secure_url, grant_type, password, username)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 刷新Token
    public void refreshToken(Observer<LoginResponse> observer,
                             String client_id,
                             String client_secret,
                             String grant_type,
                             String refresh_token,
                             String device_token,
                             boolean get_secure_url){
        loginApi.refreshToken(client_id, client_secret, grant_type, refresh_token, device_token, get_secure_url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private interface LoginApi {


        /**
         * 登陆
         *
         * @return 返回一个被观测的解析对象
         */
        @FormUrlEncoded
        @POST("/auth/token")
        @Headers({"User-Agent:PixivAndroidApp/5.0.119 (Android 6.0.1; D6653)", "Accept-Language:zh_CN"})
        // 被观测者，通过subscribe与观测者达成观测关系 它决定什么时候触发事件以及触发怎样的事件
        Observable<LoginResponse> tryLogin(@Field("client_id") String client_id,
                                           @Field("client_secret") String client_secret,
                                           @Field("device_token") String device_token,
                                           @Field("get_secure_url") boolean get_secure_url,
                                           @Field("grant_type") String grant_type,
                                           @Field("password") String password,
                                           @Field("username") String username);

        /**
         * 刷新token
         * @param client_id
         * @param client_secret
         * @param grant_type
         * @param refresh_token
         * @param device_token
         * @param get_secure_url
         * @return
         */
        @FormUrlEncoded
        @POST("/auth/token")
        Observable<LoginResponse> refreshToken(@Field("client_id") String client_id,
                                               @Field("client_secret") String client_secret,
                                               @Field("grant_type") String grant_type,
                                               @Field("refresh_token") String refresh_token,
                                               @Field("device_token") String device_token,
                                               @Field("get_secure_url") boolean get_secure_url);



    }


}

