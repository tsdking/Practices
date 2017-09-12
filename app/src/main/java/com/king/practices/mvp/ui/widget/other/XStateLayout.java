package com.king.practices.mvp.ui.widget.other;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.king.practices.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 多状态布局
 * 1.默认内容 2.加载中 3.空数据 4.网络错误
 */
public class XStateLayout extends FrameLayout {

    private int mContentId = NO_ID;
    private LayoutInflater mInflater;

    private Map<Integer, View> mLayouts = new HashMap<>();

    public XStateLayout(@NonNull Context context) {
        this(context, null);
    }

    public XStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        mInflater=LayoutInflater.from(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XStateLayout, defStyleAttr, R.style.XStateLayout);
        // TODO: 2017/9/13 获取自定义的属性

        typedArray.recycle();
    }

    /**********************wrap********************/
    public static XStateLayout wrap(Activity activity) {
        return wrap(((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0));
    }
    public static XStateLayout wrap(Fragment fragment) {
        return wrap(fragment.getView());
    }
    public static XStateLayout wrap(View view) {
        if (view == null) {
            throw new RuntimeException("content view can not be null");
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (view == null) {
            throw new RuntimeException("parent view can not be null");
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);

        XStateLayout layout = new XStateLayout(view.getContext());
        parent.addView(layout, index, lp);
        layout.addView(view);
        layout.setContentView(view);
        return layout;
    }

    /**********************wrap********************/
    private void setContentView(View view) {
        mContentId = view.getId();
        mLayouts.put(mContentId, view);
    }
}
