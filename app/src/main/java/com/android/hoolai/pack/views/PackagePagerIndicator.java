package com.android.hoolai.pack.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hoolai.pack.R;

import java.util.List;

/**
 * Created by Administrator on 2016/5/31.
 */
public class PackagePagerIndicator extends LinearLayout {

    private static final int COLOR_TEXT_NORMAL = Color.parseColor("#ff475787");
    private static final int COLOR_TEXT_HIGHLIGHT = Color.WHITE;
    private ViewPager mViewPager;
    private final int mTabVisibleCount = 3;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count == 0) {
            return;
        }
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public PackagePagerIndicator(Context context) {
        this(context, null);
    }

    public PackagePagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTitles(List<String> mTitles) {
        if (mTitles == null || mTitles.size() == 0) {
            return;
        }
        this.removeAllViews();
        for (int i = 0; i < mTitles.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setSingleLine(true);
            textView.setPadding(5, 5, 5, 5);
            textView.setText(mTitles.get(i));
            textView.setGravity(Gravity.CENTER);
            if (i == 0) {
                textView.setBackgroundResource(R.drawable.tab_ranking_left_selector);
            } else if (i == mTitles.size() - 1) {
                textView.setBackgroundResource(R.drawable.tab_ranking_right_selector);
            } else {
                textView.setBackgroundResource(R.drawable.tab_ranking_center_selector);
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            this.addView(textView);
        }
        onFinishInflate();
    }

    public void setViewPager(ViewPager viewpager, int position) {
        mViewPager = viewpager;
        setItemClickEvent();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                highLightTextView(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(position);
        highLightTextView(position);
    }

    /**
     * 高亮被点击的tab
     *
     * @param position
     */
    private void highLightTextView(int position) {
        resetTextViewColor();
        View view = getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHT);
            view.setEnabled(false);
        }
    }

    /**
     * 重置tab文本颜色
     */
    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
                view.setEnabled(true);
            }
        }
    }

    /**
     * 设置Tab的点击事件
     */
    private void setItemClickEvent() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int j = i;
            View view = getChildAt(i);
            if (view instanceof TextView) {
                view.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(j);
                    }
                });
            }
        }
    }

    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / mTabVisibleCount;
        int mTx = (int) (tabWidth * (offset + position));
        // 容器移动，当tab处于移动至最后一个时
        if (getChildCount() > mTabVisibleCount && position >= mTabVisibleCount - 2 && offset > 0) {
            this.scrollTo((position - (mTabVisibleCount - 2)) * tabWidth + (int) (tabWidth * offset), 0);
        }
        invalidate();
    }
}
