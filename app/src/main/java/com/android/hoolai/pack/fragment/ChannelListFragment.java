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
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.hoolai.pack.R;
import com.android.hoolai.pack.adapter.ChannelAdapter;
import com.android.hoolai.pack.domain.Channel;
import com.android.hoolai.pack.domain.Product;
import com.android.hoolai.pack.service.HoolaiServiceCreater;
import com.android.hoolai.pack.utils.LogUtil;
import com.android.hoolai.pack.utils.ProgressDialogUtil;
import com.android.hoolai.pack.utils.T;
import com.gc.materialdesign.views.ButtonFlat;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/30.
 */
public class ChannelListFragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private ChannelAdapter mAdapter;
    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_channel_list, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.id_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        requestForData();
        // 选择版本
        ButtonFlat button = (ButtonFlat) mView.findViewById(R.id.btn_select_version);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .items(PackageFragment.getTitles())
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                tv = (TextView) mView.findViewById(R.id.tv_current_version);
                                tv.setText(text);
                                return true;
                            }
                        })
                        .positiveText("确定")
                        .show();
            }
        });
        // 打包
        ButtonFlat btn = (ButtonFlat) mView.findViewById(R.id.btn_package);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientPackage();
            }
        });
        return mView;
    }

    private void requestForData() {
        Product product = getActivity().getIntent().getParcelableExtra("product");
        final ProgressDialog dialog = ProgressDialogUtil.show(getActivity());
        HoolaiServiceCreater.create().getChannels(product.getProductId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Channel>>() {
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
                    public void onNext(List<Channel> channels) {
                        mAdapter = new ChannelAdapter(getActivity(), channels);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
    }

    private void clientPackage() {
        int channelId = mAdapter.getFirstCheckedChannelId();
        String versionName = tv.getText().toString();
        Product product = getActivity().getIntent().getParcelableExtra("product");
        final ProgressDialog dialog = ProgressDialogUtil.show(getActivity());
        HoolaiServiceCreater.create().clientPackage(product.getProductId(), channelId, versionName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        ProgressDialogUtil.dismiss(dialog);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(String s) {
                        T.show(getActivity(), s);
                    }
                });
    }
}
