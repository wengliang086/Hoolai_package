package com.android.hoolai.pack.service;

import android.text.TextUtils;

import com.android.hoolai.pack.user.UserConfig;
import com.android.hoolai.pack.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/4/21.
 */
public class HoolaiServiceCreater {

    private static String BASE_URL = "http://61.148.167.74:11116/access_web/";
    private static int BASE_URL_INDEX = 1;

    public static void setBaseUrl(int i) {
        BASE_URL_INDEX = i;
        if (i == 0) {
            BASE_URL = "http://192.168.150.37:8080/access_web/";
        } else {
            BASE_URL = "http://61.148.167.74:11116/access_web/";
        }
    }

    public static int getBaseUrlIndex() {
        return BASE_URL_INDEX;
    }

    public static String getDownLoadUrl(String url) {
        return BASE_URL + "client2/downLoadFile.do?pathType=app&filePath=" + url + "&token=" + UserConfig.accessToken;
    }

    public static HoolaiService create() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        if (!TextUtils.isEmpty(UserConfig.accessToken)) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            LogUtil.i(request.url().toString() + " " + request.headers().toString());
                            LogUtil.i("&token=" + UserConfig.accessToken);
                            Request newReq = request.newBuilder()
                                    .addHeader("token", UserConfig.accessToken)
                                    .build();
                            return chain.proceed(newReq);
                        }
                    }).build();
            builder.client(client);
        }
        return builder.build().create(HoolaiService.class);
    }
}
