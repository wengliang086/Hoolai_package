package com.android.hoolai.pack.service;

import com.android.hoolai.pack.domain.Channel;
import com.android.hoolai.pack.domain.LoginUser;
import com.android.hoolai.pack.domain.Package;
import com.android.hoolai.pack.domain.Product;
import com.android.hoolai.pack.domain.Version;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/5/27.
 */
public interface HoolaiService {

    @GET("client2/loginByAccount_client.do")
    Observable<LoginUser> login(@Query("account") String account, @Query("password") String password);

    @GET("client2/getProdects.do")
    Observable<List<Product>> getProdects(@Query("uid") long uid);

    @GET("client2/getChannels.do")
    Observable<List<Channel>> getChannels(@Query("productId") long productId);

    @GET("client2/getVersionList.do")
    Observable<List<Version>> getVersionList(@Query("productId") long productId);

    @GET("client2/getPackageList.do")
    Observable<List<Package>> getPackageList(@Query("productId") long productId, @Query("versionName") String versionName);

    @GET("client2/clientPackage.do")
    Observable<String> clientPackage(@Query("productId") long productId, @Query("channelId") long channelId, @Query("versionName") String versionName);
}
