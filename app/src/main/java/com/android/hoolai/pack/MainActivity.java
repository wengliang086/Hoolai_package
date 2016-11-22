package com.android.hoolai.pack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.hoolai.pack.activity.AccountListActivity;
import com.android.hoolai.pack.activity.LoginActivity;
import com.android.hoolai.pack.activity.ProductListActivity;
import com.android.hoolai.pack.adapter.TabPagerFragmentAdapter;
import com.android.hoolai.pack.fragment.ToolbarFragment;
import com.android.hoolai.pack.utils.T;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalContext.application.addActivity(this);
        setContentView(R.layout.activity_main);

        initDrawer();

        SViewPager viewPager = (SViewPager) findViewById(R.id.view_pager);
        Indicator indicator = (Indicator) findViewById(R.id.indicator);

        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new TabPagerFragmentAdapter(getSupportFragmentManager(), this));
        // 禁止viewpager的滑动事件
        viewPager.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(3);
    }

    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        findViewById(R.id.id_drawer_setting).setOnClickListener(this);
        findViewById(R.id.id_drawer_about).setOnClickListener(this);
        findViewById(R.id.id_switch_account).setOnClickListener(this);
        findViewById(R.id.id_switch_product).setOnClickListener(this);
        findViewById(R.id.id_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_drawer_setting:
                ToolbarFragment.onSettingClick(this);
                break;
            case R.id.id_drawer_about:
                ToolbarFragment.onAboutClick(this);
                break;
            case R.id.id_switch_account:
                onSwitchAccount(this);
                break;
            case R.id.id_switch_product:
                onSwitchProduct(this);
                break;
            case R.id.id_exit:
                ToolbarFragment.onExitClick(this);
                break;
            default:
                T.show(this, "未处理的点击事件！");
                break;
        }
    }

    public static void onSwitchAccount(Activity activity) {
        activity.startActivity(new Intent(activity, AccountListActivity.class));
    }

    public static void onSwitchProduct(Activity activity) {
        activity.startActivity(new Intent(activity, ProductListActivity.class));
    }
}
