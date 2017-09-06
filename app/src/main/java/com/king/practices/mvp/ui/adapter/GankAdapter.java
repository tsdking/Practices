package com.king.practices.mvp.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.practices.R;
import com.king.practices.mvp.model.entity.Gank;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
class GankAdapter extends BaseQuickAdapter<Gank, BaseViewHolder> {
    public GankAdapter(@LayoutRes int layoutResId, @Nullable List<Gank> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Gank it) {
        helper.setText(R.id.tv_item, it.get_id());
    }
}
