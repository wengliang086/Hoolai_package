package com.android.hoolai.pack.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hoolai.pack.R;
import com.android.hoolai.pack.fragment.ChannelListFragment;
import com.android.hoolai.pack.fragment.PackageFragment;
import com.android.hoolai.pack.fragment.VersionListFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;

/**
 * Created by Administrator on 2016/5/30.
 */
public class TabPagerFragmentAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private String[] tabNames = {"版本管理", "打包管理", "打包列表"};
    private int[] tabIcons = {R.drawable.tab_pitches_selector, R.drawable.tab_matches_selector, R.drawable.tab_profile_selector};

    private LayoutInflater inflater;

    public TabPagerFragmentAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tab_main, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(tabNames[position]);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
        return textView;
    }

    @Override
    public Fragment getFragmentForPage(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new VersionListFragment();
                break;
            case 1:
                fragment = new ChannelListFragment();
                break;
            case 2:
                fragment = new PackageFragment();
                break;
        }
        return fragment;
    }

}
