package com.android.hoolai.pack.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hoolai.pack.R;
import com.android.hoolai.pack.adapter.ProductAdapter;
import com.android.hoolai.pack.adapter.VersionAdapter;
import com.android.hoolai.pack.domain.Product;
import com.android.hoolai.pack.domain.Version;
import com.android.hoolai.pack.service.HoolaiServiceCreater;
import com.android.hoolai.pack.utils.LogUtil;
import com.android.hoolai.pack.utils.ProgressDialogUtil;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/30.
 */
public class VersionListFragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_version_list, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.id_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        requestForData();
        return mView;
    }

    private void requestForData() {
        Product product = getActivity().getIntent().getParcelableExtra("product");
        final ProgressDialog dialog = ProgressDialogUtil.show(getActivity());
        HoolaiServiceCreater.create().getVersionList(product.getProductId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Version>>() {
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
                    public void onNext(List<Version> versions) {
                        mAdapter = new VersionAdapter(getActivity(), versions);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
    }
}
