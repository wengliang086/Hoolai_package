package com.android.hoolai.pack.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.hoolai.pack.R;
import com.android.hoolai.pack.activity.AccountListActivity;
import com.android.hoolai.pack.service.HoolaiHttpMethods;

/**
 * Created by Administrator on 2016/6/6.
 */
public class ToolbarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toolbar, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.id_toolbar);
        toolbar.setLogo(R.drawable.hl_sdk_logo);
        toolbar.setNavigationIcon(R.color.color_0176da);
        toolbar.setTitle("打包工具");
        toolbar.setTitleTextAppearance(getActivity(), R.style.Theme_ToolBar_Base_Title);//修改主标题的外观，包括文字颜色，文字大小等
//        toolbar.setSubtitle("登录页面");
//        toolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));
//        toolbar.setSubtitleTextAppearance(this, R.style.Theme_ToolBar_Base_SubTitle);
        toolbar.inflateMenu(R.menu.base_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        break;
                    case R.id.action_notification:
                        break;
                    case R.id.action_setting:
                        onSettingClick(getActivity());
                        break;
                    case R.id.action_about:
                        onAboutClick(getActivity());
                        break;
                    case R.id.action_exit:
                        onExitClick(getActivity());
                        break;
                }
                return true;
            }
        });
        return view;
    }

    public static void onSettingClick(Activity activity) {
        new MaterialDialog.Builder(activity)
                .title("网络选择")
                .items("开发环境", "测试环境")
                .itemsCallbackSingleChoice(HoolaiHttpMethods.getInstance().getBaseUrlIndex(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        HoolaiHttpMethods.getInstance().setBaseUrl(which);
                        return true;
                    }
                })
                .positiveText("提交")
                .show();
    }

    public static void onAboutClick(Activity activity) {
        new MaterialDialog.Builder(activity)
                .content("Android版打包工具0.0.1")
                .positiveText("关闭")
                .show();
    }

    public static void onExitClick(final Activity activity) {
        new MaterialDialog.Builder(activity)
                .positiveText("确定")
                .title("退出确认")
                .content("确定要退出Android打包工具？")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        Process.killProcess(Process.myPid());
//                        System.exit(0);
                        // 第一种退出方式
//                        GlobalContext.application.exit();
                        // 第二种退出方式
                        Intent intent = new Intent(activity, AccountListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                        activity.finish();
                        // 第三种退出方式（BroadcastReceiver）
                        // 暂未实现
                    }
                })
                .negativeText("取消")
                .show();
    }
}
