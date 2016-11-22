package com.android.hoolai.pack.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.hoolai.pack.GlobalContext;
import com.android.hoolai.pack.R;
import com.android.hoolai.pack.adapter.ProductAdapter;
import com.android.hoolai.pack.domain.Product;
import com.android.hoolai.pack.service.HoolaiServiceCreater;
import com.android.hoolai.pack.user.UserConfig;
import com.android.hoolai.pack.utils.LogUtil;
import com.android.hoolai.pack.utils.ProgressDialogUtil;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/30.
 */
public class ProductListActivity extends FragmentActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalContext.application.addActivity(this);
        setContentView(R.layout.activity_product_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        requestForData();
    }

    private void requestForData() {
        long uid = Long.parseLong(UserConfig.getCurrentUser().getUid());
        final ProgressDialog dialog = ProgressDialogUtil.show(this);
        HoolaiServiceCreater.create().getProdects(uid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Product>>() {
                    @Override
                    public void onCompleted() {
                        ProgressDialogUtil.dismiss(dialog);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ProgressDialogUtil.dismiss(dialog);
                        LogUtil.e(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(List<Product> products) {
                        mAdapter = new ProductAdapter(ProductListActivity.this, products);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
    }

}
