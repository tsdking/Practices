package com.king.practices.mvp.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.practices.R;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class DrawListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DrawListAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String it) {
        helper.setText(R.id.tv_item,it);
    }
}
