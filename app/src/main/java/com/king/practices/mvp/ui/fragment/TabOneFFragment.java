package com.king.practices.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.king.practices.R;
import com.king.practices.di.component.DaggerTabOneFComponent;
import com.king.practices.di.module.TabOneFModule;
import com.king.practices.mvp.contract.TabOneFContract;
import com.king.practices.mvp.presenter.TabOneFPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/9/4.
 */

public class TabOneFFragment extends XBaseFragment<TabOneFPresenter> implements TabOneFContract.View {

    @BindView(R.id.tv_index)
    TextView tvIndex;
    private String mIndex;

    @Override
    public void fetchData() {
        tvIndex.setText(mIndex);
    }

    public static TabOneFFragment newInstance(String index) {
        TabOneFFragment fragment = new TabOneFFragment();
        Bundle args = new Bundle();
        // TODO: 2017/9/4 外部可以传递参数
        args.putString("keyOne", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerTabOneFComponent.builder()
                .appComponent(appComponent)
                .tabOneFModule(new TabOneFModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_one, null, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        //获取newInstance 传入的初始化参数 如下:
        if (bundle != null) {
            mIndex = bundle.getString("keyOne", "index");
        }
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }


}
