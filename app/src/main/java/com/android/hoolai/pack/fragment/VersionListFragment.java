package com.android.hoolai.pack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hoolai.pack.R;
import com.android.hoolai.pack.adapter.VersionAdapter;
import com.android.hoolai.pack.domain.Product;
import com.android.hoolai.pack.domain.Version;
import com.android.hoolai.pack.service.HoolaiHttpMethods;
import com.android.hoolai.pack.service.ObserverOnNextListener;

import java.util.List;

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
        HoolaiHttpMethods.getInstance().getVersionList(getActivity(), new ObserverOnNextListener<List<Version>>() {
            @Override
            public void onNext(List<Version> versions) {
                mAdapter = new VersionAdapter(getActivity(), versions);
                mRecyclerView.setAdapter(mAdapter);
            }
        }, product.getProductId());
    }
}
