package com.king.practices.mvp.ui.widget.other;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.practices.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 多状态布局
 * 1.默认内容 2.加载中 3.空数据 4.网络错误
 */
public class StateLayout extends FrameLayout {

    private int mContentId = NO_ID;
    private LayoutInflater mInflater;

    private Map<Integer, View> mLayouts = new HashMap<>();

    public StateLayout(@NonNull Context context) {
        this(context, null);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private View contentView;

    private View emptyView;

    private View errorView;

    private View progressView;

    private TextView emptyTextView;
    private TextView errorTextView;
    private TextView progressTextView;

    private ImageView errorImageView;
    private ImageView emptyImageView;
    private ImageView progressImage;
    private int progressViewId;
    private int emptyViewId;
    private int errorViewId;
    private String emptyText = "empty";
    private String errorText = "error";

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        mInflater = LayoutInflater.from(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0);
        Drawable errorDrawable;
        Drawable emptyDrawable;
        try {
            //加载中布局id
            progressViewId = a.getResourceId(R.styleable.StateLayout_ProgressResId, -1);

            //空视图布局id
            emptyViewId = a.getResourceId(R.styleable.StateLayout_EmptyResId, -1);
            emptyDrawable = a.getDrawable(R.styleable.StateLayout_EmptyImageResId);
            emptyText = a.getString(R.styleable.StateLayout_EmptyText);

            //错误(网络错误或超时等)视图布局id
            errorViewId = a.getResourceId(R.styleable.StateLayout_ErrorResId, -1);
            errorDrawable = a.getDrawable(R.styleable.StateLayout_ErrorImageResId);
            errorText = a.getString(R.styleable.StateLayout_ErrorText);
        } finally {
            a.recycle();
        }

        //设置加载中布局
        if (progressViewId != -1) {
            progressView = mInflater.inflate(progressViewId, this, false);
        }

        //设置空视图布局
        if (emptyViewId != -1) {
            emptyView = mInflater.inflate(emptyViewId, this, false);
        }

        //设置错误视图布局
        if (errorViewId != -1) {
            errorView = mInflater.inflate(errorViewId, this, false);
        }


    }

    /**********************
     * wrap
     ********************/
    public static StateLayout wrap(Activity activity) {
        return wrap(((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0));
    }

    public static StateLayout wrap(Fragment fragment) {
        return wrap(fragment.getView());
    }

    public static StateLayout wrap(View view) {
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

        StateLayout layout = new StateLayout(view.getContext());
        parent.addView(layout, index, lp);
        layout.addView(view);
        layout.setContentView(view);
        return layout;
    }

    /**********************
     * wrap
     ********************/
    private void setContentView(View view) {
        mContentId = view.getId();
        mLayouts.put(mContentId, view);
    }
}
