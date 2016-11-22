package com.android.hoolai.pack.utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Administrator on 2016/6/7.
 */
public class ProgressDialogUtil {

    public static ProgressDialog show(Activity activity) {
        return ProgressDialog.show(activity, null, "Loading...");
    }

    public static void dismiss(ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }
}
