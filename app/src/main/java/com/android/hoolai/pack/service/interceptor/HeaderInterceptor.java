package com.android.hoolai.pack.service.interceptor;

import com.android.hoolai.pack.user.UserConfig;
import com.android.hoolai.pack.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/14.
 * 请求头拦截器
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtil.i("&token=" + UserConfig.accessToken);
        Request newReq = request.newBuilder()
                .addHeader("token", UserConfig.accessToken)
                .build();
        return chain.proceed(newReq);
    }
}
