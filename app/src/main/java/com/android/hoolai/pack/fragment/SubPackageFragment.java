package com.android.hoolai.pack.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hoolai.pack.R;
import com.android.hoolai.pack.domain.Package;
import com.android.hoolai.pack.service.HoolaiServiceCreater;
import com.android.hoolai.pack.utils.LogUtil;
import com.android.hoolai.pack.utils.ProgressDialogUtil;
import com.android.hoolai.pack.utils.T;
import com.android.hoolai.pack.utils.UpdateService;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/31.
 */
public class SubPackageFragment extends Fragment {

    private String mTitle;
    private int productId;
    private static final String BUNDLE_TITLE = "title";
    private static final String BUNDLE_Product_ID = "productId";
    private View baseView;
    private List<Package> mDataSet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(BUNDLE_TITLE);
            productId = bundle.getInt(BUNDLE_Product_ID);
        }
        baseView = inflater.inflate(R.layout.fragment_package_list_sub, container, false);
        initData(inflater);
        return baseView;
    }

    private void initData(final LayoutInflater inflater) {
        final ProgressDialog dialog = ProgressDialogUtil.show(getActivity());
        HoolaiServiceCreater.create().getPackageList(productId, mTitle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Package>>() {
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
                    public void onNext(List<Package> packages) {
                        mDataSet = packages;
                        afterCreateView(inflater);
                    }
                });
    }

    private void afterCreateView(final LayoutInflater inflater) {
        RecyclerView recyclerview = (RecyclerView) baseView.findViewById(R.id.id_recyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = inflater.inflate(R.layout.item_package, parent, false);
                return new MyViewHolder(v);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, final int position) {
                final Package value = mDataSet.get(position);
                holder.itemView.setTag(value);
                holder.id.setText("" + value.getId());
                holder.name.setText(value.getChannel());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        T.show(getActivity(), "开始下载...");
                        downloadApk(value.getDownloadPath(), value.getChannel());
//                        downloadApk2(value.getDownloadPath(), value.getChannel());
                    }
                });
                holder.desc.setText(value.getMark());
                if (value.getStatus() == Package.STATUS_PACKAGE_FAIELD) {
                    holder.itemView.setClickable(false);
                    holder.status.setVisibility(View.VISIBLE);
                    holder.imageView.setVisibility(View.GONE);
                    holder.status.setText("打包失败");
                } else if (value.getStatus() == Package.STATUS_PACKAGE_SUCCESSED) {
                    holder.itemView.setClickable(true);
                    holder.status.setVisibility(View.GONE);
                    holder.imageView.setVisibility(View.VISIBLE);
                } else if (value.getStatus() == Package.STATUS_PACKAGING) {
                    holder.itemView.setClickable(false);
                    holder.status.setVisibility(View.VISIBLE);
                    holder.imageView.setVisibility(View.GONE);
                    holder.status.setText("进行中");
                }
            }

            @Override
            public int getItemCount() {
                return mDataSet.size();
            }
        });
    }

    private void downloadApk(String url, String name) {
        String completeUrl = HoolaiServiceCreater.getDownLoadUrl(url);
        LogUtil.i(completeUrl);

        Intent intent = new Intent();
        intent.setAction("com.hoolai.open.fastaccess.UpdateService");
        intent.setPackage(getActivity().getPackageName());
        intent.putExtra(UpdateService.DOWNLOAD_URL, completeUrl);
        intent.putExtra(UpdateService.DOWNLOAD_APP_NAME, name);
        getActivity().startService(intent);
    }

    private void downloadApk2(String url, String name) {
        String completeUrl = HoolaiServiceCreater.getDownLoadUrl(url);
        LogUtil.i(completeUrl);

//        completeUrl = "http://pkg.fir.im/bd272d971b8922fe48736ba9f30a67d862753aad?attname=app-debug.apk_1.0.apk&e=1465283476&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:F_GGmZ4uL31esyq0jYYnRxCrwKs=";
        String path = getActivity().getExternalCacheDir().getAbsolutePath() + File.separator + "apk" + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String pathAndName = path + name + ".apk";
        FileDownloader.getImpl().create(completeUrl).setPath(pathAndName)
                .setListener(new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        LogUtil.d("pending_long");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        LogUtil.d("progress_long");
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        LogUtil.d("blockComplete");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        LogUtil.d("completed");
                        Uri apk = Uri.parse("file://" + task.getPath());
                        installAPK(apk);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        LogUtil.d("paused_long");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtil.e("error", e);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        LogUtil.d("warn");
                    }

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.d("pending_int");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.d("progress_int");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.d("paused_int");
                    }
                }).start();
    }

    /**
     * 安装apk文件
     */
    private void installAPK(Uri apk) {
        // 通过Intent安装APK文件
        Intent intents = new Intent();
        intents.setAction("android.intent.action.VIEW");
        intents.addCategory("android.intent.category.DEFAULT");
        intents.setType("application/vnd.android.package-archive");
        intents.setData(apk);
        intents.setDataAndType(apk, "application/vnd.android.package-archive");
        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            android.os.Process.killProcess(android.os.Process.myPid());
        // 如果不加上这句的话在apk安装完成之后点击单开会崩溃
        try {
            startActivity(intents);
        } catch (Exception e) {
            LogUtil.e("UpdataService", "installAPK", e);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        TextView desc;
        ImageView imageView;
        TextView status;

        public MyViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id_p_id);
            name = (TextView) itemView.findViewById(R.id.id_p_name);
            desc = (TextView) itemView.findViewById(R.id.id_p_desc);
            imageView = (ImageView) itemView.findViewById(R.id.id_download);
            status = (TextView) itemView.findViewById(R.id.id_status);
        }
    }

    public static SubPackageFragment newInstance(int productId, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        bundle.putInt(BUNDLE_Product_ID, productId);
        SubPackageFragment fragment = new SubPackageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
