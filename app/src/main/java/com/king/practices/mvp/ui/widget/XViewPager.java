package com.king.practices.mvp.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 扩展了滑动功能的ViewPager
 * 默认禁止滑动
 */
public class XViewPager extends ViewPager {
    private boolean mScrollable = false;

    public XViewPager(Context context) {
        super(context);
    }

    public XViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.mScrollable && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.mScrollable && super.onInterceptTouchEvent(event);
    }

    public void setScrollable(boolean scrollable) {
        mScrollable = scrollable;
    }
}
