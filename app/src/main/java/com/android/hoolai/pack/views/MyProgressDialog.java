package com.android.hoolai.pack.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.android.hoolai.pack.R;

public class MyProgressDialog extends Dialog {

    public MyProgressDialog(@NonNull Context context) {
//        super(context);
        super(context, R.style.progress_dialog);
        setContentView(R.layout.custom_my_progress_dialog);
        setCancelable(true);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ((TextView) findViewById(R.id.id_tv_loading_text)).setText("加载中...");
    }
}
