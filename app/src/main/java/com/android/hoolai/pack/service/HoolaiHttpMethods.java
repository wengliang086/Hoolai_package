package com.android.hoolai.pack.service;

import android.content.Context;

import com.android.hoolai.pack.domain.Channel;
import com.android.hoolai.pack.domain.LoginUser;
import com.android.hoolai.pack.domain.Package;
import com.android.hoolai.pack.domain.Product;
import com.android.hoolai.pack.domain.Version;
import com.android.hoolai.pack.service.interceptor.HeaderInterceptor;
import com.android.hoolai.pack.service.interceptor.LoggingInterceptor;
import com.android.hoolai.pack.user.UserConfig;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/4/21.
 */
public class HoolaiHttpMethods {

    private String BASE_URL = "http://61.148.167.74:11116/access_web/";
    private int BASE_URL_INDEX = 1;//网络环境切换标识
    private static final int DEFAULT_TIMEOUT = 5;

    private static final HoolaiHttpMethods mInstance = new HoolaiHttpMethods();//单例
    private HoolaiService service;

    //构造方法私有
    private HoolaiHttpMethods() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        service = builder.build().create(HoolaiService.class);
    }

    //获取单例
    public static HoolaiHttpMethods getInstance() {
        return mInstance;
    }

    public void login(Context context, ObserverOnNextListener<LoginUser> observerOnNextListener, String account, String password) {
        toObserver(context, service.login(account, password), observerOnNextListener);
    }

    public void getProdects(Context context, ObserverOnNextListener<List<Product>> observerOnNextListener, long uid) {
        toObserver(context, service.getProdects(uid), observerOnNextListener);
    }

    public void getChannels(Context context, ObserverOnNextListener<List<Channel>> observerOnNextListener, long productId) {
        toObserver(context, service.getChannels(productId), observerOnNextListener);
    }

    public void getVersionList(Context context, ObserverOnNextListener<List<Version>> observerOnNextListener, long productId) {
        toObserver(context, service.getVersionList(productId), observerOnNextListener);
    }

    public void getPackageList(Context context, ObserverOnNextListener<List<Package>> observerOnNextListener, long productId, String versionName) {
        toObserver(context, service.getPackageList(productId, versionName), observerOnNextListener);
    }

    public void clientPackage(Context context, ObserverOnNextListener<String> observerOnNextListener, long productId, long channelId, String versionName) {
        toObserver(context, service.clientPackage(productId, channelId, versionName), observerOnNextListener);
    }

    /**
     * 下面两个是封装方法
     */
    private <T> void toObserver(Context context, Observable<T> observable, ObserverOnNextListener<T> observerOnNextListener) {
        toObserver(observable, new ProgressObserver<T>(context, observerOnNextListener));
    }

    private <T> void toObserver(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 下面是网络地址设置相关
     */
    public void setBaseUrl(int i) {
        BASE_URL_INDEX = i;
        if (i == 0) {
            BASE_URL = "http://192.168.150.37:8080/access_web/";
        } else {
            BASE_URL = "http://61.148.167.74:11116/access_web/";
        }
    }

    public int getBaseUrlIndex() {
        return BASE_URL_INDEX;
    }

    public String getDownLoadUrl(String url) {
        return BASE_URL + "client2/downLoadFile.do?pathType=app&filePath=" + url + "&token=" + UserConfig.accessToken;
    }
}
