package com.android.hoolai.pack.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.hoolai.pack.GlobalContext;
import com.android.hoolai.pack.R;
import com.android.hoolai.pack.utils.MD5;
import com.android.hoolai.pack.utils.T;

/**
 * Created by Administrator on 2016/5/27.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalContext.application.addActivity(this);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_login).setOnClickListener(this);
        checkAPP(this);
    }

    int checkAPP(Context context) {
        try {
            String packageName = context.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];

            int hashcode = sign.hashCode();
            byte[] bytes = sign.toByteArray();
            String md5 = MD5.getMessageDigest(bytes);
            Log.i("testttt", "hashCode : " + hashcode + ", md5=" + md5);
            return hashcode == -82892576 ? 1 : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String userName = ((EditText) findViewById(R.id.name)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                AccountListActivity.doLogin(this, userName, password, true);
                break;
            default:
                T.show(this, "未处理的点击事件！");
                break;
        }
    }

}
