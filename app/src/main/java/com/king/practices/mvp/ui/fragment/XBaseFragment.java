package com.king.practices.mvp.ui.fragment;

import android.os.Bundle;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.mvp.IPresenter;

/**
 * 与Viewpager结合使用的Fragment，实现懒加载
 */
public abstract class XBaseFragment<P extends IPresenter> extends BaseFragment<P> implements IFragment {
//    private boolean isViewInitiated;//true  视图初始化完成；
//    private boolean isVisibleToUser;//true  当前页面对用户可见
//    private boolean isDataInitiated;//true  数据已初始化完成
private static final String TAG = "xqf";
    private boolean isFirstInvisible = true;
    private boolean isFirstVisible = true;
    private boolean isPrepared;
    private boolean isVisibleToUser;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        isViewInitiated = true;
//        prepareFetchData();
        isFirstInvisible = true;
        isFirstVisible = true;
        isPrepared = true;
        initPrepare();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isPrepared) {
            if (getUserVisibleHint()) {
                onUserInvisible();
            }
        }
    }

    protected void onUserInvisible() {
    }

    protected void onUserVisible() {
    }

    protected void onFirstUserVisible() {
    }

    protected void onFirstUserInvisible() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        this.isVisibleToUser = isVisibleToUser;
//        prepareFetchData();
        this.isVisibleToUser = isVisibleToUser;
        if (!isPrepared) {
            return;
        }
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                onFirstUserVisible();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

//    public boolean prepareFetchData() {
//        return prepareFetchData(false);
//    }

//    public boolean prepareFetchData(boolean forceUpdate) {
//        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
//            fetchData();
//            isDataInitiated = true;
//            return true;
//        }
//        return false;
//    }


    private synchronized void initPrepare() {
        if (isVisibleToUser) {
            isFirstVisible = false;
            onFirstUserVisible();
        }
    }


    /**
     * 第一次对用户可见时获取数据(懒加载)
     */
    public abstract void fetchData();


}
