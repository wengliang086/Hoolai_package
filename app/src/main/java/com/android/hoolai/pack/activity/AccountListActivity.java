package com.android.hoolai.pack.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hoolai.pack.GlobalContext;
import com.android.hoolai.pack.MainActivity;
import com.android.hoolai.pack.R;
import com.android.hoolai.pack.domain.LoginUser;
import com.android.hoolai.pack.service.HoolaiServiceCreater;
import com.android.hoolai.pack.user.UserConfig;
import com.android.hoolai.pack.utils.ProgressDialogUtil;
import com.hoolai.access.greendao.bean.DbUser;
import com.hoolai.access.greendao.dao.DbUserDao;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.Query;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/22.
 */
public class AccountListActivity extends Activity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private static List<DbUser> accounts;

    /**
     * 退出时关闭所有Activity的一种方法，必须是第一个Activity
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalContext.application.addActivity(this);
        setContentView(R.layout.activity_account_list);

        recyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        requestForData();

        findViewById(R.id.id_account_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private static void selectAll() {
        Query<DbUser> query = GlobalContext.getDaoSession().getDbUserDao().queryBuilder()
                .orderDesc(DbUserDao.Properties.Date)
                .build();
        accounts = query.list();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void requestForData() {
        selectAll();

        adapter = new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(AccountListActivity.this).inflate(R.layout.item_account, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, final int position) {
                final DbUser user = accounts.get(position);
                holder.textView.setText(user.getAccount());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doLogin(AccountListActivity.this, user.getAccount(), user.getPassword(), false);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        new AlertDialog.Builder(AccountListActivity.this)
                                .setTitle("删除确认")
                                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        GlobalContext.getDaoSession().getDbUserDao().delete(user);
                                        selectAll();
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                        return false;
                    }
                });
            }

            @Override
            public int getItemCount() {
                return accounts.size();
            }

        };
        recyclerView.setAdapter(adapter);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.id_name);
        }
    }

    public static void doLogin(final Activity activity, final String userName, final String password, final boolean close) {
        final ProgressDialog dialog = ProgressDialogUtil.show(activity);
        HoolaiServiceCreater.create().login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginUser>() {
                    @Override
                    public void onCompleted() {
                        ProgressDialogUtil.dismiss(dialog);
                        if (close) {
                            activity.finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ProgressDialogUtil.dismiss(dialog);
                        Log.e("onError", "onError", e);
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(LoginUser user) {
                        addAccount(userName, password);
                        UserConfig.setCurrentUser(user);
                        MainActivity.onSwitchProduct(activity);
                    }
                });
    }

    private static void addAccount(String name, String pwd) {
        // 插入操作，简单到只要你创建一个 Java 对象
        DbUser user = new DbUser(name, pwd, null, new Date());
        GlobalContext.getDaoSession().getDbUserDao().insertOrReplace(user);

        selectAll();
    }
}
