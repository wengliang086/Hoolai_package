package com.android.hoolai.pack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hoolai.pack.R;
import com.android.hoolai.pack.domain.Product;
import com.android.hoolai.pack.domain.Version;
import com.android.hoolai.pack.service.HoolaiHttpMethods;
import com.android.hoolai.pack.service.ObserverOnNextListener;
import com.android.hoolai.pack.views.PackagePagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class PackageFragment extends Fragment {

    private View mView;
    private PackagePagerIndicator indicator;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    private static List<String> mTitles;
    private List<SubPackageFragment> mContents = new ArrayList<>();

    private void setTitles(List<Version> versions) {
        mTitles = new ArrayList<>();
        for (int i = 0; i < versions.size(); i++) {
            mTitles.add(versions.get(i).getVersionName());
        }
    }

    public static List<String> getTitles() {
        return mTitles;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_package_list, container, false);
        requestForData();
        return mView;
    }

    private void afterInitData() {
        indicator = (PackagePagerIndicator) mView.findViewById(R.id.id_indicator);
        indicator.setTitles(mTitles);
        Product product = getActivity().getIntent().getParcelableExtra("product");
        for (int i = 0; i < mTitles.size(); i++) {
            mContents.add(SubPackageFragment.newInstance(product.getProductId(), mTitles.get(i)));
        }
        mViewPager = (ViewPager) mView.findViewById(R.id.id_viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        indicator.setViewPager(mViewPager, 0);
    }

    private void requestForData() {
        Product product = getActivity().getIntent().getParcelableExtra("product");
        HoolaiHttpMethods.getInstance().getVersionList(getActivity(), new ObserverOnNextListener<List<Version>>() {
            @Override
            public void onNext(List<Version> versions) {
                setTitles(versions);
                afterInitData();
            }
        }, product.getProductId());
    }
}
